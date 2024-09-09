> ⚠️ 현재 페이지는 스르륵의 `Android` repository입니다.<br>아래 링크를 통해 스르륵의 다른 repositories를 확인해주세요.

- [FintechInternshipCourse-Dongas](https://github.com/orgs/FintechInternshipCourse-Dongas/repositories) : 스르륵 organization
- [backend](https://github.com/FintechInternshipCourse-Dongas/backend) : Backend of 스르륵 with Spring

# 💸프로젝트 개요
![logo_20](https://github.com/user-attachments/assets/04318198-05cd-49d9-9517-62a3df616e5c)

> QR 결제를 이용한 자동 더치페이 서비스

## 개발 배경
다양한 금융사에서 정산하기, 더치페이 기능이 증가하며 간편송금 서비스 이용이 빠르게 증가하고 있습니다. 하지만, 다음과 같은 이유로 정산 과정에서 돈을 받이 어려운 상황이 빈번하게 발생합니다.
- 총무가 정산해야 하는 사실을 까먹어서
- 현금이 부족해서
- 모임 멤버가 돈을 주지 않아서
따라서 제때 정산 받기 어려운 기존 더치페이 서비스의 문제점을 해결하고자 모임 QR을 통해 동시 분할 결제가 이루어지도록 선정산 더치페이 서비스(이하 `스르륵`)를 제안하게 되었습니다.

## 주요 기능
- N분의1 정산
- QR 결제
- 정산 내역 관리
- 스르륵 머니 충전 및 계좌 송금
>  ✨[발표영상](https://youtube.com/watch?v=NAMajFrSIO0&si=8-RDY1DZ8PjV-7TK)<br/>

## 팀원 및 역할분담
|[@rlagksql219](https://github.com/rlagksql219)|[@nangeee](https://github.com/nangeee)|[@Woohahalife](https://github.com/Woohahalife)|[@joyulmoo](https://github.com/joyulmoo) &emsp; [@ubbba](https://github.com/ubbba) &emsp; [@gaeun0204](https://github.com/gaeun0204)|
|-----|-----|-----|-----|
|**Android** <br> • 홈화면 <br> • 로그인/회원가입 <br> • 마이페이지 <br> • 스르륵머니 충전 <br> • 계좌 송금 |**Android** <br> • 결제수단 등록 <br> • 모임 생성 <br> • 멤버 초대 <br> • 정산 프로세스 <br> • 결제 |**Backend** <br> • API 설계 및 구현 <br> • 시스템 아키텍쳐 설계 <br> • 데이터베이스 설계 및 최적화 <br> • 프로젝트 배포 및 운영 |**기획** <br> • 비즈니스 기획 <br> • UI/UX 기획
<br/>


# 💸프론트엔드 기능 구현 사항
`Kotlin`을 이용하여 `Android`를 개발하였습니다.
>  ✨[demo video](https://youtube.com/watch?v=on4zdKuLYIg&si=OXFuJd4eoIW8CXMo)<br/>

## 주요 기능
- **백엔드 연결**
    - `Retrofit` 이용

- **회원가입 & 로그인 페이지**
    - 결제수단 등록

- **Main 페이지**
    - 모임 생성 및 모임 관리
        - 초대 코드를 이용한 모임 참가
    - 정산하기
      - 초대 코드로 사용자 초대
        - 정산 내역 등록
        - 결제 QR
        - 정산 내역 관리
    - 알림 기능
        - 정산 요청 알림

- **마이페이지**
    - 내 정보 관리
    - 스르륵 머니 충전
    - 계좌 송금
    - 결제수단 관리
      - 결제수단 삭제
    - 모임 관리

## 주요 기술
- `JWT Token`
- `Retrofit`
- `MVVM Pattern`
- `Gradle`
<br>

# 💸결과 화면

### ✨로그인
