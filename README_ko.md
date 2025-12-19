[🇰🇷 한국어](https://github.com/hyuck0221/callIt/blob/main/README_ko.md) | [🇯🇵 日本語](https://github.com/hyuck0221/callIt/blob/main/README_ja.md) | [🇨🇳 中文](https://github.com/hyuck0221/callIt/blob/main/README_zh.md) | [🇫🇷 Français](https://github.com/hyuck0221/callIt/blob/main/README_fr.md)

<img src="https://raw.githubusercontent.com/hyuck0221/storage/main/callit/logo.png" alt="CallIt logo" width="200">

# CallIt - AI 기반 네이밍 어시스턴트

**CallIt**은 Google Gemini AI를 기반으로 한 지능형 네이밍 어시스턴트로, 언어별 명명 규칙을 따르는 완벽한 변수명과 함수명을 생성해줍니다.

<img src="https://raw.githubusercontent.com/hyuck0221/storage/main/callit/capture.gif" alt="CallIt Capture">

## ✨ 주요 기능

- **AI 기반 제안** - Google Gemini AI를 사용한 스마트한 네이밍 제안
- **언어 인식** - Java, Kotlin, JavaScript, TypeScript, Python, Go, Rust, C#, Swift, Ruby, PHP 등의 명명 규칙 자동 적용
- **다양한 모델** - 여러 Gemini 모델 선택 가능 (gemini-2.5-flash-lite, gemini-2.0-flash-exp 등)
- **다국어 UI** - English, 한국어, 日本語, 中文, Français 지원
- **커스터마이징** - 최대 제안 개수, 모델 선택, 언어 설정 가능
- **빠른 적용** - 제안된 이름을 선택하여 코드에 즉시 적용

## 🚀 사용 방법

1. **설정 → 도구 → CallIt**에서 Gemini API 키 설정
2. 에디터에서 코드 선택
3. 우클릭하여 **"Suggest Name with CallIt"** 선택 또는 `Ctrl+Shift+C` 단축키 사용
4. AI가 생성한 제안 중 선택하여 즉시 적용

## ⚙️ 설정

- **Gemini API Key** - AI 제안에 필요 (필수)
- **Model Selection** - 사용 가능한 Gemini 모델 새로고침 및 선택
- **Max Suggestions** - 1로 설정하면 즉시 적용, 높게 설정하면 여러 옵션 제공
- **Message Language** - 선호하는 UI 언어 선택

## 📝 참고사항

유효한 Google Gemini API 키가 필요합니다. [Google AI Studio](https://ai.google.dev/)에서 발급받으세요.
