# location-search-api
장소 검색 서비스

### 구성
- front-end : angular-js
- back-end : spring-boot, java 1.8, h2db
- 개발 환경 : mac, intellij, gradle

### 구동 방법
1. https://github.com/bboroccu/location-search-api.git 에서 master branch 소스 다운
2. 터미널로 다운로드한 폴더로 이동
3. `gradle clean :bootJar` 명렁어 실행
4. 완료되면 /build/libs 폴더로 이동
5. location-search-api.jar 파일 존재 유무 확인 
6. java -Xms1g -Xmx1g -server -XX:+UseG1GC -XX:NewRatio=2 -Duser.timezone=Asia/Seoul -jar location-search-api.jar 실행
7. 웹브라우저에서 http://127.0.0.1:5000 이동
8. 아이디 : bboroccu, password : 1234 로 로그인 후 키워드 검색이용

### 이슈
- kakao 키워드 검색에서 검색 데이터의 meta의 `pageable_count` 와 `total_count`에 대한 설명부족으로 의미하는 부분에 대한 이해가 부족하여
page 처리에서 `pageable_count`를 사용했는데 `page` 파라미터를 변경하며 조회 요청을 하면 `pageable_count`가 동일한 값으로 내려오지 않아
front-end 에서 pagination처리에 문제가 있습니다. 

### 사용된 외부 라이브러리
front-end
1. 비밀번호 암호화 위해 MD5(`http://lig-membres.imag.fr/donsez/cours/exemplescourstechnoweb/js_securehash/md5.js`)사용
2. paging 처리를 위해 ui.bootstrap 사용
