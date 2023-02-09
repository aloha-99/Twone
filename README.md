# Twone(2 > 1) Project
### *Two Heads are Better Than One*
---

**1. Twone 홈페이지 : http://www.twoneproject.com**

**2. 프로젝트 팀명: 신진봉(SJB)**
	
- Leader : 신성주 (hans52410537@gmail.com)
    
- Member : 진윤석 (ed.ys.jin@gmail.com)
    
- Member : 봉지원 (jeewon1202@gmail.com)


**3. 프로젝트 목표 및 요구사항**

ZIRA, JANDI 등 과 같은 협업툴 구현해보기


- AWS(EC2, RDS)기반의 협업툴 시스템을 구축


**4. 진행 기간**

2022.12.17 ~ 현재 진행 중


**5. 주요 개발자원 (S/W)**
<table border="1">
		<tr>
			<th>구 분</th>
			<th>용 도</th>
			<th>명칭 및 버전</th>
		</tr>
		<tr align="center">
			<td>OS</td>
			<td>개발 PC</td>
			<td>Windows 10, Mac OS</td>
		</tr>
		<tr align="center">
			<td>WAS</td>
			<td>WEB/WAS</td>
			<td>Apach Tomcat 9</td>
		</tr>
		<tr align="center">
			<td>미들웨어</td>
			<td>DB</td>
			<td>MySQL</td>
		</tr>
		<tr align="center">
			<td rowspan="3">사용 언어</td>
			<td>백 앤드</td>
			<td>java jdk 1.8 , jsp/servlet 3.1</td>
		</tr>
		<tr align="center">
			<td>개발 프레임 워크</td>
			<td>spring framework 5.0.2 , Spring Boot 2.7.7</td>
		</tr>
		<tr align="center">
			<td>프론트 앤드</td>
			<td>HTML5 , CSS3 , JAVASCRIPT</td>
		</tr>
		<tr align="center">
			<td rowspan="2">소프트웨어 개발도구</td>
			<td>개발도구</td>
			<td>IntelliJ</td>
		</tr>
		<tr align="center">
			<td>DB</td>
			<td>dBeaver , MySQLWorkbench</td>
		</tr>
		<tr align="center">
			<td>문서 작성</td>
			<td>에디터</td>
			<td>
			[Google Drive](https://drive.google.com/drive/folders/17bybIQVKNMG0I_cRHtDqgadOedEauMU6) <br/>
			</td>
		</tr>
		<tr align="center">
			<td>형상 관리및 협업</td>
			<td>소스 관리 및 버전관리</td>
			<td>Git hub , Git</td>
		</tr>
		<tr align="center">
			<td rowspan="13">사용 라이브러리 및 API</td>
			<td>관리</td>
			<td>Maven</td>
		</tr>
		<tr align="center">
			<td>db</td>
			<td>Mybatis-3.5.3</td>
		</tr>
		<tr align="center">
			<td>로그</td>
			<td>Spring AOP , Log4j</td>
		</tr>
		<tr align="center">
			<td>디자인</td>
			<td>Bootstrap , JQuery UI</td>
		</tr>
		<tr align="center">
			<td>보안</td>
			<td>Spring Security-5.0.8</td>
		</tr>
		<tr align="center">
			<td>파일</td>
			<td>Fileupload-1.3.1</td>
		</tr>
		<tr align="center">
			<td>데이터 전송</td>
			<td>Ajax , JSON</td>
		</tr>
		<tr align="center">
			<td>화면 UI</td>
			<td>JQuery-3.5.2</td>
		</tr>
	</table>

**6. 담당 기능**
  * 회원가입 시 인증 메일 기능 구현 
  * 프로젝트 팀원 관리 (권한 변경, 팀원 초대, 팀원 삭제 등)
  * Scheduler를 활용하여 사용자 인증키 관리

  
**7. 느낀 점** <br/>

* 어려웠던 점 <br/>
📌 회원가입 시 이메일로 인증 링크를 보내 링크를 클릭해야 가입이 완료되는 기능을 어떻게 구현해야 될지 감이 잘 잡히지 않았다. <br/>
✔  pring-boot-starter-mail이라는 라이브러리에 관해 공부하여 Configuration으로 따로 만들어 사용하였고, 인증 실패 시의 프로세스를 생각하여 사용자의 인증 여부를 분리하여 데이터를 저장함으로써 사용자를 관리했다.  <br/><br/>

* 배운 점 <br/>
💡 다양한 개발도구에 대한 이해(새로운 개발환경에서의 개발 등)  <br/>
💡 새로운 기능 구현을 통한 역량 증진 <br/>
💡 이메일 인증 프로세스에 대한 이해 <br/>
💡 사용자 관점에서의 좋은 서비스에 대한 깊은 고찰 <br/>
💡 문서화 작업의 필요성과 작업 과정에 대한 이해 <br/>

**8. 시연 영상** <br/>
[![Video Label](http://img.youtube.com/vi/rAzBgJglJD4/0.jpg)](https://youtu.be/rAzBgJglJD4?t=0s)
