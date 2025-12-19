package com.hshim.callit.gemini

/**
 * Gemini API 설정 클래스 (Spring 없이 사용 가능)
 */
data class GeminiProperties(
    val apiKey: String,
    val model: String = "gemini-2.5-flash-lite",
    val baseUrl: String = "https://generativelanguage.googleapis.com",
    val temperature: Double = 0.7,
    val maxOutputTokens: Int = 1024,
    val timeout: Long = 30000L // 30 seconds
)
