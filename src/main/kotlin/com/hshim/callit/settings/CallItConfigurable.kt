package com.hshim.callit.settings

import com.hshim.callit.gemini.GeminiClient
import com.hshim.callit.gemini.GeminiProperties
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import javax.swing.*

class CallItConfigurable : Configurable {
    private var settingsComponent: CallItSettingsComponent? = null

    override fun createComponent(): JComponent {
        settingsComponent = CallItSettingsComponent()

        // 설정 화면 열릴 때 API 키가 있으면 자동으로 모델 목록 로드
        val apiKey = CallItSettings.getInstance().geminiApiKey
        if (apiKey.isNotBlank()) {
            settingsComponent?.loadModelsInBackground(apiKey)
        }

        return settingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = CallItSettings.getInstance()
        return settingsComponent?.apiKeyField?.password?.concatToString() != settings.geminiApiKey ||
                settingsComponent?.maxSuggestionsField?.text?.toIntOrNull() != settings.maxSuggestions ||
                settingsComponent?.getLanguageCode() != settings.messageLanguage ||
                settingsComponent?.modelComboBox?.selectedItem != settings.selectedModel
    }

    override fun apply() {
        val settings = CallItSettings.getInstance()
        settingsComponent?.let {
            settings.geminiApiKey = it.apiKeyField.password.concatToString()
            settings.maxSuggestions = it.maxSuggestionsField.text.toIntOrNull() ?: 5
            settings.messageLanguage = it.getLanguageCode()
            settings.selectedModel = it.modelComboBox.selectedItem as? String ?: "gemini-2.5-flash-lite"
        }
    }

    override fun reset() {
        val settings = CallItSettings.getInstance()
        settingsComponent?.let {
            it.apiKeyField.text = settings.geminiApiKey
            it.maxSuggestionsField.text = settings.maxSuggestions.toString()
            it.setLanguageByCode(settings.messageLanguage)
            it.modelComboBox.selectedItem = settings.selectedModel
        }
    }

    override fun getDisplayName(): String = "CallIt"

    override fun disposeUIResources() {
        settingsComponent = null
    }

    private class CallItSettingsComponent {
        val panel: JPanel
        val apiKeyField = JBPasswordField()
        val maxSuggestionsField = JBTextField()
        val languageComboBox = ComboBox(arrayOf("English", "한국어", "日本語", "中文", "Français"))
        val modelComboBox = ComboBox(getDefaultModels())
        val refreshModelsButton = JButton("Refresh Models")

        private val languageMap = mapOf(
            "English" to "en",
            "한국어" to "ko",
            "日本語" to "ja",
            "中文" to "zh",
            "Français" to "fr"
        )

        private val reverseLanguageMap = mapOf(
            "en" to "English",
            "ko" to "한국어",
            "ja" to "日本語",
            "zh" to "中文",
            "fr" to "Français"
        )

        fun getLanguageCode(): String {
            return languageMap[languageComboBox.selectedItem as? String] ?: "en"
        }

        fun setLanguageByCode(code: String) {
            languageComboBox.selectedItem = reverseLanguageMap[code] ?: "English"
        }

        init {
            maxSuggestionsField.text = "5"

            // API 키 입력 후 포커스를 잃으면 모델 목록 자동 새로고침
            apiKeyField.addFocusListener(object : FocusAdapter() {
                override fun focusLost(e: FocusEvent?) {
                    val apiKey = apiKeyField.password.concatToString()
                    if (apiKey.isNotBlank()) {
                        loadModels(apiKey)
                    }
                }
            })

            // 수동 새로고침 버튼
            refreshModelsButton.addActionListener {
                val apiKey = apiKeyField.password.concatToString()
                if (apiKey.isBlank()) {
                    JOptionPane.showMessageDialog(
                        panel,
                        "Please enter API Key first",
                        "API Key Required",
                        JOptionPane.WARNING_MESSAGE
                    )
                } else {
                    loadModels(apiKey)
                }
            }

            val modelPanel = JPanel()
            modelPanel.layout = BoxLayout(modelPanel, BoxLayout.X_AXIS)
            modelPanel.add(modelComboBox)
            modelPanel.add(Box.createHorizontalStrut(5))
            modelPanel.add(refreshModelsButton)

            panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(JBLabel("Gemini API Key:"), apiKeyField, 1, false)
                .addLabeledComponent(JBLabel("Gemini Model:"), modelPanel, 1, false)
                .addLabeledComponent(JBLabel("Max Suggestions:"), maxSuggestionsField, 1, false)
                .addLabeledComponent(JBLabel("Message Language:"), languageComboBox, 1, false)
                .addComponentFillVertically(JPanel(), 0)
                .panel
        }

        fun loadModelsInBackground(apiKey: String) {
            Thread {
                try {
                    val properties = GeminiProperties(
                        apiKey = apiKey,
                        model = "gemini-2.5-flash-lite",
                        timeout = 10000L
                    )
                    val client = GeminiClient(properties)
                    val models = client.listModels()

                    SwingUtilities.invokeLater {
                        val currentSelection = modelComboBox.selectedItem
                        modelComboBox.removeAllItems()

                        models.forEach { modelInfo ->
                            modelComboBox.addItem(modelInfo.getShortName())
                        }

                        // 기존 선택 유지 또는 첫 번째 항목 선택
                        if (currentSelection != null && models.any { it.getShortName() == currentSelection }) {
                            modelComboBox.selectedItem = currentSelection
                        } else if (modelComboBox.itemCount > 0) {
                            modelComboBox.selectedIndex = 0
                        }
                    }
                } catch (e: Exception) {
                    // 자동 로드 실패는 조용히 무시
                }
            }.start()
        }

        private fun loadModels(apiKey: String) {
            refreshModelsButton.isEnabled = false
            refreshModelsButton.text = "Loading..."

            Thread {
                try {
                    val properties = GeminiProperties(
                        apiKey = apiKey,
                        model = "gemini-2.5-flash-lite",
                        timeout = 10000L
                    )
                    val client = GeminiClient(properties)
                    val models = client.listModels()

                    SwingUtilities.invokeLater {
                        val currentSelection = modelComboBox.selectedItem
                        modelComboBox.removeAllItems()

                        models.forEach { modelInfo ->
                            modelComboBox.addItem(modelInfo.getShortName())
                        }

                        // 기존 선택 유지 또는 첫 번째 항목 선택
                        if (currentSelection != null && models.any { it.getShortName() == currentSelection }) {
                            modelComboBox.selectedItem = currentSelection
                        } else if (modelComboBox.itemCount > 0) {
                            modelComboBox.selectedIndex = 0
                        }

                        refreshModelsButton.text = "Refresh Models"
                        refreshModelsButton.isEnabled = true

                        JOptionPane.showMessageDialog(
                            panel,
                            "Loaded ${models.size} models",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                        )
                    }
                } catch (e: Exception) {
                    SwingUtilities.invokeLater {
                        refreshModelsButton.text = "Refresh Models"
                        refreshModelsButton.isEnabled = true

                        JOptionPane.showMessageDialog(
                            panel,
                            "Failed to load models: ${e.message}",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        )
                    }
                }
            }.start()
        }

        companion object {
            private fun getDefaultModels(): Array<String> {
                return arrayOf(
                    "gemini-3.0-pro",
                    "gemini-2.5-pro",
                    "gemini-2.5-flash",
                    "gemini-2.5-flash-lite",
                    "gemini-2.0-flash",
                    "gemini-2.0-flash-lite",
                )
            }
        }
    }
}
