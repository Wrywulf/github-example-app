package com.jayway.example.github.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubRepository(

    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String,

    @Json(name = "full_name")
    val fullName: String,

    @Json(name="stargazers_count") val stars : Long
)
