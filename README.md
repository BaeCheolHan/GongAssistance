1. NumberToString
   1) 파일 경로 : com.herren.gongassistance.quiz.NumberToString
   2) 문제해결 전략 : 
      - 문자열로 변환할 시작 숫자 변수와 종료 숫자 변수 그리고 배열을 순회하면서 비교할 현재 변수를 설정
      - 배열을 순회하면서(첫번째 인덱스는 제외) 현재 변수와 현재변수에서 1을 더한 값이 현재의 인덱스의 값과 일치하는지 비교
      - 만약 일치한다면 종료 숫자 변수의 값을 현재 변수로 변경
      - 일치하지 않는다면 문자열을 생성하여 결과를 리턴할 List<String>에 add

2. 공비서
   1) API 명세
      - swagger 사용, 프로젝트 실행 후 http://loalhost:8080/swagger-ui/
   2) DB
      - H2 DB 사용
      - 과제 수행 위해 spring.jpa.hibernate.ddl-auto 값을 create-drop 으로 설정했으나 실제 어플리케이션을 개발한다고 가정시 none 으로 변경 필요
      - h2 DB를 사용했으므로 별도의 DB 설치는 할 필요 없음
   3) 문제 해결 전략 :
      - 샵 Entity와 직원 Entity를 OneToMany 관계로 설정
      - 동시성 문제를 해결하기 위해 Redis를 사용하여 lock 로직 구현
      - 샵에 직원 등록시, 샵 고유 아이디와 직원 고유 아이디를 전달받아 table을 확인후 정보가 존재하지 않는다면 exception을 throw 하도록 처리
      - 샵에 직원 등록시 직원 정보를 find 한 후, shop과 연관관계가 있을 경우 특정샵에 이미 등록되어있다고 판단, exception을 throw 하도록 처리
   4) 예외처리 :
      - 하나의 커스텀 Exception을 생성하고 ErrorCode를 정의하여 ControllerAdvice에서 각 ErrorCode에 맞게 에러 응답하도록 처리
