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

### ✨초기 화면
<img src="https://github.com/user-attachments/assets/e2ffb816-5bcc-4dc9-a5d1-e26258672f33" alt="1" width="200" />

### ✨회원가입
<img src="https://github.com/user-attachments/assets/e65d6e6e-663b-4965-a9f0-06d55bd7a0e9" alt="2" width="200" />

### ✨로그인
<img src="https://github.com/user-attachments/assets/4ae4a2c8-3184-4519-a932-c69f158c4306" alt="3" width="200" />

### ✨결제수단 등록
<img src="https://github.com/user-attachments/assets/572952fe-76dd-4c93-be8f-21fe8bf9974c" alt="4" width="200" />

### ✨홈 화면
<img src="https://github.com/user-attachments/assets/80d5e175-76d8-4f30-a72f-409124314d98" alt="5" width="200" />

### ✨마이페이지
<img src="https://github.com/user-attachments/assets/1cf68f59-092f-4bba-b2e7-a9797f1b1a3a" alt="6" width="200" />

### ✨내 정보 관리
<img src="https://github.com/user-attachments/assets/cb5e31c6-8327-402d-bbf0-3de101a48224" alt="16" width="200" />

### ✨결제수단 관리
<img src="https://github.com/user-attachments/assets/121f573a-42a4-4390-973e-9fa603edcc71" alt="17" width="200" />

### ✨스르륵 머니 충전
<img src="https://github.com/user-attachments/assets/7400eed2-e6c8-40ec-8a1e-9278561e6b57" alt="7" width="200" />

### ✨계좌로 송금
<img src="https://github.com/user-attachments/assets/6b82c80f-6d9d-4592-8f2c-622b736d8f2d" alt="18" width="200" />

### ✨모임 생성
<img src="https://github.com/user-attachments/assets/a5fe4600-6848-473c-8aaf-cab732c3f767" alt="8" width="200" />

### ✨멤버 초대
<img src="https://github.com/user-attachments/assets/0631f89d-ca6b-4692-aebb-2d3b4d395715" alt="9" width="200" />

### ✨초대 코드로 모임 참가
<img src="https://github.com/user-attachments/assets/f1452de8-86ee-4727-b1fc-d5b51c4068aa" alt="10" width="200" />

### ✨멤버들에게 정산 요청
<img src="https://github.com/user-attachments/assets/efa8449d-323e-42a4-b114-5b80a10090fe" alt="11" width="200" />

### ✨정산 요청 동의
<img src="https://github.com/user-attachments/assets/9b189fd3-63d1-45bf-b347-db851747a4fe" alt="12" width="200" />

### ✨멤버들의 정산 동의 여부 확인
<img src="https://github.com/user-attachments/assets/44b8e9f5-cb32-40bb-811d-918e7b2bff5a" alt="13" width="200" />

### ✨QR코드를 이용한 동시 분할 결제
<img src="https://github.com/user-attachments/assets/2d14c4d4-0c63-4106-b43d-e857917cafa3" alt="14" width="200" />

### ✨정산 상세 내역
<img src="https://github.com/user-attachments/assets/e187d783-3094-494c-9035-846ccdb92fc9" alt="15" width="200" />
