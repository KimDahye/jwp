2014년 개발 경험 프로젝트
=========

1. 로컬 개발 환경에 Tomcat 서버를 시작한 후 http://localhost:8080으로 접근하면 질문 목록을 확인할 수 있다. http://localhost:8080으로 접근해서 질문 목록이 보이기까지의 소스 코드의 호출 순서 및 흐름을 설명하라.

	1. 먼저 web.xml을 살펴보면, welcome-file-list에 index.jsp가 설정되어 있다. 따라서 index.jsp를 찾아간다.
	2. index.jsp 를 그리려고 봤더니, 이 안에서 /list.next 로 redirect가 일어난다. 그럼 *.next 를 받는 FrontController로 간다.
	3. FrontController 안 line 36에서 requestMappling이 requestURI가 list.next임을 확인하여, listController 객체를 반환한다. (이제부터 controller에는 ListController 객체가 담겨있다.)
	4. line 39에서 ListController의 execute를 실행한다. 이 메소드 안에서, DB에 다녀와 질문목록을 가져오고, ModelAndView 객체 mav의 view로는 jstlView(list.jsp)를 물리고, model에는 질문목록 리스트를 questions라는 이름으로 물린다.
	5. line 41에서 mav의 model에 있는 질문목록 리스트를 request에 questions(key name)라는 이름의 속성으로 물리고, 요청을 list.jsp로 forward한다.
	6. list.jsp에서는 EL을 통해 requestScope에 물린 questions에 접근하여 question목록을 보여준다.


2. 질문 목록은 정상적으로 동작하지만 질문하기 기능은 정상적으로 동작하지 않는다. 질문하기 기능을 구현한다. 질문 추가 로직은 QuestionDao 클래스의 insert method 활용 가능하다. HttpServletRequest에서 값을 추출할 때는 ServletRequestUtils 클래스를 활용 가능하다. <br>
=> ServletRequestUtils 안 쓰였다. list.next로 redirect해주어야 한다.

3. 자바 기반으로 웹 프로그래밍을 할 경우 한글이 깨진다. 한글이 깨지는 문제를 해결하기 위해 ServletFilter를 활요해 문제를 해결할 수 있다. core.web.filter.CharacterEncodingFilter에 어노테이션 설정을 통해 한글 문제를 해결한다. <br>
=>
참고: http://neokido.tistory.com/entry/EncodingFilter-%EB%A1%9C-%ED%95%9C%EA%B8%80-%EC%B2%98%EB%A6%AC-%ED%95%98%EA%B8%B0 <br>
주의: web.xml에서 filter와 welcome-file-list의 위치가 정해져있다는 것! 


4. 질문 목록에서 제목을 클릭하면 상세보기 화면으로 이동한다. 상세보기 화면에서 답변 목록이 정적인 HTML로 구현되어 있다. 답변 목록을 정적인 HTML이 아니라 데이터베이스 저장되어 있는 답변을 출력하도록 구현한다. 단, <%%>와 같이 스크립틀릿을 사용하지 않고 JSTL과 EL(expression language)만으로 구현해야 한다. <br>
=> list.jsp 로 forwarding 하는 것 show.jsp로 수정 <br>
=> EL로 답변 가져옴.

5. 상세보기 화면에서 답변하기 기능이 정상적으로 동작하지 않는다. 답변을 추가하는 기능은 AJAX로 구현할 계획이다. 클라이언트의 AJAX 구현코드는 webapp/javascripts/qna.js에 구현 되어 있다. 이 클라이언트에 코드가 정상적으로 동작하도록 서버측 코드를 구현해야 한다. 답변 추가 로직은 AnswerDao 클래스의 insert() method를 활용한다. 답변을 추가하는 시점에 질문(QUESTIONS 테이블)의 댓글 수(countOfComment)도 1 증가해야 한다. 데이터베이스 접근 로직은 직접 구현해야 한다. <br>
=> 완료.

6. next.controller package의 ListController와 ShowController는 멀티 쓰레드 상황에서 문제가 발생할 가능성이 있는 코드이다. 멀티 쓰레드 상황에서 문제가 발생하지 않도록 수정한다. <br>
=> 각 컨트롤러 객체 RequestMappping에서 init이 불릴 때 한번만 생성된다. ListController와 ShowController 객체의 field에는 QuestionDao와 Dao에서 넘겨온 데이터를 담는 List가 있었다. 그런데 이 List가 모든 요청마다 공유하기 때문에 여러 요청이 겹치다보면 요청과 결과가 다르게 저장될 수 있다. 따라서, 이 List를 유저마다 공유하는 게 아니라, 유저마다 각각 다르게 갖고 있게 해주어야 한다. 그러려면, List 변수를 필드에 선언하지 말고, 메소드 안으로 넣어주어야 한다. 

7. 이 Q&A 서비스는 모바일에서도 서비스할 계획이라 API를 추가해야 한다. API는 JSON 또는 XML 형식으로 제공할 계획이다. 질문 목록 데이터를 /api/list.next URL로 접근 가능하도록 서비스한다. <br>
=>완료



