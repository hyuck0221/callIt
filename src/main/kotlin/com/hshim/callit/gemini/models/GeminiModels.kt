package com.hshim.callit.gemini.models

import com.google.gson.annotations.SerializedName

/**
 * Gemini API Request Models
 */
data class GeminiRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null
)

data class Content(
    val parts: List<Part>,
    val role: String = "user"
)

data class Part(
    val text: String
)

data class GenerationConfig(
    val temperature: Double? = null,
    val maxOutputTokens: Int? = null,
    val topP: Double? = null,
    val topK: Int? = null
)

/**
 * Gemini API Response Models
 */
data class GeminiResponse(
    val candidates: List<Candidate>?,
    val promptFeedback: PromptFeedback? = null,
    val error: GeminiError? = null
)

data class Candidate(
    val content: Content,
    val finishReason: String? = null,
    val safetyRatings: List<SafetyRating>? = null
)

data class SafetyRating(
    val category: String,
    val probability: String
)

data class PromptFeedback(
    val safetyRatings: List<SafetyRating>? = null
)

data class GeminiError(
    val code: Int,
    val message: String,
    val status: String
)

/**
 * Gemini API Model List Response
 */
data class ModelListResponse(
    val models: List<ModelInfo>?
)

data class ModelInfo(
    val name: String,
    val displayName: String? = null,
    val description: String? = null,
    val supportedGenerationMethods: List<String>? = null
) {
    fun getShortName(): String {
        // "models/gemini-2.0-flash-exp" -> "gemini-2.0-flash-exp"
        return name.removePrefix("models/")
    }
}
