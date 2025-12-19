package com.hshim.callit.actions

import com.hshim.callit.services.GeminiService
import com.hshim.callit.settings.CallItSettings
import com.hshim.callit.utils.LanguageDetector
import com.hshim.callit.utils.MessageBundle
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import kotlinx.coroutines.runBlocking
import javax.swing.JList
import javax.swing.JScrollPane

class NameSuggestionAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
        val project = e.project ?: return

        val selectedText = editor.selectionModel.selectedText

        if (selectedText.isNullOrBlank()) {
            Messages.showWarningDialog(
                MessageBundle.message("error.no.selection"),
                MessageBundle.message("dialog.no.selection.title")
            )
            return
        }

        val geminiService = project.service<GeminiService>()

        if (!geminiService.isConfigured()) {
            Messages.showErrorDialog(
                MessageBundle.message("error.no.api.key"),
                MessageBundle.message("dialog.title.error")
            )
            return
        }

        val language = LanguageDetector.detectLanguage(psiFile)

        ProgressManager.getInstance().run(object : Task.Backgroundable(project, MessageBundle.message("info.generating"), true) {
            private var suggestions: List<String>? = null
            private var errorMessage: String? = null

            override fun run(indicator: ProgressIndicator) {
                indicator.isIndeterminate = true
                runBlocking {
                    geminiService.suggestNames(selectedText, language)
                        .onSuccess { suggestions = it }
                        .onFailure { errorMessage = it.message }
                }
            }

            override fun onSuccess() {
                suggestions?.let {
                    showSuggestionsDialog(it, editor, project)
                } ?: run {
                    val errorText = errorMessage ?: "Unknown error"
                    Messages.showErrorDialog(
                        "${MessageBundle.message("error.api.call.failed", "")}:\n\n$errorText",
                        MessageBundle.message("dialog.title.error")
                    )
                }
            }

            override fun onCancel() {
                // User cancelled the operation
            }
        })
    }

    private fun showSuggestionsDialog(suggestions: List<String>, editor: Editor, project: Project) {
        val maxSuggestions = CallItSettings.getInstance().maxSuggestions

        // Max Suggestions가 1이면 바로 적용
        if (maxSuggestions == 1 && suggestions.isNotEmpty()) {
            applySuggestion(suggestions.first(), editor, project)
            return
        }

        // 여러 개의 제안이 있으면 선택 다이얼로그 표시
        val selectedValue = Messages.showEditableChooseDialog(
            MessageBundle.message("dialog.title.name.suggestion"),
            MessageBundle.message("dialog.title.name.suggestion"),
            Messages.getQuestionIcon(),
            suggestions.toTypedArray(),
            suggestions.firstOrNull() ?: "",
            null
        )

        if (selectedValue != null && selectedValue.isNotEmpty()) {
            applySuggestion(selectedValue, editor, project)
        }
    }

    private fun applySuggestion(suggestion: String, editor: Editor, project: Project) {
        WriteCommandAction.runWriteCommandAction(project) {
            val selectionModel = editor.selectionModel
            val document = editor.document
            val startOffset = selectionModel.selectionStart
            val endOffset = selectionModel.selectionEnd

            document.replaceString(startOffset, endOffset, suggestion)
            selectionModel.removeSelection()
        }
    }

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        val hasSelection = editor?.selectionModel?.hasSelection() == true
        e.presentation.isEnabledAndVisible = hasSelection
    }
}