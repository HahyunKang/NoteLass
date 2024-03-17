package com.app.note_lass.module.record.data

data class RecordScore(
    val attitudeScore: Int,
    val attitudeScoreRank: Int,
    val highScoreAssignmentList: List<Any>,
    val presentationNum: Int,
    val presentationRank: Int
)