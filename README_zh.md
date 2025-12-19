[🇰🇷 한국어](https://github.com/shimhyuck/callIt/blob/main/README_ko.md) | [🇯🇵 日本語](https://github.com/shimhyuck/callIt/blob/main/README_ja.md) | [🇨🇳 中文](https://github.com/shimhyuck/callIt/blob/main/README_zh.md) | [🇫🇷 Français](https://github.com/shimhyuck/callIt/blob/main/README_fr.md)

<img src="https://raw.githubusercontent.com/hyuck0221/storage/main/callit/logo.png" alt="CallIt logo" width="200">

# CallIt - AI驱动的命名助手

**CallIt**是一个由Google Gemini AI驱动的智能命名助手，可帮助您生成遵循特定语言命名约定的完美变量名和函数名。

<img src="https://raw.githubusercontent.com/hyuck0221/storage/main/callit/capture.gif" alt="CallIt Capture">

## ✨ 主要功能

- **AI驱动的建议** - 使用Google Gemini AI获取智能命名建议
- **语言感知** - 自动应用Java、Kotlin、JavaScript、TypeScript、Python、Go、Rust、C#、Swift、Ruby、PHP等语言的命名约定
- **多种模型** - 可选择各种Gemini模型（gemini-2.5-flash-lite、gemini-2.0-flash-exp等）
- **多语言UI** - 支持English、한국어、日本語、中文、Français
- **可定制** - 配置最大建议数、模型选择和语言偏好
- **快速应用** - 选择建议的名称并立即应用到代码中

## 🚀 使用方法

1. 在**设置 → 工具 → CallIt**中配置Gemini API密钥
2. 在编辑器中选择代码
3. 右键选择**"Suggest Name with CallIt"**或按`Ctrl+Shift+C`快捷键
4. 从AI生成的建议中选择并立即应用

## ⚙️ 配置

- **Gemini API Key** - AI建议所需（必需）
- **Model Selection** - 刷新并选择可用的Gemini模型
- **Max Suggestions** - 设置为1可立即应用，设置更高可提供多个选项
- **Message Language** - 选择您喜欢的UI语言

## 📝 注意事项

需要有效的Google Gemini API密钥。请在[Google AI Studio](https://ai.google.dev/)获取。
