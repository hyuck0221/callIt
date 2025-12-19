package com.hshim.callit.utils

import com.hshim.callit.settings.CallItSettings
import java.text.MessageFormat
import java.util.*

object MessageBundle {
    private const val BUNDLE_NAME = "messages.CallItBundle"

    private fun getBundle(): ResourceBundle {
        val language = CallItSettings.getInstance().messageLanguage
        val locale = when (language) {
            "ko" -> Locale.KOREAN
            "ja" -> Locale.JAPANESE
            "zh" -> Locale.CHINESE
            "fr" -> Locale.FRENCH
            else -> Locale.ENGLISH
        }
        return ResourceBundle.getBundle(BUNDLE_NAME, locale)
    }

    fun message(key: String, vararg params: Any): String {
        return try {
            val bundle = getBundle()
            val pattern = bundle.getString(key)
            if (params.isEmpty()) {
                pattern
            } else {
                MessageFormat.format(pattern, *params)
            }
        } catch (e: MissingResourceException) {
            "!$key!"
        } catch (e: Exception) {
            // 포맷팅 실패 시 원본 반환
            try {
                getBundle().getString(key)
            } catch (ex: Exception) {
                "!$key!"
            }
        }
    }
}
