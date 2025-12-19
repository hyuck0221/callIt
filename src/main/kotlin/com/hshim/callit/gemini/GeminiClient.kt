package com.hshim.callit.gemini

import com.google.gson.Gson
import com.hshim.callit.gemini.models.*
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

/**
 * Gemini API 클라이언트 (Spring 의존성 없음)
 * Kemi 라이브러리의 핵심 로직을 추출한 구현
 */
class GeminiClient(
    private val properties: GeminiProperties
) {
    private val httpClient: HttpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofMillis(properties.timeout))
        .build()

    private val gson = Gson()

    /**
     * Gemini API에 질문을 전송하고 응답을 받음
     */
    fun ask(prompt: String): String {
        val request = buildRequest(prompt)
        val response = sendRequest(request)
        return parseResponse(response)
    }

    /**
     * 사용 가능한 Gemini 모델 목록 조회
     */
    fun listModels(): List<ModelInfo> {
        val url = "${properties.baseUrl}/v1/models?key=${properties.apiKey}"

        val httpRequest = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(Duration.ofMillis(properties.timeout))
            .header("Content-Type", "application/json")
            .GET()
            .build()

        try {
            val httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

            if (httpResponse.statusCode() != 200) {
                throw GeminiApiException("Failed to fetch models: ${httpResponse.statusCode()}")
            }

            val response = gson.fromJson(httpResponse.body(), ModelListResponse::class.java)
            val models = response.models?.filter {
                it.supportedGenerationMethods?.contains("generateContent") == true
            } ?: emptyList()

            return models
        } catch (e: GeminiApiException) {
            throw e
        } catch (e: Exception) {
            throw GeminiApiException("Error fetching models: ${e.message}")
        }
    }

    /**
     * API 요청 객체 생성
     */
    private fun buildRequest(prompt: String): GeminiRequest {
        return GeminiRequest(
            contents = listOf(
                Content(
                    parts = listOf(Part(text = prompt)),
                    role = "user"
                )
            ),
            generationConfig = GenerationConfig(
                temperature = properties.temperature,
                maxOutputTokens = properties.maxOutputTokens
            )
        )
    }

    /**
     * HTTP 요청 전송
     */
    private fun sendRequest(geminiRequest: GeminiRequest): GeminiResponse {
        val url = "${properties.baseUrl}/v1/models/${properties.model}:generateContent?key=${properties.apiKey}"

        val requestBody = gson.toJson(geminiRequest)

        val httpRequest = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(Duration.ofMillis(properties.timeout))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        try {
            val httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

            if (httpResponse.statusCode() != 200) {
                val errorBody = httpResponse.body()

                val errorResponse = try {
                    gson.fromJson(errorBody, GeminiResponse::class.java)
                } catch (e: Exception) {
                    throw GeminiApiException(
                        "API request failed with status ${httpResponse.statusCode()}.\nResponse: $errorBody"
                    )
                }

                throw GeminiApiException(
                    "API Error: ${errorResponse.error?.message ?: "Unknown error"}\nStatus: ${errorResponse.error?.status ?: "N/A"}",
                    errorResponse.error?.code ?: httpResponse.statusCode()
                )
            }

            return gson.fromJson(httpResponse.body(), GeminiResponse::class.java)
        } catch (e: GeminiApiException) {
            throw e
        } catch (e: Exception) {
            throw GeminiApiException("Network error: ${e.message}", null)
        }
    }

    /**
     * API 응답 파싱
     */
    private fun parseResponse(response: GeminiResponse): String {
        if (response.error != null) {
            throw GeminiApiException(response.error.message, response.error.code)
        }

        val candidate = response.candidates?.firstOrNull()
            ?: throw GeminiApiException("No candidates in response")

        val text = candidate.content.parts.firstOrNull()?.text
            ?: throw GeminiApiException("No text in response")

        return text
    }
}

/**
 * Gemini API 예외 클래스
 */
class GeminiApiException(
    message: String,
    val errorCode: Int? = null
) : Exception(message)
