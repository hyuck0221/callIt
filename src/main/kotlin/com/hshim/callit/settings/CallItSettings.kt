package com.hshim.callit.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service(Service.Level.APP)
@State(
    name = "CallItSettings",
    storages = [Storage("CallItSettings.xml")]
)
class CallItSettings : PersistentStateComponent<CallItSettings.State> {

    data class State(
        var geminiApiKey: String = "",
        var maxSuggestions: Int = 5,
        var messageLanguage: String = "en", // ko, en, ja, zh, fr
        var selectedModel: String = "gemini-2.5-flash-lite"
    )

    private var myState = State()

    override fun getState(): State = myState

    override fun loadState(state: State) {
        myState = state
    }

    var geminiApiKey: String
        get() = myState.geminiApiKey
        set(value) {
            myState.geminiApiKey = value
        }

    var maxSuggestions: Int
        get() = myState.maxSuggestions
        set(value) {
            myState.maxSuggestions = value
        }

    var messageLanguage: String
        get() = myState.messageLanguage
        set(value) {
            myState.messageLanguage = value
        }

    var selectedModel: String
        get() = myState.selectedModel
        set(value) {
            myState.selectedModel = value
        }

    companion object {
        fun getInstance(): CallItSettings {
            return com.intellij.openapi.components.service()
        }
    }
}
