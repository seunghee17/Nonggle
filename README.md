# 농촌 인력 매칭 서비스 농글

# 🛠️ Jetpack Compose 기반 로그인/회원가입 마이그레이션

기존 XML 기반으로 구성된 로그인 및 회원가입 화면을 Jetpack Compose로 마이그레이션하고 아키텍처를 MVVM → MVI 클린 아키텍처로 재설계하였습니다. Jetpack Navigation, Hilt, ViewModel을 활용하여 UI/로직/데이터 계층을 분리하며 확장성과 유지보수성을 높였습니다.

---

## ✅ 주요 변경사항 요약

| 항목 | 변경 전 | 변경 후 |
|------|---------|---------|
| UI 구현 | XML + ViewBinding | **Jetpack Compose** |
| 화면 전환 | Intent 기반 | **Jetpack Navigation** |
| 아키텍처 | MVVM + Clean Architecture | **MVI + Clean Architecture** |
| DI(의존성 주입) | Hilt | **Hilt (DI 모듈 및 싱글톤 관리 개선)** |
| 테스트 | 미작성 | **ViewModel 단위 테스트 작성** |

---

## 🧱 아키텍처 구조

### 🧭 MVI + Clean Architecture

상태 관리의 예측 가능성과 안정성을 위해 `MVI 패턴`을 Clean Architecture에 적용하여 아래와 같은 구조로 상태를 관리합니다.

🧱 아키텍처 계층 구조

📍 Presentation (UI Layer)

UiState
→ StateFlow 기반으로 UI의 최신 상태를 구독하고 표현합니다.

UiEvent
→ SharedFlow 기반으로 사용자 입력을 순차적으로 처리하여 이벤트 누락을 방지합니다.

UiEffect
→ Channel 기반으로 Toast, Navigation 등 단발성 이벤트를 전달합니다.

📍 Domain

UseCases
→ 하나의 목적/유즈케이스에 집중된 비즈니스 로직을 담당하며 UI와 데이터 계층을 분리합니다.

📍 Data

Repository
→ Firebase, Google Auth 등 외부 데이터 소스를 추상화하고 UseCase에서 사용할 수 있도록 제공합니다.


> 이 구조를 통해 화면 구성과 상태 흐름을 명확히 분리하고 중복 이벤트 처리나 메모리 누수 가능성을 최소화할 수 있도록 설계했습니다.

---

## 🧪 테스트를 통한 안정성 확보

### 🔍 ViewModel 단위 테스트 작성

MVI 구조의 핵심인 `State`, `Event`, `Effect`가 올바르게 동작하는지 검증하기 위해 **ViewModel 단위 테스트를 작성**하여 안정성과 예측 가능성을 높였습니다.

#### ✅ 테스트 커버리지 예시

- 사용자가 카카오 로그인 버튼 클릭 시 Toast 메시지 출력 여부 확인
- Google 로그인 성공 시 intent sender가 효과로 전달되는지 검증
- 신규 유저일 경우 `Enroll` 화면으로 이동하는지 확인
- 기존 유저 + `WORKER` 타입일 경우 홈 화면으로 이동하는지 확인
- 로그인 유형 정보를 가져오는 UseCase 실패 시 에러 메시지 업데이트 확인
- Google 로그인 결과 처리 시 상태가 정확히 갱신되는지 확인

Jetpack Compose와 함께 아키텍처를 새롭게 재정립하는 경험은 UI 프레임워크 이상의 변화를 요구했습니다.
기능 구현 그 이상의 구조적 개선을 목표로 상태 흐름을 명확히 하고 유지보수 가능한 코드를 만들기 위해 고민하며 개발했습니다.
단위 테스트를 통해 안정성을 확보하고 기능 추가 및 유지보수가 용이한 구조로 성장 중입니다.

## 프로젝트 소개

![표지](https://github.com/songseunghei/CapstoneProject/assets/80136506/cb4bc378-0274-40a5-bc12-555ddaac2209)


![로고](https://github.com/songseunghei/CapstoneProject/assets/80136506/3155ed09-c471-4026-8cd8-6de6f8004de6)

농글은 농업 일자리 매칭 서비스로 안드로이드 기반 애플리케이션입니다.


## 어플리케이션 소개

![소개1](https://github.com/songseunghei/CapstoneProject/assets/80136506/40c5e699-de17-411b-9196-97056e6352f9)

![소개2](https://github.com/songseunghei/CapstoneProject/assets/80136506/0d70024c-ce1f-422b-b0f9-ecc24711a950)

![소개3](https://github.com/songseunghei/CapstoneProject/assets/80136506/ad148b96-6f4c-4b5a-bb49-9cb8f2b51755)

![Select](https://github.com/songseunghei/CapstoneProject/assets/80136506/c7245cb6-a458-4128-8e3e-1e2b11780547)

![Evaluation](https://github.com/songseunghei/CapstoneProject/assets/80136506/8ebf60c3-6d53-48a8-8eba-84aefb67fc7e)


## Tech Architecture

![1685500013](https://github.com/songseunghei/CapstoneProject/assets/80136506/cf73abfb-7f2d-40c6-a174-ca657a7301e6)


## ERD

![농글농글](https://github.com/songseunghei/CapstoneProject/assets/80136506/82bd8ddb-4fce-4796-9078-c990d13d5333)

## 기술 스택

- Android
    - ![Android Studio](https://img.shields.io/badge/Android%20Studio-%233DDC84?logo=androidstudio&logoColor=white)
      ![Jetpack](https://img.shields.io/badge/Jetpack%20-%234285F4?logo=jetpackcompose&logoColor=white)
      ![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=flat-square&logo=firebase&logoColor=black)
  
| 기술 스택 | 설명 |
|-----------|------|
| **Clean Architecture** | Domain Layer (비즈니스 로직, Usecase, Repository 포함), Data Layer (Repository 구현체, 데이터 입출력 Data Source, Entity 포함), Presentation Layer (MVVM 패턴 사용, UI 구성 및 View와 ViewModel로 구성) |
| **Dagger-Hilt** | Clean Architecture의 각 계층에서 필요한 객체 생성 및 의존성 주입을 위한 Dagger-Hilt 사용 |
| **Android Jetpack** | ViewModel, Navigation, Flow, LiveData, DataBinding 등을 포함하는 Android Jetpack 라이브러리 활용 |


## R&R
| Name | Kim Ji Won | [Song Seung hee](https://github.com/songseunghei) | Kim Ka Eun | Jeong Hye Ri |
| --- | --- | --- |--- | --- |
| Role | PM/ UI&UX Designer/Planner | Android Developer/Planner | Graphic Designer | UI&UX Designer |
| Profile Image | <img src="https://github.com/songseunghei/Nonggle/assets/80136506/9b3554cc-416b-49a6-ac9f-805c4c38d748" width="200"/> | <img src="https://github.com/AlwaysFighting/SeoulEducation_AppService/assets/87655596/3effd961-c190-4013-b46b-3429eb5a8f82" width="200"/> | <img src="https://github.com/songseunghei/Nonggle/assets/80136506/c912e606-377b-41e0-86fc-6af83fb8f7c0" width="200"/> | <img src="https://github.com/songseunghei/Nonggle/assets/80136506/b040d6f2-9c51-4d38-9402-a29cac7478e2" width="200"/> |




