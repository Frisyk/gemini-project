package com.dicoding.chatgemini

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val model: GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.apiKey
    )

    private val _generatedContent = MutableLiveData<String>()
    val generatedContent: LiveData<String> get() = _generatedContent

    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                // Generate content and post the result
                val result = model.generateContent(question)
                _generatedContent.postValue(result.text)
            } catch (e: Exception) {
                // Handle errors
                _generatedContent.postValue("Error generating response: ${e.message}")
            }
        }
    }
}
