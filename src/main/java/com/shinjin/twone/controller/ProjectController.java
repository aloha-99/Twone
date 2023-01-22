package com.shinjin.twone.controller;

import com.shinjin.twone.common.commonMethod;
import com.shinjin.twone.dto.*;
import com.shinjin.twone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    MemService memService;
    @Autowired
    BoardService boardService;
    @Autowired
    ColService colService;
    @Autowired
    IssueService issueService;
    @Autowired
    IssueFormService issueFormService;
    @Autowired
    LinkedIssueService linkedIssueService;
    @Autowired
    LabelService labelService;

    @RequestMapping("/project")
    public String viewProjectList(HttpServletRequest request, Model model){
        int memSeq = (int) request.getSession().getAttribute("login");
        MemDTO memDTO = memService.getDto(memSeq);
        model.addAttribute("memDTO", memDTO);

        // team 테이블을 활용하여 내가 속한 프로젝트 가져오기
        List<ProjectDTO> plist = projectService.getList(memSeq);
        model.addAttribute("plist", plist);
        return "project/project";
    }

    @RequestMapping("/project/createForm")
    public String createProjectForm(HttpServletRequest request, Model model) {
        int mem_seq = (int) request.getSession().getAttribute("login");
        MemDTO memDTO = memService.getDto(mem_seq);
        model.addAttribute("memDTO", memDTO);

        return "project/createForm";
    }

    @RequestMapping("/project/create")
    public String createProject(HttpServletRequest request, ProjectDTO projectDto, Model model) {
        int memSeq = (int) request.getSession().getAttribute("login");
        MemDTO memDTO = memService.getDto(memSeq);
        model.addAttribute("memDTO", memDTO);

        // 프로젝트 생성
        projectDto.setMemSeq(memSeq);
        int projectSeq = projectService.createProject(projectDto);

        // 자동 팀 생성 (내가 만들었으니 관리자로)
        // 프로젝트 시퀀스 가져온걸 다시 보내기
//        projectDto.setProjectSeq(projectSeq); // selectOne 으로 대체 [윤석 230118]
        projectDto = projectService.selectOne(projectSeq);
        projectService.insertMasterTeam(projectDto);

        // 샘플 보드 생성
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setProjectSeq(projectSeq);
        int boardSeq = boardService.createSampleBoard(boardDTO);

        // 샘플 컬럼 생성
        ColDTO colDTO = new ColDTO();
        colDTO.setProjectSeq(projectSeq);
        colDTO.setBoardSeq(boardSeq);
        int colSeq = colService.createSampleColumn(colDTO); // 일반 컬럼
        colService.addDoneColumn(colDTO); // Done 컬럼

        // 샘플 이슈 생성
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setProjectSeq(projectSeq);
        issueDTO.setBoardSeq(boardSeq);
        issueDTO.setColSeq(colSeq);
        issueDTO.setMemSeq(memSeq);
        issueDTO.setIssueCode(projectDto.getProjectKey() + "-");
        issueDTO.setIssueTitle("샘플 이슈");
        issueService.addIssue(issueDTO);

        return "redirect:/project";
    }

    @RequestMapping("/project/setting")
    public String projectSetting(HttpServletRequest request, Model model){
        int memSeq = (int) request.getSession().getAttribute("login");
        MemDTO memDTO = memService.getDto(memSeq);
        model.addAttribute("memDTO", memDTO);

        int projectSeq = Integer.parseInt(request.getParameter("projectSeq"));
        ProjectDTO pdto = projectService.selectOne(projectSeq);
        model.addAttribute("pdto", pdto);

        // 프로젝트 세팅 권한
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("memSeq", memSeq);
        map.put("projectSeq", projectSeq);
        int check = projectService.checkSetting(map);

        if(check == 1) {
            model.addAttribute("navType", "setting");
            return "project/setting";
        }else{
            String msg = "프로젝트 설정 권한이 없습니다. 관리자에게 문의 하세요";
            commonMethod.setAttribute(model, "/project/board?projectSeq=" + projectSeq, msg);
            return "common/alert";
        }

    }

    @RequestMapping("/project/delete")
    public String deleteProject(HttpServletRequest request){
        int projectSeq = Integer.parseInt(request.getParameter("projectSeq"));

        /* 이슈 및 하위 레코드 전체 삭제 */
        List<Integer> issueSeqList = issueService.getIssueSeqListUnderProject(projectSeq);
        for(int issueSeq : issueSeqList){
            // 이슈폼 및 이슈폼 자식 삭제
            List<IssueFormDTO> issueFormList = issueFormService.getIssueFormList(issueSeq);
            for(IssueFormDTO issueFormDTO : issueFormList){
                String code = issueFormDTO.getFormsSeq().substring(0, 3);
                String formsTableName = "t_forms_" + code; // forms 테이블명 조합
                String formsColName = code + "_seq"; // forms 컬럼명 조합
                issueFormDTO.setFormsTableName(formsTableName);
                issueFormDTO.setFormsColName(formsColName);
                issueFormService.deleteFormsUnderIssue(issueFormDTO); // 이슈폼 자식 삭제
                issueFormService.deleteIssueForm(issueFormDTO.getIssueFormSeq()); // 이슈폼 삭제
            }
            // 이슈된 링크 삭제
            linkedIssueService.deleteLinkedIssue(issueSeq);
            // 이슈 삭제
            issueService.deleteIssue(issueSeq);
        }

        /* 레이블 삭제 */
        labelService.deleteLabelByProjectSeq(projectSeq);

        /* 컬럼 삭제 */
        colService.deleteColumnByProjectSeq(projectSeq);

        /* 보드 삭제 */
        boardService.deleteBoardByProjectSeq(projectSeq);

        /* 프로젝트 삭제 */
        projectService.deleteOne(projectSeq);

        return "redirect:/project";
    }
}
