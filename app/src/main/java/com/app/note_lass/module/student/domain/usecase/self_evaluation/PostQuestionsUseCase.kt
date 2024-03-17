package com.app.note_lass.module.student.domain.usecase.self_evaluation

import androidx.datastore.core.DataStore
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.domain.repository.StudentRepository
import com.app.note_lass.module.student.data.Evaluation.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostQuestionsUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
    val dataStore : DataStore<Token>,
    private val dataGroupStore : DataStore<GroupInfo>
) {

    operator fun invoke(questions : List<String>) : Flow<Resource<NoteResponseBody<Nothing>>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"
            val groupId= dataGroupStore.data.first().groupId
            val evaluationQuestions = questions.map {
                Question(it)
            }

            emit(Resource.Loading())

            val recordResponse = studentRepository.postQuestions(token,groupId!!.toLong(),evaluationQuestions)

            emit(
                Resource.Success(
                    data = recordResponse,
                    code = recordResponse.code,
                    message = recordResponse.message
            )

            )
        }
        catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
