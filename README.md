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

- **마이페이지**
    - 내 정보 관리
    - 스르륵 머니 충전
    - 계좌 송금
    - 결제수단 관리
    - 모임 관리

## 주요 기술
- `JWT Token`
- `Retrofit`
- `MVVM Pattern`
- `Gradle`
<br>

# 💸결과 화면

### ✨로그인
![1 로그인](https://user-images.githubusercontent.com/69866091/152654409-4aedfbb5-07e2-4bf4-9143-7586896d0963.gif)

### ✨마이페이지
![2 마이페이지](https://user-images.githubusercontent.com/69866091/152655343-91dea02a-0a44-481d-af00-72fa8128de96.png)

### ✨매장 검색
![3 매장 검색](https://user-images.githubusercontent.com/69866091/152654425-91a9c402-1c5a-4c8a-bbf2-948e991a6084.gif)

### ✨매장 정보
![4 매장 정보](https://user-images.githubusercontent.com/69866091/152654430-b8d07f84-ad0d-4584-9051-afa3725e9270.gif)

### ✨매장지도
![5 매장지도](https://user-images.githubusercontent.com/69866091/152654433-36fb29a2-1a1e-46f3-8388-57433669ce40.gif)

### ✨카테고리별 매장 분류
![6 카테고리별 매장 분류](https://user-images.githubusercontent.com/69866091/152654435-97a29f32-a388-444a-80c6-1cda50003286.gif)

### ✨채팅을 통한 매장 Q&A
![7 채팅을 통한 매장 Q A](https://user-images.githubusercontent.com/69866091/152654442-7e937729-0030-45e7-9324-b2edf196306e.gif)

### ✨상품목록
![8 상품목록](https://user-images.githubusercontent.com/69866091/152654445-e244b2ba-ee5b-4f40-bf33-a4f10265e442.gif)

### ✨상점목록
![9 상점목록](https://user-images.githubusercontent.com/69866091/152654448-5a5ba54b-e87f-4f54-9835-804b2d5322c9.gif)

### ✨상점 검색
![10 상점 검색](https://user-images.githubusercontent.com/69866091/152654458-19482c65-379a-49de-a685-4262ae1f73c6.gif)

### ✨연관상품 추천
![11 연관상품 추천](https://user-images.githubusercontent.com/69866091/152654463-73ee27a5-af2d-45e8-8247-66e3f9a5f4a9.gif)

### ✨채팅을 통한 상품 구매
![12 채팅을 통한 상품 구매](https://user-images.githubusercontent.com/69866091/152654467-7000a4a5-9534-4dc9-83b6-7632701ec3cb.gif)

### ✨채팅목록
![13 채팅목록](https://user-images.githubusercontent.com/69866091/152654481-3ada9097-7951-4577-ad53-abca2b07d26f.gif)
