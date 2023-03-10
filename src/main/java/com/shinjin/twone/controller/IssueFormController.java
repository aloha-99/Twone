package com.shinjin.twone.controller;

import com.shinjin.twone.dto.*;
import com.shinjin.twone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class IssueFormController {

  @Autowired
  IssueService issueService;
  @Autowired
  IssueFormService issueFormService;
  @Autowired
  LinkedIssueService linkedIssueService;
  @Autowired
  FormsDatService formsDatService;
  @Autowired
  FormsPerService formsPerService;
  @Autowired
  FormsPriService formsPriService;
  @Autowired
  FormsSimService formsSimService;
  @Autowired
  MemService memService;

  /*
   * [이슈폼 생성], [이슈폼 위로 이동], [이슈폼 아래로 이동] 메소드는 IssueController에 작성되어 있음
   * -> 이유 : html 태그 작성하는 메소드를 이슈 관련 메소드와 공유함
   */

  /* 이슈폼 제목(Label) 변경 */
  @GetMapping("/project/updatelabel")
  @ResponseBody
  public String updateLabelProc(HttpServletRequest request){
    // 이슈폼 제목 입력값, issueSeq 받기
    String labelValue = request.getParameter("labelValue");
    int issueSeq = Integer.parseInt(request.getParameter("issueSeq"));
    String formsSeq = request.getParameter("formsSeq");

    switch (formsSeq.substring(0, 3)){
      case "per":
        // perDTO 만들기
        FormsPerDTO perDTO = new FormsPerDTO();
        perDTO.setPerSeq(formsSeq);
        perDTO.setPerTitle(labelValue);
        // 이슈폼 제목(Label) 변경
        formsPerService.updatePerTitle(perDTO);
        break;
      case "dat":
        // datDTO 만들기
        FormsDatDTO datDTO = new FormsDatDTO();
        datDTO.setDatSeq(formsSeq);
        datDTO.setDatTitle(labelValue);
        // 이슈폼 제목(Label) 변경
        formsDatService.updateDatTitle(datDTO);
        break;
      case "pri":
        // priDTO 만들기
        FormsPriDTO priDTO = new FormsPriDTO();
        priDTO.setPriSeq(formsSeq);
        priDTO.setPriTitle(labelValue);
        // 이슈폼 제목(Label) 변경
        formsPriService.updatePriTitle(priDTO);
        break;
      case "sim":
        // simDTO 만들기
        FormsSimDTO simDTO = new FormsSimDTO();
        simDTO.setSimSeq(formsSeq);
        simDTO.setSimTitle(labelValue);
        // 이슈폼 제목(Label) 변경
        formsSimService.updateSimTitle(simDTO);
        break;
    }
    // 이슈 업데이트 일자 변경
    issueService.updateIssueUpdate(issueSeq);

    // 문자열 만들기
    String result = "";
    result += "<input id=\"" + formsSeq + "-label\" type=\"text\" value=\"" + labelValue + "\" onkeyup=\"updateLabel(this, '" + formsSeq + "')\">";

    return result;
  }

  /* 이슈폼 값(Value) 변경 */
  @GetMapping("/project/updatevalue")
  @ResponseBody
  public String updateValueProc(HttpServletRequest request){

    int issueSeq = Integer.parseInt(request.getParameter("issueSeq"));
    String inputValue = request.getParameter("inputValue");
    String formsSeq = request.getParameter("formsSeq");
    String result = "";

    switch (formsSeq.substring(0, 3)){
      case "per":
        // perDTO 만들기
        FormsPerDTO perDTO = new FormsPerDTO();
        perDTO.setPerSeq(formsSeq);
        perDTO.setMemSeq(Integer.parseInt(inputValue));
        // 이슈폼 값(Value) 변경
        formsPerService.updateMemSeq(perDTO);
        // 문자열 만들기
        int projectSeq = issueService.getIssueDTO(issueSeq).getProjectSeq(); // projectSeq 불러오기
        List<MemDTO> teamList = memService.getTeamMemberForIssueForm(projectSeq); // (권한 구성원 이상의) 팀 멤버 리스트 불러오기
        result += "<select id=\"" + perDTO.getPerSeq() + "-value\" class=\"form-select\" aria-label=\"Default select example\" onchange=\"updateValue(this, '" + perDTO.getPerSeq() + "')\">";
        for(MemDTO mdto : teamList){
          if(perDTO.getMemSeq() == mdto.getMemSeq()){
            result += "<option value=\"" + mdto.getMemSeq() + "\" selected>" + mdto.getMemName() + "(" + mdto.getMemEmail() + ")</option>";
          } else {
            result += "<option value=\"" + mdto.getMemSeq() + "\">" + mdto.getMemName() + "(" + mdto.getMemEmail() + ")</option>";
          }
        }
        result += "</select>";
        break;
      case "dat":
        // datDTO 만들기
        FormsDatDTO datDTO = new FormsDatDTO();
        datDTO.setDatSeq(formsSeq);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date datValue = null;
        try {
            datValue = format.parse(inputValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datDTO.setDatValue(datValue);
        // 이슈폼 값(Value) 변경
        formsDatService.updateDatValue(datDTO);
        // 문자열 만들기
        result += "<input id=\"" + formsSeq + "-value\" type=\"date\" class=\"form-control\" value=\"" + inputValue + "\" onchange=\"updateValue(this, '" + formsSeq + "')\">";
        break;
      case "pri":
        // priDTO 만들기
        FormsPriDTO priDTO = new FormsPriDTO();
        priDTO.setPriSeq(formsSeq);
        priDTO.setPriValue(inputValue);
        // 이슈폼 값(Value) 변경
        formsPriService.updatePriValue(priDTO);
        // 문자열 만들기
        String[] primary = {"Highest", "High", "Medium", "Low", "Lowest"};
        result += "<select id=\"" + formsSeq + "-value\" class=\"form-select\" aria-label=\"Default select example\" onchange=\"updateValue(this, '" + formsSeq + "')\">";
        // priValue 값과 일치하면 selected 삽입
        for(int i = 0; i < 5; i++){
          result += "<option value=\"";
          result += (primary[i].equals(inputValue)) ? primary[i] + "\" selected" : primary[i] + "\"";
          result += ">" + primary[i] + "</option>";
        }
        result += "</select>";
        break;
      case "sim":
        // simDTO 만들기
        if(inputValue == null) {
          inputValue = "";
        }
        FormsSimDTO simDTO = new FormsSimDTO();
        simDTO.setSimSeq(formsSeq);
        simDTO.setSimValue(inputValue);
        // 이슈폼 값(Value) 변경
        formsSimService.updateSimValue(simDTO);
        // 문자열 만들기
        result += "<input id=\"" + formsSeq + "-value\" type=\"text\" class=\"form-control\" value=\"" + inputValue + "\" onchange=\"updateValue(this, '" + formsSeq + "')\">";
        break;
    }
    // 이슈 업데이트 일자 변경
    issueService.updateIssueUpdate(issueSeq);

    return result;
  }

  /*** 이슈폼 삭제 ***/
  @GetMapping("/project/deleteforms")
  @ResponseBody
  public void deleteFormsProc(HttpServletRequest request){
    int issueSeq = Integer.parseInt(request.getParameter("issueSeq"));
    String formsSeq = request.getParameter("formsSeq");
    // 이슈폼 삭제
    issueFormService.deleteIssueFormByFormsSeq(formsSeq);
    // 이슈폼 배치순서 재정렬
    // 배치순서 오름차순 정렬된 이슈폼 리스트 불러오기
    List<IssueFormDTO> issueFormList = issueFormService.getIssueFormList(issueSeq);
    int num = 1;
    for(IssueFormDTO ifdto : issueFormList){
      ifdto.setIssueFormOrder(num++);
      issueFormService.updateIssueFormOrder(ifdto);
    }
    // 이슈 업데이트 일자 변경
    issueService.updateIssueUpdate(issueSeq);
  }

}
