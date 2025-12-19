[🇰🇷 한국어](https://github.com/shimhyuck/callIt/blob/main/README_ko.md) | [🇯🇵 日本語](https://github.com/shimhyuck/callIt/blob/main/README_ja.md) | [🇨🇳 中文](https://github.com/shimhyuck/callIt/blob/main/README_zh.md) | [🇫🇷 Français](https://github.com/shimhyuck/callIt/blob/main/README_fr.md)

<img src="https://raw.githubusercontent.com/hyuck0221/storage/main/callit/logo.png" alt="CallIt logo" width="200">

# CallIt - AI搭載ネーミングアシスタント

**CallIt**は、Google Gemini AIを活用した知的ネーミングアシスタントで、言語固有の命名規則に従った完璧な変数名と関数名を生成します。

<img src="https://raw.githubusercontent.com/hyuck0221/storage/main/callit/capture.gif" alt="CallIt Capture">

## ✨ 主な機能

- **AI搭載の提案** - Google Gemini AIを使用したスマートなネーミング提案
- **言語認識** - Java、Kotlin、JavaScript、TypeScript、Python、Go、Rust、C#、Swift、Ruby、PHPなどの命名規則を自動適用
- **複数モデル** - 様々なGeminiモデルから選択可能（gemini-2.5-flash-lite、gemini-2.0-flash-expなど）
- **多言語UI** - English、한국어、日本語、中文、Français対応
- **カスタマイズ可能** - 最大提案数、モデル選択、言語設定が可能
- **クイック適用** - 提案された名前を選択してコードに即座に適用

## 🚀 使い方

1. **設定 → ツール → CallIt**でGemini APIキーを設定
2. エディタでコードを選択
3. 右クリックして**「Suggest Name with CallIt」**を選択、または`Ctrl+Shift+C`ショートカットを使用
4. AIが生成した提案から選択して即座に適用

## ⚙️ 設定

- **Gemini API Key** - AI提案に必要（必須）
- **Model Selection** - 利用可能なGeminiモデルを更新して選択
- **Max Suggestions** - 1に設定すると即座に適用、高く設定すると複数のオプションを提供
- **Message Language** - 希望するUI言語を選択

## 📝 注意事項

有効なGoogle Gemini APIキーが必要です。[Google AI Studio](https://ai.google.dev/)で取得してください。
