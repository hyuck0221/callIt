package com.hshim.callit.services

import com.hshim.callit.config.GeminiConfig
import com.hshim.callit.gemini.GeminiApiException
import com.hshim.callit.prompts.PromptTemplates
import com.hshim.callit.settings.CallItSettings
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Service(Service.Level.PROJECT)
class GeminiService(private val project: Project) {

    /**
     * Gemini API를 사용하여 코드 기반 변수명/함수명 제안
     */
    suspend fun suggestNames(code: String, language: String): Result<List<String>> = withContext(Dispatchers.IO) {
        try {
            val client = GeminiConfig.createClient()
                ?: return@withContext Result.failure(Exception("API key not configured"))

            val settings = CallItSettings.getInstance()
            val prompt = PromptTemplates.buildNamingPrompt(
                code = code,
                language = language,
                maxSuggestions = settings.maxSuggestions
            )

            val response = client.ask(prompt)

            // 응답을 줄 단위로 분할하여 리스트로 변환
            val suggestions = response
                .split("\n")
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .take(settings.maxSuggestions)

            if (suggestions.isEmpty()) {
                Result.failure(Exception("No suggestions generated"))
            } else {
                Result.success(suggestions)
            }
        } catch (e: GeminiApiException) {
            Result.failure(Exception("Gemini API Error: ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * API 키 설정 확인
     */
    fun isConfigured(): Boolean {
        return GeminiConfig.isConfigured()
    }
}
