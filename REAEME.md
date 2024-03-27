# Spring Lotto Project
- SpringBoot 로또 랜덤 번호 추출 Toy Project

# 프로젝트 소개
- 평소 로또 구매시에 추첨되지 않을 번호를 미리 제거 하여 랜덤으로 로또 번호를 뽑아 구매를 하곤 하였고, SpringBoot 웹 기반으로 만들어 게시판 등의 기능과 더불어 사용자들에게 무료로 배포하기 위한 프로젝트입니다.

# 개발환경
- Spring FrameWork 5.3.23
- Spring Boot 2.7.4
- JDK 11
- JPA (Hibernate 5.6.11)
- PostgreSQL

# 주요 기능
- 로그인 
    - JWT 토큰을 통한 인증 및 인가 
- Spring Security 적용
- Spring Mail 라이브러리 사용
  - 회원가입 및 비밀번호 리셋 시 이메일 전송
- Cache(caffeine cache) 사용
  - 회원 가입 시 AuthCode 저장
  - 게시판 목록 저장
- Slack ErrorMessage 전송
- AOP 적용
- Error 및 API 공통 Response 정의
- Message Source 를 이용한 응답 메시지 정의

# 프로젝트 내용
- 프로젝트 실행 시 관리자 계정 auto insert (관리자 계정 존재 시 skip)
- 관리자
  - 전체 유저 전반적인 관리 및 상태 변경
  - 전체 게시판 관리 및 상태 변경
  - 전체 게시글 관리 및 상태 변경
- 사용자
  - 랜덤 로또 번호 추출 가능 (제거목록 및 포함목록 포함)
  - 게시글 생성 및 수정, 삭제
  - 댓글 생성
  - 내 정보
    - 비밀번호 변경
    - 랜덤 로또 추출 히스토리 모니터링
    - 회원 탈퇴

# 수정 내용
- Setter 의존성 제거 (불변성 추가)
  