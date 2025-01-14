# Decentralized-identity-App

안드로이드 스튜디오 기반 메타버스 환경에서 개인화된 아바타와 자기 주권 신원(SSI)을 통합 및 관리하는 애플리케이션 개발.

※ 시스템 동작 흐름 ※
1. 탈중앙화 신원 관리 시스템의 주요 주체인 Holder(신원 소유자인 사용자), Issuer(Holder를 검증하고 SSI 제작을 승인하는 발급자), Verifier(개별 서비스 관리자이며 Holder의 SSI를 확인한 후 서비스를 제공하는 검증자)는 'Document'와 'Identifiers'를 생성하기 위해 Klaytn 블록체인용 암호화폐 지갑인 Kaikas 계정을 생성.
2. 사용자는 App 내에서 세 주체 중 Holder의 역할을 선택.
3. Firebase 기반의 실시간 데이터 송수신 기능을 활용하여, Holder는 Issuer에게 본인에 대한 검증 요청을 전송하고, Issuer는 Holder의 Block-chain Document를 확인하여 검증. 검증은 디지털 지갑 거래를 통해 수행.
4. 검증 완료 후, 사용자는 이름, 성별, 출생일, 범죄 이력, 국적, 학력 등 개인 정보와 머리 모델 영상, 신체 치수(키, 어깨 너비, 허리 너비, 골반 너비) 등 외형 정보를 App에 입력.
   이를 통해 검증된 크레덴셜(Verifiable Credential, VC)을 생성 및 발급받음.
   생성된 개인화된 아바타는 분산 파일 저장 시스템에 저장되고, Content-address hyperlink와 통합하여 SSI 아바타를 생성. 이를 사용자에게 VC 형태로 발행.
5. 사용자는 VC를 필요에 따라 선택하여 검증된 프레젠테이션(Verifiable Presentation, VP)으로 변환 후 Verifier에게 제출.
6. Verifier는 사용자의 VP를 검증하고 서비스 이용을 허가.

※ 기술적 구현 ※
1. App UI 설계 및 개발.
2. 텍스트 복사 및 붙여넣기 기능.
3. Firebase와 Android Studio 연동을 통한 실시간 데이터 송수신 기능.
4. 텍스트 암호화 및 복호화 기능.
5. 화면 전환 시 데이터 전이 및 휘발성 여부 처리.
6. 갤러리와 App 연동을 통한 동영상 가져오기 기능.
7. Sign Pad를 활용한 디지털 서명 생성 및 갤러리에 서명 저장 기능.
8. 체크박스를 활용한 데이터 선택 기능.
