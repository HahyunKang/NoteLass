package com.app.note_lass.ui.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.module.group.data.applicationList.ApplicationStudent
import com.app.note_lass.module.group.ui.viewModel.GroupForTeacherViewModel
import com.app.note_lass.module.group.ui.viewModel.GroupViewModel
import com.app.note_lass.module.student.data.Evaluation.EvaluationAnswer
import com.app.note_lass.module.student.data.Evaluation.EvaluationForSubmit
import com.app.note_lass.module.student.data.Evaluation.EvaluationQuestion
import com.app.note_lass.module.student.data.Evaluation.Evaluations
import com.app.note_lass.module.student.ui.viewmodel.SelfEvaluationViewModel
import com.app.note_lass.module.student.data.handbook.HandBookRequest
import com.app.note_lass.module.student.ui.viewmodel.StudentMemoViewModel
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray


@Composable
fun DialogSignUp(
    setShowDialog : (Boolean)-> Unit,
    content : String,
    onDecline : () -> Unit,
    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Box(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
        ){
            Icon(
                painter= painterResource(id = R.drawable.group_filedelete_small),
                tint =Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp, bottom = 40.dp, start = 16.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text= content, style = NoteLassTheme.Typography.twenty_700_pretendard,color = PrimaryBlack)

                Spacer(modifier = Modifier.height(64.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Box(modifier = Modifier.weight(1f)){
                        RectangleUnableButton(
                            text ="아니오"
                        ) {
                         onDecline()   
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier.weight(1f)){
                        RectangleEnabledButton(
                            text ="네"
                        ) {
                            onAccept()
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CreateGroup(
    setShowDialog : (Boolean)-> Unit,
    getInfo : (String,String,String) -> Unit,
    onClickNext : () -> Unit
) {
    val gradeList = listOf("1","2","3")
    val classList = listOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15")
    val subjectList = listOf("국어","수학","영어","과학","사회","음악","체육","미술")

    val gradeInfo = remember {
        mutableStateOf("1")
    }
    val classInfo = remember {
        mutableStateOf("1")
    }
    val subjectInfo = remember {
        mutableStateOf("국어")
    }

    val gradeFilled = remember {
        mutableStateOf(false)
    }
    val classFilled = remember {
        mutableStateOf(false)
    }
    val subjectFilled = remember {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .size(width = 480.dp, height = 544.dp)
                .padding(horizontal = 40.dp, vertical = 25.dp)
        ){
            Icon(
                painter= painterResource(id = R.drawable.group_filedelete_small),
                tint =Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            Text(
                text = "대상 학년 선택",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))

            DropDownMenu(
                menuList = gradeList ,
                iconDown = R.drawable.arrow_down,
                iconUp = R.drawable.arrow_down,
                placeHolder = "대상 학년을 입력해주세요",
                isSelected =  {
                    gradeFilled.value = true
                },
                onGetInfo = {
                    gradeInfo.value = it
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "대상 반 선택",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))

            DropDownMenu(
                menuList = classList ,
                iconDown = R.drawable.arrow_down,
                iconUp = R.drawable.arrow_down,
                placeHolder = "대상 반을 입력해주세요",
                isSelected =  {
                    classFilled.value = true
                },
                onGetInfo = {
                    classInfo.value = it
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "대상 과목 선택",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))

            DropDownMenu(
                menuList = subjectList ,
                iconDown = R.drawable.arrow_down,
                iconUp = R.drawable.arrow_down,
                placeHolder = "대상 과목을 입력해주세요",
                isSelected =  {
                    subjectFilled.value = true
                },
                onGetInfo = {
                    subjectInfo.value = it
                }
            )
            Spacer(modifier = Modifier.height(56.dp))

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(400.dp)
            ) {
                RectangleButtonWithStatus(
                    text = "다음",
                    onClick = {
                              getInfo(gradeInfo.value,classInfo.value,subjectInfo.value)
                              onClickNext()
                    },
                    isEnabled = subjectFilled.value && gradeFilled.value && classFilled.value
                )

            }
        }
    }
}

@Composable
fun DialogGroupCode(
    setShowDialog : (Boolean)-> Unit,
    code : Int,
    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 480.dp, height = 544.dp)
                .padding(horizontal = 40.dp, vertical = 25.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(15.dp)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                    text = "그룹 입장 코드",
                    style = NoteLassTheme.Typography.twenty_700_pretendard,
                    color = PrimaryBlack,
                )
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = code.toString(),
                    style = NoteLassTheme.Typography.fourtyeight_700_pretendard,
                    color = PrimarayBlue,
                )

            Spacer(modifier = Modifier.height(130.dp))

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(350.dp)
            ) {
                RectangleEnabledButton(
                    text = "완료",
                    onClick = {
                        onAccept()
                    },
                )

            }
            }
        }
    }
@Composable
fun DialogEnterGroup(
    setShowDialog : (Boolean)-> Unit,
    getCode : (String) -> Unit,
    isCodeWrong : Boolean,
    onAccept : () -> Unit
) {
    val code = remember{
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 40.dp, vertical = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .weight(1f)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            Text(
                text = "입장 코드 입력",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(2f)
            )
            Spacer(modifier = Modifier.height(30.dp))

           OutlinedTextField(
               value = code.value.toString() ,
               onValueChange = {
                   code.value = it
               },
               enabled= true ,
               modifier = Modifier
                   .width(400.dp)
                   .weight(2.8f)
                   .padding(horizontal = 20.dp),
               textStyle = NoteLassTheme.Typography.sixteem_600_pretendard,
               placeholder = {
                   Text(
                       text = "입장 코드를 입력해주세요",
                       style = NoteLassTheme.Typography.sixteem_600_pretendard,
                       color= Color.LightGray
                       )
               },
               shape = RoundedCornerShape(8.dp)

           )
            Spacer(modifier = Modifier.height(8.dp))

            if(isCodeWrong){
                Text(
                    text = "존재하지 않는 코드입니다.",
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = Color(0xFFFF7788),
                    modifier = Modifier.weight(0.8f)
                )
            }else{
                Spacer(modifier = Modifier.weight(0.8f))

            }
            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .weight(2.5f)
                    .width(350.dp)
                    .padding(horizontal = 20.dp)
            ) {
                if (code.value.isNotEmpty()) {
                    RectangleEnabledButton(
                        text = "다음",
                        onClick = {
                            getCode(code.value)
                            onAccept()
                        },
                    )
                }
            else{
                    RectangleUnableButton(
                        text = "다음",
                        onClick = {}
                    )

                }

            }
        }
    }
}

@Composable
fun DialogEnterGroupAccept(
    setShowDialog : (Boolean)-> Unit,
    groupInfo : String,
    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 40.dp, vertical = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                buildAnnotatedString {
                      withStyle(style = SpanStyle(color = PrimarayBlue)) {
                            append(groupInfo)
                        }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                            append("에\n입장하시겠습니까?")
                        }
                },
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(350.dp)
                    .padding(horizontal = 20.dp)
            ) {
                RectangleEnabledButton(
                    text = "입장",
                    onClick = {
                        onAccept()
                    },
                )

            }
        }
    }
}

@Composable
fun DialogInRecord(
    setShowDialog : (Boolean)-> Unit,
    content : String,
    buttonText : String,
    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 40.dp, vertical = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            Text(
                text = content,
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(350.dp)
                    .padding(horizontal = 20.dp)
            ) {
                RectangleEnabledButton(
                    text = buttonText,
                    onClick = {
                        onAccept()
                    },
                )

            }
        }
    }
}


@Composable
fun DialogGroupTeacherAccept(
    setShowDialog : (Boolean)-> Unit,
    list : List<ApplicationStudent>,
    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 40.dp, vertical = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
            )
//            list.forEachIndexed { index, applicationStudent ->
//                ApplicationStudent(
//                    memberId = ,
//                    id = index+1 ,
//                    name = ,
//                    onClickAccept = ,
//                    onClickDecline =
//                )
//            }


        }
    }
}

@Composable
fun DialogStudentMemo(
    setShowDialog : (Boolean)-> Unit ={},
    getGroupInfo : (String,Int) -> Unit = {string,int->},
    getStudentInfo : (String,Int) -> Unit ={string,int->},
    studentMemoViewModel: StudentMemoViewModel = hiltViewModel()

) {
    val isGroupSelected = remember {
        mutableStateOf(false)
    }
    val isStudentSelected = remember {
        mutableStateOf(false)
    }
    val memo = remember {
        mutableStateOf("")
    }
    val groupId = remember {
        mutableStateOf(0)
    }
    val studentId = remember {
        mutableStateOf(0)
    }
    val attitudeScore = remember {
        mutableStateOf(0)
    }
    val presentationScore = remember {
        mutableStateOf(0)
    }
    val groupListState = studentMemoViewModel.groupHashState

    val studentListState = studentMemoViewModel.studentHashState

    val submitState = studentMemoViewModel.handBookSubmitState


    var handBookRequest = HandBookRequest(content = null, attitudeScore = 0, presentationNum = 0)


    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .width(720.dp)
                .height(580.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 40.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        setShowDialog(false)
                    }
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    DropDownMenuForMemo(
                        menuList = groupListState.value.groupHash,
                        iconDown = R.drawable.arrow_down,
                        iconUp = R.drawable.arrow_down,
                        placeHolder = "그룹 선택",
                        isSelected = {
                            isGroupSelected.value = it
                        },
                        onGetInfo = { name, id ->
                            getGroupInfo(name, id)
                            groupId.value = id
                            studentMemoViewModel.getStudentList(groupId.value)
                        }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    DropDownMenuForMemo(
                        menuList = studentListState.value.studentHash,
                        iconDown = R.drawable.arrow_down,
                        iconUp = R.drawable.arrow_down,
                        placeHolder = "학생 선택",
                        isSelected = {
                            isStudentSelected.value = it
                        },
                        onGetInfo = { name, id ->
                            getStudentInfo(name, id)
                            studentId.value = id
                        },
                        isGroupSelected = studentListState.value.isSuccess
                    )
                }

            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = memo.value,
                onValueChange = {
                    memo.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                placeholder = {
                    Text(
                        text = "메모를 입력해주세요.",
                        style = NoteLassTheme.Typography.sixteem_600_pretendard,
                        color = Color.LightGray
                    )
                },
                textStyle = NoteLassTheme.Typography.sixteem_600_pretendard,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = PrimaryGray,
                    focusedIndicatorColor = PrimaryGray,
                    cursorColor = Color.Black,
                    backgroundColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))


            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "발표 횟수",
                    style = NoteLassTheme.Typography.fourteen_600_pretendard,
                    color = PrimaryBlack,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(32.dp)
                ) {
                    NumUpAndDown(isNumChange = {
                        presentationScore.value = it
                    })
                }
                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = "태도 점수",
                    style = NoteLassTheme.Typography.fourteen_600_pretendard,
                    color = PrimaryBlack,
                    textAlign = TextAlign.Center

                )
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(32.dp)
                ) {
                    NumUpAndDown(isNumChange = {
                        attitudeScore.value = it
                    })
                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            Row(modifier = Modifier.align(Alignment.End)) {

                Box(modifier = Modifier.size(width = 49.dp, height = 40.dp)) {
                    RectangleUnableButton(text = "취소") {
                        setShowDialog(false)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(modifier = Modifier.size(width = 73.dp, height = 40.dp)) {
                    RectangleEnabledButton(text = "저장하기") {
                        handBookRequest = HandBookRequest(
                            memo.value,
                            attitudeScore.value,
                            presentationScore.value
                        )
                        studentMemoViewModel.postHandBook(
                            groupId.value,
                            studentId.value,
                            handBookRequest,
                            onSuccess = {
                                setShowDialog(false)
                            })
                    }
                }

            }


        }
    }
}
@Composable
fun DialogGroupInfo(
    setShowDialog : (Boolean)-> Unit,
    onClickDelete : () -> Unit,
    onClickModify : () -> Unit,
    groupViewModel: GroupViewModel = hiltViewModel()
) {

//    if(showDeleteDialog.value) {
//        Log.e("그룹 삭제","그룹 삭제 Dialog")
//        DialogDeleteGroup(
//            setShowDialog = {
//                showDeleteDialog.value = it
//            }
//        )
//    }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Box(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
        ){
            Icon(
                painter= painterResource(id = R.drawable.group_filedelete_small),
                tint =Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp, bottom = 40.dp, start = 16.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text=
                    "수정 또는 삭제하실 수 있습니다.",
                    style = NoteLassTheme.Typography.twenty_700_pretendard,color = PrimaryBlack)

                Spacer(modifier = Modifier.height(64.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Box(modifier = Modifier.weight(1f)){
                        RectangleEnabledButton(
                            text ="그룹 삭제"
                        ) {
                            onClickDelete()
                            setShowDialog(false)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier.weight(1f)){
                        RectangleEnabledButton(
                            text ="명단 수정"
                        ) {
                            onClickModify()
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun DialogDeleteGroup(
    setShowDialog : (Boolean)-> Unit,
    goBackToGroup : () -> Unit ,
    groupViewModel : GroupForTeacherViewModel = hiltViewModel()
) {
    val text  = remember{
        mutableStateOf("")
    }
    val isTextWrong = remember{
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val deleteState = groupViewModel.deleteGroupState
    if(deleteState.value.isSuccess)setShowDialog(false)
    if(deleteState.value.isError) Toast.makeText(context,"오류가 발생했습니다.",Toast.LENGTH_SHORT).show()

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 540.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(vertical = 25.dp)
//                .padding(horizontal = 40.dp, vertical = 25.dp),
                ,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .weight(1.5f),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Text(
                    text = "그룹 삭제시 그룹 내 모든 데이터가 삭제됩니다.",
                    color = PrimarayBlue,
                    style = NoteLassTheme.Typography.twenty_700_pretendard
                )

                Icon(
                    painter = painterResource(id = R.drawable.group_filedelete_small),
                    tint = Color(0xFF26282B),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            setShowDialog(false)
                        }
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "그룹을 삭제합니다",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1.5f)
                    .padding(start = 80.dp)
                ,
                )
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = text.value ,
                onValueChange = {
                    text.value = it
                },
                enabled= true ,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3.2f)
                    .padding(horizontal = 80.dp),
                textStyle = NoteLassTheme.Typography.sixteem_600_pretendard,
                placeholder = {
                    Text(
                        text = "위 문장을 적어주세요.",
                        style = NoteLassTheme.Typography.sixteem_600_pretendard,
                        color= Color.LightGray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor =  Color.White,
                    unfocusedIndicatorColor = PrimaryGray,
                    focusedIndicatorColor = PrimarayBlue,
                    cursorColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            if(isTextWrong.value){
                Text(
                    text = "문장을 제대로 적어주세요.",
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = Color(0xFFFF7788),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 80.dp),
                    )
            }else{
                Spacer(modifier = Modifier.weight(0.8f))

            }
            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .weight(2.5f)
                    .width(350.dp)
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                if (text.value == "그룹을 삭제합니다") {
                    RectangleEnabledButton(
                        text = "삭제하기",
                        onClick = {
                            groupViewModel.deleteGroup()
                            goBackToGroup()
                        },
                    )
                }
                else{
                    RectangleUnableButton(
                        text = "삭제하기",
                        onClick = {
                            isTextWrong.value = true
                        }
                    )

                }

            }
        }
    }
}
@Composable
fun DialogDeleteStudent(
    groupInfo: String,
    setShowDialog : (Boolean)-> Unit,
    onClickDelete : (Int)-> Unit,
    groupViewModel : GroupForTeacherViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val studentsState  = groupViewModel.studentListState

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 580.dp, height = 658.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(vertical = 22.dp)
//                .padding(horizontal = 40.dp, vertical = 25.dp),
            ,
        ) {

                Text(
                    text = groupInfo,
                    color = PrimaryBlack,
                    modifier = Modifier.padding(start= 22.dp ),
                    style = NoteLassTheme.Typography.twenty_700_pretendard
                )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 110.dp, end = 131.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "번호",
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = PrimaryGray
                )

                Text(
                    text = "이름",
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = PrimaryGray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            androidx.compose.material3.Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                thickness = 1.dp,
                color = PrimaryGray
            )

            Spacer(modifier = Modifier.height(14.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ){
               itemsIndexed(studentsState.value.studentList) {
                   index, student ->
                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 108.dp, end = 77.dp),
                       horizontalArrangement = Arrangement.SpaceBetween,
                   ) {
                       Text(text = (index+1).toString(),color = PrimarayBlue,style = NoteLassTheme.Typography.twelve_600_pretendard)

                       Row(modifier = Modifier.wrapContentWidth()){
                           Text(
                               text= student.name,color = PrimaryBlack, style = NoteLassTheme.Typography.twelve_600_pretendard
                           )
                           Spacer(modifier = Modifier.width(41.dp))
                           Text(text = "삭제",
                               color = PrimaryGray,
                               style = NoteLassTheme.Typography.twelve_600_pretendard,
                               textDecoration = TextDecoration.Underline,
                               modifier = Modifier.clickable {
                                   onClickDelete(student.id)
                                   setShowDialog(false)
                               }
                           )
                       }
                   }
               }

            }
        }
    }
}

@Composable
fun DialogDeleteConfirm(
    setShowDialog : (Boolean)-> Unit,
    studentId : Int,
    groupViewModel: GroupForTeacherViewModel = hiltViewModel()
) {

//    if(showDeleteDialog.value) {
//        Log.e("그룹 삭제","그룹 삭제 Dialog")
//        DialogDeleteGroup(
//            setShowDialog = {
//                showDeleteDialog.value = it
//            }
//        )
//    }
    val context = LocalContext.current
    if(groupViewModel.deleteStudentState.value.isSuccess){
        Toast.makeText(context,"삭제되었습니다.",Toast.LENGTH_SHORT).show()
        setShowDialog(false)
    }
    if(groupViewModel.deleteStudentState.value.isError){
        Toast.makeText(context,"오류가 발생했습니다.",Toast.LENGTH_SHORT).show()

    }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Box(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
        ){
            Icon(
                painter= painterResource(id = R.drawable.group_filedelete_small),
                tint =Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp, bottom = 40.dp, start = 16.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text= "삭제하시면 해당 학생 데이터가 사라집니다.\n정말 삭제하시겠습니까?",
                    style = NoteLassTheme.Typography.twenty_700_pretendard,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = PrimaryBlack
                )

                Spacer(modifier = Modifier.height(64.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Box(modifier = Modifier.weight(1f)){
                        RectangleUnableButton(
                            text ="계속 이용하기"
                        ) {
                            setShowDialog(false)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier.weight(1f)){
                        RectangleEnabledButton(
                            text ="삭제하기"
                        ) {
                            groupViewModel.deleteStudent(studentId.toLong())
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun DialogModifyMemo(
    setShowDialog : (Boolean)-> Unit,
    memoId : Int,
    memoContent : String,
    onClickModify : (String) -> Unit,
    groupViewModel: GroupViewModel = hiltViewModel()
) {
    val content = remember{
        mutableStateOf(memoContent)
    }

//    if(showDeleteDialog.value) {
//        Log.e("그룹 삭제","그룹 삭제 Dialog")
//        DialogDeleteGroup(
//            setShowDialog = {
//                showDeleteDialog.value = it
//            }
//        )
//    }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 600.dp, height = 388.dp)
                .padding(horizontal = 20.dp, vertical = 25.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
        ){

            OutlinedTextField(
                value = content.value,
                onValueChange = {
                    content.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 25.dp)
                    .weight(4.5f),
                shape= RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PrimaryGray,
                    unfocusedBorderColor = PrimaryGray,
                    backgroundColor = Color.White,
                    cursorColor = Color.Black
                )
            )
                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(bottom = 15.dp, end = 15.dp)
                        .align(Alignment.End)
                        .weight(1f),
                ){
                    Box(modifier = Modifier.width(80.dp)){
                        RectangleUnableButton(
                            text ="취소"
                        ) {
                            setShowDialog(false)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.width(100.dp)){
                        RectangleEnabledButton(
                            text ="저장하기"
                        ) {
                            onClickModify(content.value)
                        }
                    }
                }
            }

        }
    }


@Composable
fun DialogCreateSelfEvaluation(
    setShowDialog : (Boolean)-> Unit,
    selfEvaluationViewModel: SelfEvaluationViewModel = hiltViewModel()
) {
    val questions = remember {
        mutableStateListOf<String>()
    }

    val getQuestionState = selfEvaluationViewModel.getQuestionState
    val allQuestionFilled = remember {
        mutableStateOf(false)
    }
    val opened = remember{
        mutableStateOf(true)
    }
    allQuestionFilled.value = questions.all { it.isNotEmpty() }
    LaunchedEffect(Unit){
        if(opened.value){
            selfEvaluationViewModel.getQuestions()
        }
        opened.value= false
    }
    LaunchedEffect(key1 = getQuestionState.value){
        if(getQuestionState.value.isSuccess){
            val getQuestions = getQuestionState.value.result
            if(getQuestions?.isNotEmpty() == true){
                getQuestions.forEach {
                    Log.e("question",it.question)
                    questions.add(it.question)
                }
            }else{
                questions.add("")
            }
        }
    }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 720.dp, height = 480.dp)
                .padding(horizontal = 20.dp, vertical = 25.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
        ) {
            Icon(
                painter= painterResource(id = R.drawable.group_filedelete_small),
                tint =Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(24.dp)
                    .weight(1f)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .weight(4f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                itemsIndexed(questions){
                    index, question ->
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "질문",style= NoteLassTheme.Typography.sixteen_700_pretendard,color = Color.Black)
                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(
                            value =question  ,
                            onValueChange = {
                                questions[index] = it
                            },
                            modifier = Modifier
                                .height(53.dp)
                                .fillMaxWidth(),
                            placeholder = {
                              Text(text = "질문을 입력하세요",color = PrimaryGray, style = NoteLassTheme.Typography.sixteem_600_pretendard)
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                focusedIndicatorColor =  PrimaryGray,
                                unfocusedIndicatorColor = PrimaryGray,
                                cursorColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                    }

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 24.dp, end = 24.dp)
            ){
                Box(modifier = Modifier.align(Alignment.Center)){
                    Box(modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)) {
                        RectangleEnabledButton(text = "문항 추가") {
                            questions.add("")
                        }
                    }
                }
                Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                    Box(modifier = Modifier
                        .width(49.dp)
                        .height(40.dp)) {

                        RectangleUnableButton(text = "취소") {
                            setShowDialog(false)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier
                        .width(73.dp)
                        .height(40.dp)
                    ) {

                        if(!allQuestionFilled.value)RectangleUnableButton (text = "저장하기") {

                    }else{
                        RectangleEnabledButton(text = "저장하기") {
                            if(getQuestionState.value.result?.isNotEmpty()==true){
                                val modifyQuestions = getQuestionState.value.result?.mapIndexed {
                                        index, evaluationQuestion ->
                                    EvaluationQuestion(evaluationQuestion.id, questions[index])
                                }
                                selfEvaluationViewModel.modifyStudentRecord(modifyQuestions!!)
                                selfEvaluationViewModel.postQuestions(
                                    questions.subList(
                                    getQuestionState.value.result?.size!!,
                                    questions.size
                                )
                                )
                            }else{
                                selfEvaluationViewModel.postQuestions(questions)
                            }
                            setShowDialog(false)
                            opened.value = true
                        }

                        }
                }

                }


            }



        }
    }
}

@Composable
fun DialogSelfEvaluationForStudent(
    setShowDialog : (Boolean)-> Unit,
    selfEvaluationViewModel: SelfEvaluationViewModel = hiltViewModel()
) {
    val evaluations  = remember {
        mutableStateOf(mutableListOf<EvaluationForSubmit>())
    }
    val opened = remember{
        mutableStateOf(true)
    }
    LaunchedEffect(Unit){
        if(opened.value){
            selfEvaluationViewModel.getAnswers()
        }
        opened.value= false
    }

    val getQuestionState = selfEvaluationViewModel.getQuestionState
    val getAnswerState = selfEvaluationViewModel.getAnswersState

    LaunchedEffect(key1 = getAnswerState.value,key2 = getQuestionState.value){
        if(getAnswerState.value.isSuccess && getQuestionState.value.isSuccess){
            val getAnswers = getAnswerState.value.result
            val getQuestions = getQuestionState.value.result

                getQuestions?.forEachIndexed  {
                    index, questions ->
                    getAnswers?.forEach {
                        if(questions.id == it.questionId){
                            if(it.answer==null)evaluations.value = (evaluations.value + EvaluationForSubmit(it.questionId,it.question,it.answerId,mutableStateOf( ""),true)).toMutableList()
                            else evaluations.value = (evaluations.value + EvaluationForSubmit(it.questionId,it.question,it.answerId,mutableStateOf(it.answer ?: ""),false)).toMutableList()
                        }

                    }

                }

        }
    }


    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 720.dp, height = 480.dp)
                .padding(horizontal = 20.dp, vertical = 25.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
        ) {
          Text(
              text = "자기 평가서",
              style = NoteLassTheme.Typography.twenty_700_pretendard,
              color = PrimaryBlack,
              modifier = Modifier.padding(32.dp)
          )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .weight(3.5f),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                itemsIndexed(evaluations.value){
                        index, evaluation ->
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = (index+1).toString() +"."+ evaluation.question,style= NoteLassTheme.Typography.sixteen_700_pretendard,color = Color.Black)
                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(
                            value = evaluation.answer.value ?: "" ,
                            onValueChange = {
                                evaluation.answer.value = it
                            },
                            modifier = Modifier
                                .height(117.dp)
                                .fillMaxWidth(),
                            placeholder = {
                                Text(text = "음슴체로 작성해주세요",color = PrimaryGray, style = NoteLassTheme.Typography.sixteem_600_pretendard)
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                focusedIndicatorColor =  PrimaryGray,
                                unfocusedIndicatorColor = PrimaryGray,
                                cursorColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                    }

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 24.dp, end = 24.dp, top = 16.dp)
            ){

                Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                    Box(modifier = Modifier
                        .width(49.dp)
                        .height(40.dp)) {

                        RectangleUnableButton(text = "취소") {
                            setShowDialog(false)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier
                        .width(73.dp)
                        .height(40.dp)
                    ) {

                            RectangleEnabledButton(text = "저장하기") {
                                val modifyAnswers = emptyList<EvaluationAnswer>().toMutableList()
                                val postAnswers = emptyList<EvaluationAnswer>().toMutableList()
                                evaluations.value.forEach {
                                    if(it.first){
                                        postAnswers.add(
                                            EvaluationAnswer(it.questionId,
                                               it.answer.value.toString()
                                            )
                                        )
                                    }else{
                                        modifyAnswers.add(EvaluationAnswer(it.answerId!!, it.answer.value.toString()))
                                    }
                                }
                                if(postAnswers.isNotEmpty())selfEvaluationViewModel.postAnswers(postAnswers)
                                if(modifyAnswers.isNotEmpty())selfEvaluationViewModel.modifyAnswers(modifyAnswers)
                                opened.value= true
                                setShowDialog(false)


                        }
                    }

                }


            }



        }
    }
}

@Composable
fun DialogEvaluationForTeacher(
    setShowDialog : (Boolean)-> Unit ={},
    evaluations : List<Evaluations>,
    studentMemoViewModel: StudentMemoViewModel = hiltViewModel()

) {


    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .width(720.dp)
                .height(580.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 40.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "자기 평가서",
                    style = NoteLassTheme.Typography.twenty_700_pretendard,
                    color = PrimaryBlack
                )
                Icon(
                    painter = painterResource(id = R.drawable.group_filedelete_small),
                    tint = Color(0xFF26282B),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            setShowDialog(false)
                        }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                itemsIndexed(evaluations) { index, item ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = (index+1).toString() + "." + item.question)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(117.dp)
                                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                                .wrapContentHeight()
                        ) {
                            Text(
                                text = item.answer ?: "",
                                style = NoteLassTheme.Typography.fourteen_600_pretendard,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(vertical = 15.dp, horizontal = 10.dp)
                            )
                        }
                    }
                }

            }


        }
    }
}

@Composable
fun DialogResetPassword(
    setShowDialog : (Boolean)-> Unit,
    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .padding(horizontal = 40.dp, vertical = 25.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(15.dp)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "비밀번호가 재설정되었습니다.",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
            )
            Spacer(modifier = Modifier.height(88.dp))

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(200.dp)
            ) {
                RectangleEnabledButton(
                    text = "확인",
                    onClick = {
                        onAccept()
                    },
                )

            }
        }
    }
}
@Preview
@Composable
fun DialogPreview(){
    val showDialog = remember {
        mutableStateOf(false)
    }


    Column {

//
//        DialogSignUp(
//            setShowDialog = {
//                showDialog.value = it
//            },
//            content = "노트고등학교 3학년 1반 1번\n   김OO님이 맞습니까?",
//            onDecline = { /*TODO*/ }
//        ) {
//            /*TODO*/
//        }
//
//        CreateGroup(
//            setShowDialog = {
//                            showDialog.value = it
//            },
//        getInfo ={
//            gradeInfo, classInfo , subjctInfo ->
//
//        }
//
//        ) {
//
//        }
//        DialogGroupCode(
//            setShowDialog = {
//                            showDialog.value = it
//            }, code = 123456) {
//
//        }

//        DialogEnterGroup(setShowDialog = {
//                                         showDialog.value = it
//        }, getCode = {}) {
//
//        }

   //     DialogStudentMemo(groupList = hashMapOf(), studentList = hashMapOf() )

    }
}

