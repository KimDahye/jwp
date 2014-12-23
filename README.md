2014년 개발 경험 프로젝트
=========

1. 로컬 개발 환경에 Tomcat 서버를 시작한 후 http://localhost:8080으로 접근하면 질문 목록을 확인할 수 있다. http://localhost:8080으로 접근해서 질문 목록이 보이기까지의 소스 코드의 호출 순서 및 흐름을 설명하라.

* 먼저 web.xml을 살펴보면, welcome-file-list 에 index.jsp가 설정되어 있다. 따라서 index.jsp를 찾아간다.
* index.jsp 를 그리려고 봤더니, 이 안에서 /list.next 로 redirect가 일어난다. 그럼 *.next 를 받는 FrontController로 간다.
* FrontController 안 line 36에서 requestMappling이 requestURI가 list.next임을 확인하여, listController 객체를 반환한다. (이제부터 controller에는 ListController 객체가 담겨있다.)
* line 39에서 ListController의 execute를 실행한다. 이 메소드 안에서, DB에 다녀와 질문목록을 가져오고, ModelAndView의 객체 mav view로는 jstlView(list.jsp)를 물리고, model에는 질문목록 리스트를 questions라는 이름으로 물린다.
* line 41에서 mav의 model에 있는 질문목록 리스트를 request에 questions(key name)라는 이름의 속성으로 물리고, list.jsp로 forward함.
* list.jsp에서는 EL을 통해 위에서 물린 questions에 접근하여 question목록을 보여준다.


2. 질문 목록은 정상적으로 동작하지만 질문하기 기능은 정상적으로 동작하지 않는다. 질문하기 기능을 구현한다. 질문 추가 로직은 QuestionDao 클래스의 insert method 활용 가능하다. HttpServletRequest에서 값을 추출할 때는 ServletRequestUtils 클래스를 활용 가능하다. 
=> ServletRequestUtils 안 쓰였다. list.next로 redirect해주어야 한다.

3. 자바 기반으로 웹 프로그래밍을 할 경우 한글이 깨진다. 한글이 깨지는 문제를 해결하기 위해 ServletFilter를 활요해 문제를 해결할 수 있다. core.web.filter.CharacterEncodingFilter에 어노테이션 설정을 통해 한글 문제를 해결한다.
=>
참고: http://neokido.tistory.com/entry/EncodingFilter-%EB%A1%9C-%ED%95%9C%EA%B8%80-%EC%B2%98%EB%A6%AC-%ED%95%98%EA%B8%B0
주의: web.xml에서 filter와 welcome-file-list의 위치가 정해져있다는 것! 



