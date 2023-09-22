package com.app.note_lass.module.signup.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SchoolInfoViewModel : ViewModel(){
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _schools = MutableStateFlow(schoolNames)
    val schools = searchText
        //Flow에서 값을 변환하지 않고 각 값을 처리하기 위한 연산자
        .onEach { _isSearching.update { true } }
        .combine(_schools) { text, schools ->
            if(text.isBlank()) {
                schools
            } else {
                delay(2000L)
                schools.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _schools.value
        )
    //stateIn : flow를 변경 가능한 stateFlow로 변환해주는 Flow.

    fun onSearchTextChange(text: String) {
        Log.e("onSearchText",text)

        _searchText.value = text
    }
}

data class SchoolName(
    val schoolName: String,
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            schoolName,
            "${schoolName.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

private val schoolNames = listOf(
   SchoolName("광남고등학교"),
    SchoolName("휘문고등학교"),
    SchoolName("숙명여자고등학교"),
    SchoolName("마이스터고등학교"),

    )
