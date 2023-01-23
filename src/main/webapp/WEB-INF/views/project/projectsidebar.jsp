<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- ======= Sidebar ======= -->
<aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

        <!-- Project Section -->
        <li class="nav-heading">프로젝트</li>

        <li class="nav-item">
            <a class="nav-link ${navType == 'project'? '':'collapsed'}">
                <i class="bi bi-grid"></i>
                <span>${pdto.projectName}</span>
            </a>
        </li>

        <!-- Board Section-->
        <h5 class="card-title"></h5>
        <li class="nav-heading">보드</li>

        <li class="nav-item">
            <!-- Board Drop Down Menu -->
            <a class="nav-link ${navType == 'board'? '':'collapsed'}" data-bs-target="#components-nav" data-bs-toggle="collapse">
                <i class="bi bi-menu-button-wide"></i><span>보드</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="components-nav" class="nav-content collapse show" data-bs-parent="#sidebar-nav">
                <!-- Board List -->
                <c:forEach var="bdto" items="${blist}">
                    <li id="${bdto.boardSeq}">
                        <div class="row">
                            <div class="col-sm-8">
                                <a href="${twone}/project/board?projectSeq=${pdto.projectSeq}&boardSeq=${bdto.boardSeq}">
                                    <div><span>${bdto.boardName}</span></div>
                                </a>
                            </div>
                            <div class="col-sm-2">
                                <!-- Three Dots Dropdown Menu Icon -->
                                <div class="filter">
                                    <a class="icon" href="#" data-bs-toggle="dropdown"><i class="bi bi-three-dots"></i></a>
                                    <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
                                        <li><a class="dropdown-item" href="${twone}/project/deleteboard?projectSeq=${pdto.projectSeq}&boardSeq=${bdto.boardSeq}">보드 삭제</a></li>
                                    </ul>
                                </div><!-- End Three Dots Dropdown Menu Icon -->
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <!-- Board Creation Button -->
            <div class="d-grid gap-2 mt-3">
                <button class="btn btn-primary btn-light" type="button" onclick="toggleInput('board-input-box')">
                    <img src="../resources/bootstrap/img/button_plus.png" width="17">
                </button>
            </div>
            <br>
            <div class="col-sm-12">
                <!-- Board Creation Input Box -->
                <input type="text" class="form-control" id="board-input-box" style="display: none" placeholder="보드 이름 입력 후 엔터 (최대 30자)" onkeyup="addBoard(this)">
            </div>
        </li>

        <!-- Member Section -->
        <h5 class="card-title"></h5>
        <li class="nav-heading">사용자</li>

        <li class="nav-item">
            <a class="nav-link ${navType == 'team'? '':'collapsed'}" href="${twone}/project/team?projectSeq=${pdto.projectSeq}">
                <i class="bi bi-person"></i>
                <span>사용자 관리</span>
            </a>
        </li>

        <!-- Setting Section -->
        <h5 class="card-title"></h5>
        <li class="nav-heading">설정</li>


        <li class="nav-item">
            <a class="nav-link ${navType == 'setting'? '':'collapsed'}" href="${twone}/project/setting?projectSeq=${pdto.projectSeq}">
                <i class="bi bi-person"></i>
                <span>프로젝트 설정</span>
            </a>
        </li>

    </ul>

</aside><!-- End Sidebar-->

<script>

    /* 보드 생성 */
    function addBoard(inputText){

        const inputBox = document.getElementById("board-input-box");
        let boardName = inputText.value;

        // 입력 글자수 제어
        if(boardName.length > 30) {
            alert("최대 30자까지만 작성할 수 있습니다.");
            inputBox.value = boardName.substring(0, 28); // 문자열 29자로 자르기
            return;
        }

        // 엔터키 입력 시 IF문 실행
        if (window.event.keyCode == 13){
            // 공백이 입력된 경우
            if(boardName.trim() == ""){
                alert("보드명을 최소 1글자 이상 입력해 주세요.");
                return;
            }

            // URL(+ 파라미터) 만들기
            let url = "/project/addboard?projectSeq=" + ${pdto.projectSeq} + "&boardName=" + encodeURIComponent(boardName);

            // 연결 작업
            const xhttp = new XMLHttpRequest();
            xhttp.open("GET", url, true);

            // 콜백 작업 지정
            xhttp.onreadystatechange = function (){
                if(this.readyState == 4 && this.status == 200){
                    document.getElementById("components-nav").innerHTML = this.responseText;
                }
            };

            // input 클리어 & 숨기기
            inputBox.value = null;
            inputBox.style.display = "none";

            // 결과값 받음
            xhttp.send();
        }
    }

    /* 입력창 보이기/숨기기 */
    function toggleInput(inputBoxId) {

        let id = inputBoxId;
        const inputBox = document.getElementById(id);

        // input toggle
        if(inputBox.style.display != "none") {
            // 입력창 숨길때 입력값 삭제
            inputBox.value = null;
            inputBox.style.display = "none";
        } else {
            inputBox.style.display = "block";
        }
    }

</script>