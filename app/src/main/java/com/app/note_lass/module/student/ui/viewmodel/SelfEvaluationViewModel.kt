package com.app.note_lass.module.student.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.student.data.Evaluation.EvaluationAnswer
import com.app.note_lass.module.student.data.Evaluation.EvaluationQuestion
import com.app.note_lass.module.student.data.Evaluation.Evaluations
import com.app.note_lass.module.student.domain.usecase.self_evaluation.GetAnswersUseCase
import com.app.note_lass.module.student.domain.usecase.self_evaluation.GetQuestionsUseCase
import com.app.note_lass.module.student.domain.usecase.self_evaluation.ModifyAnswersUseCase
import com.app.note_lass.module.student.domain.usecase.self_evaluation.ModifyQuestionsUseCase
import com.app.note_lass.module.student.domain.usecase.self_evaluation.PostAnswersUseCase
import com.app.note_lass.module.student.domain.usecase.self_evaluation.PostQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SelfEvaluationViewModel@Inject constructor (
    val getQuestionsUseCase: GetQuestionsUseCase,
    val postQuestionsUseCase: PostQuestionsUseCase,
    val modifyQuestionsUseCase: ModifyQuestionsUseCase,
    val getAnswersUseCase: GetAnswersUseCase,
    val postAnswersUseCase: PostAnswersUseCase,
    val modifyAnswersUseCase: ModifyAnswersUseCase
): ViewModel() {
    private val _getQuestionsState = mutableStateOf(RequestState<List<EvaluationQuestion>>())
    val getQuestionState  = _getQuestionsState

    private val _postQuestionState = mutableStateOf(RequestState<Nothing>())
    val postQuestionState = _postQuestionState

    private val _modifyQuestionState = mutableStateOf(RequestState<Nothing>())
    val modifyQuestionState = _modifyQuestionState

    private val _getAnswersState = mutableStateOf(RequestState<List<Evaluations>>())
    val getAnswersState  = _getAnswersState

    private val _postAnswerState = mutableStateOf(RequestState<Nothing>())
    val postAnswerState = _postAnswerState

    private val _modifyAnswerState = mutableStateOf(RequestState<Nothing>())
    val modifyAnswerState = _modifyAnswerState
    init {
        getQuestions()
        getAnswers()
    }

    fun getQuestions() {
        getQuestionsUseCase().onEach { result ->

            when (result) {

                is Resource.Loading -> {
                    _getQuestionsState.value = RequestState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _getQuestionsState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                        result = result.data
                    )

                }

                is Resource.Error -> {
                    _getQuestionsState.value = RequestState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun postQuestions(questions : List<String>) {
        postQuestionsUseCase(questions).onEach { result ->

            when (result) {

                is Resource.Loading -> {
                    _postQuestionState.value = RequestState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _postQuestionState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                    )

                }

                is Resource.Error -> {
                    _postQuestionState.value = RequestState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun modifyStudentRecord(questions : List<EvaluationQuestion>) {
        modifyQuestionsUseCase(questions).onEach { result ->

            when (result) {

                is Resource.Loading -> {
                    _modifyQuestionState.value = RequestState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _modifyQuestionState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                    )

                }

                is Resource.Error -> {
                    _modifyQuestionState.value = RequestState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getAnswers() {
        getAnswersUseCase().onEach { result ->

            when (result) {

                is Resource.Loading -> {
                    _getAnswersState.value = RequestState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _getAnswersState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                        result = result.data
                    )

                }

                is Resource.Error -> {
                    _getAnswersState.value = RequestState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun postAnswers(questions : List<EvaluationAnswer>) {
        postAnswersUseCase(questions).onEach { result ->

            when (result) {

                is Resource.Loading -> {
                    _postAnswerState.value = RequestState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _postAnswerState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                    )

                }

                is Resource.Error -> {
                    _postAnswerState.value = RequestState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun modifyAnswers(questions : List<EvaluationAnswer>) {
        modifyAnswersUseCase(questions).onEach { result ->

            when (result) {

                is Resource.Loading -> {
                    _modifyAnswerState.value = RequestState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _modifyAnswerState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                    )

                }

                is Resource.Error -> {
                    _modifyAnswerState.value = RequestState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }
}