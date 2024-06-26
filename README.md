# 도서 대여 서비스 RenBook

---

<p align="center">
<img src="/Users/aljae/Study/RenBook/renbook/src/main/resources/static/images/renbook_logo.png" width="300px" alt="renbook"><br/>
</p>


개발기간 : 2024.05 ~ 2024.06

---

# 1. 개발 환경

---

Front : `BootStrap5` `CSS`  
Backend : `Spring` `Jpa` `MySql`

# 2. 주요 기능

---

- `베스트 도서`와 `신간 도서` 표시
  - 매월 대여 횟수로 베스트 도서 선정
  - 도서 출판일에 따른 신간 도서
  
- `도서 대여` 및 `찜하기`
  - 찜목록 또는 도서 상세 화면에서 대여하기, 찜하기 가능
  - 다른유저가 대여하고있는 도서는 대여불가
- `회원 가입` 및 `로그인`
  - 회원 가입 및 로그인시 아이디와 비밀번호 `검증`
  - 대여 및 찜하기 기능은 `로그인 후 사용가능`
  - 로그인 하지 않은 상태에서 대여목록, 찜목록 접근시 `로그인 요구`
  - 대여 목록, 찜목록에서 자신이 추가한 도서 목록 확인 가능
- 제목, 작가, 출판사를 검색하여 `도서검색` 가능


# 3.~~

---