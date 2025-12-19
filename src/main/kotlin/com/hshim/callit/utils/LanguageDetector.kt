package com.hshim.callit.utils

import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.PsiFile

object LanguageDetector {

    fun detectLanguage(psiFile: PsiFile?): String {
        if (psiFile == null) return "unknown"

        val fileType: FileType = psiFile.fileType
        val extension = psiFile.virtualFile?.extension?.lowercase() ?: ""

        return when {
            fileType.name.contains("Java", ignoreCase = true) -> "java"
            fileType.name.contains("Kotlin", ignoreCase = true) -> "kotlin"
            fileType.name.contains("JavaScript", ignoreCase = true) -> "javascript"
            fileType.name.contains("TypeScript", ignoreCase = true) -> "typescript"
            fileType.name.contains("Python", ignoreCase = true) -> "python"
            fileType.name.contains("Go", ignoreCase = true) -> "go"
            fileType.name.contains("Rust", ignoreCase = true) -> "rust"
            fileType.name.contains("C#", ignoreCase = true) -> "csharp"
            fileType.name.contains("Swift", ignoreCase = true) -> "swift"
            fileType.name.contains("Ruby", ignoreCase = true) -> "ruby"
            fileType.name.contains("PHP", ignoreCase = true) -> "php"
            extension == "java" -> "java"
            extension == "kt" || extension == "kts" -> "kotlin"
            extension == "js" || extension == "jsx" -> "javascript"
            extension == "ts" || extension == "tsx" -> "typescript"
            extension == "py" -> "python"
            extension == "go" -> "go"
            extension == "rs" -> "rust"
            extension == "cs" -> "csharp"
            extension == "swift" -> "swift"
            extension == "rb" -> "ruby"
            extension == "php" -> "php"
            else -> "unknown"
        }
    }
}
