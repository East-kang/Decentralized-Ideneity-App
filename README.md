# 석사 연구 1: 메타버스 환경에서 개인화 아바타와 자기 주권 신원을 통합·관리하는 앱

안드로이드 스튜디오 기반 메타버스 환경에서 개인화 아바타와 **자기 주권 신원(SSI)**을 통합·관리하는 애플리케이션입니다.
Firebase 실시간 통신과 QR/서명/암호화 흐름을 통해 **Holder–Issuer–Verifier** 간 검증과 **VC/VP** 발급·제출 과정을 구현했습니다.

<br>

## 📌 프로젝트 소개
**"메타버스 환경에서 SSI 기반 신원 검증과 아바타 통합 관리"**를 주제로 한 안드로이드 앱입니다. 사용자는 Holder(신원 소유자)·Issuer(발급자)·Verifier(검증자)의 역할에 따라 **검증 요청–승인–제출**의 전 과정을 수행하며, Firebase Realtime Database를 통한 **실시간 데이터 송수신**, ZXing 기반 **QR 생성/스캔**, **디지털 서명**, **AES/CBC 암복호화** 기능을 제공합니다. 발급된 **VC(Verifiable Credential)** 는 기기 내 저장 및 QR로 교환할 수 있고, 필요한 항목만 골라 **VP(Verifiable Presentation)** 로 제출할 수 있습니다.

<br>

## ⚙️ 기술 스택
- **언어/환경**: Java (Android), Android Studio
- **실시간 DB**: Firebase Realtime Database (`com.google.firebase:firebase-database`)
- **QR**: ZXing (`com.journeyapps:zxing-android-embedded`)
- **암호화**: AES/CBC/PKCS5Padding, Base64
- **저장소**: SharedPreferences (DID/VC 키-값 저장)
- **UI**: AndroidX AppCompat, ViewBinding(일부, `SignPad`), Activity/Intent, ConstraintLayout
- **미디어/스토리지**: 갤러리 연동(영상 선택), 서명 이미지 저장(런타임 권한)

<br>

## 🚀 주요 기능
**1. 역할 선택 & 실시간 검증 흐름 (Holder / Issuer / Verifier)**
- `InitActivity.java` → `Choice_Roll.java`에서 역할 진입
- **Holder**: `Holder.java`
   - Firebase 경로(`text`)로 검증 요청 전송, 클립보드 복붙, AES 암호화 지원
- **Issuer**: `Issuer.java`
   - Holder의 요청 수신 → **Block-chain Document 검증** → **챌린지/응답 처리**, AES 키/IV 사용
- **Verifier**: `Verifier.java`
   - 제출된 **VP 검증** 후 서비스 이용 허가
- 공통: `ValueEventListener`로 **실시간 데이터 수신** 및 UI 업데이트

**2. VC 발급 & DID 관리**
- 사용자 정보 입력: `Set_Information.java` (이름/성별/생년월일/국적/학력 등)
- 아바타·외형 정보 입력: `FaceModel.java`, `BodySize.java`, `Avatar_Scan.java`, `Body_QR.java`
- **VC 요청/생성**: `VC_Request.java`
   - 입력 항목을 **QR 코드**로 생성(ZXing) 및 화면 표시
   - 이후 `SignPad`/`QR_Scan`으로 흐름 이동
- **내 DID 보기**: `My_DID.java`
   - `SharedPreferences`에 저장된 `DID_1` ~ `DID_7`, `key1`, `hyperlink`를 불러와 출력
- 초기 진입: `InitActivity.java`
   - 로컬에 **VC 존재 여부** 점검 → 있으면 `My_DID`로 전달, 없으면 안내 토스트

**3. VP 생성 & 선택 제출**
- `VP.java`
   - 체크박스(**선택적 공개**)로 VC 항목 중 제출할 정보 선택
   - 선택 항목을 모아 **VP** 구성
- `Sub_VP.java`
   - VP 제출 전후 세부 흐름/전달 데이터 관리

**4. QR 코드 생성 & 스캔**
- **생성**: `VC_Request.java`
   - ZXing `MultiFormatWriter`로 문서/링크/키를 합친 **QR 이미지 생성**
- **스캔**: `QR_Scan.java`, `QR_Scan2.java`
   - `IntentIntegrator`로 스캔 → 디코딩/파싱 → 다음 단계 Activity로 전달

**5. 디지털 서명(전자서명) 처리**
- `SignPad.java`
   - **서명 패드**에서 사용자가 그린 서명을 **Bitmap**으로 저장
   - 갤러리(미디어스토어) 저장, **런타임 권한**(WRITE/READ) 처리
   - VC 데이터(이름/키/하이퍼링크 등)와 함께 다음 단계로 전달

**6. 보안 유틸 & 데이터 영속성**
- **암·복호화**: `Holder.java` / `Issuer.java`에 **AES/CBC/PKCS5Padding + Base64** 유틸 함수
- **로컬 저장**: `SharedPreferences`로 DID/키/하이퍼링크 **영구 저장**
- **클립보드**: 검증 코드/키를 **복사/붙여넣기** 가능(간편 교환)

**7. 화면 전환 & 예외/UX**
- **Intent Extras**로 **다단계 화면 간 데이터 전달**
- 토스트 안내, 입력 초기화, 붙여넣기 처리 등 **UX 보완**
- 레이아웃(XML): `res/layout/activity_*.xml` (역할/입력/QR/검증/서명/조회 등 전 화면 구성)

<br>

## ※ 시스템 동작 예시 흐름 (요약)
1. `InitActivity` → 역할 선택(`Choice_Roll`)
2. Holder가 Firebase로 검증 요청 전송 → Issuer가 확인·승인(AES 챌린지/응답)
3. 사용자 정보/외형 입력(`Set_Information`, `BodySize`, `FaceModel`)
4. `VC_Request`에서 VC 정보를 QR로 생성 → `SignPad`로 서명 저장 or `QR_Scan`으로 교환
5. 필요한 항목만 선택해 `VP` 생성(체크박스) → `Verifier`가 VP 검증 후 서비스 허가
6. 발급된 DID/VC/하이퍼링크는 `SharedPreferences`에 보존, `My_DID`에서 확인
