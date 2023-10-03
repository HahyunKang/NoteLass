package com.app.note_lass.module.signup.data

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