package com.hshim.callit.config

import com.hshim.callit.gemini.GeminiClient
import com.hshim.callit.gemini.GeminiProperties
import com.hshim.callit.settings.CallItSettings

object GeminiConfig {

    fun createClient(): GeminiClient? {
        val settings = CallItSettings.getInstance()
        val apiKey = settings.geminiApiKey

        if (apiKey.isBlank()) {
            return null
        }

        val properties = GeminiProperties(
            apiKey = apiKey,
            model = settings.selectedModel,
            temperature = 0.7,
            maxOutputTokens = 1024,
            timeout = 30000L
        )

        return GeminiClient(properties)
    }

    fun isConfigured(): Boolean {
        return CallItSettings.getInstance().geminiApiKey.isNotBlank()
    }
}
