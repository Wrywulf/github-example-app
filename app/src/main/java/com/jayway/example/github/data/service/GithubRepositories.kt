package com.jayway.example.github.data.service

import com.jayway.example.github.data.dto.GithubRepository
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepositories {

    @GET("orgs/jayway/repos")
    fun getJaywayRepos()

    @GET("repositories")
    fun getAllRepos(@Query("since") page: Int) : Single<List<GithubRepository>>

    @GET("search/repositories")
    fun search(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Single<List<GithubRepository>>
}