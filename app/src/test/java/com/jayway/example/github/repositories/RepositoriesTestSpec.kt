package com.jayway.example.github.repositories

import com.jayway.android.test.*
import com.jayway.example.github.data.dto.GithubRepository
import com.jayway.example.github.data.service.GithubRepositories
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import org.mockito.Mockito


object RepositoriesTestSpec : Spek({
                                       testRules(
                                           TimberRule("RepositoriesTestSpec"),
                                           RxImmediateSchedulerRule
                                       )

                                       describe("User navigates to Repositories screen") {

                                           val testGithubRepositories = Mockito.mock(
                                               GithubRepositories::class.java
                                           )
                                           //                                               val viewModel: RepositoriesViewModel by memoized(mode = CachingMode.SCOPE) {
                                           //                                                   RepositoriesViewModel(testGithubRepositories)
                                           //                                               }

                                           //                                               val testScreen by memoized(mode = CachingMode.SCOPE) {
                                           //                                                   RepositoriesTestScreen()
                                           //                                               }

                                           val viewModel =
                                               RepositoriesViewModel(testGithubRepositories)


                                           val testScreen = RepositoriesTestScreen()

                                           given("Screen is bound to ViewModel") {
                                               viewModel.bind(testScreen)
                                               it("has state INITIAL") {
                                                   Assertions.assertThat(testScreen.recordedStates.first())
                                                       .isEqualTo(RepositoriesViewModel.State.NoData.Initial)
                                               }

                                               given("no network connection") {
                                                   beforeGroup {
                                                       whenever(
                                                           testGithubRepositories.getAllRepos(
                                                               Mockito.anyInt()
                                                           )
                                                       ).thenReturn(
                                                           Single.error(
                                                               RuntimeException("No Network")
                                                           )
                                                       )
                                                   }

                                                   on("loading initial page") {
                                                       testScreen.loadNextPage()

                                                       it("enters state ShowLoading") {
                                                           Assertions.assertThat(testScreen.recordedStates[1])
                                                               .isEqualTo(RepositoriesViewModel.State.NoData.ShowLoading)
                                                       }

                                                       it("ends in state NoNetwork") {
                                                           Assertions.assertThat(testScreen.latestState)
                                                               .isInstanceOf(
                                                                   RepositoriesViewModel.State.NoData.ErrorLoadingPage::class.java
                                                               )
                                                       }
                                                   }
                                               }

                                               given("network connection") {
                                                   val testData = testRepositories()
                                                   beforeGroup {
                                                       whenever(
                                                           testGithubRepositories.getAllRepos(
                                                               Mockito.anyInt()
                                                           )
                                                       ).thenReturn(
                                                           Single.just(testData)
                                                       )
                                                   }
                                                   on("user retries loading data") {
                                                       testScreen.loadNextPage()

                                                       it("enters state ShowLoading") {
                                                           Assertions.assertThat(
                                                               testScreen.recordedStates[testScreen.recordedStates.size - 2]
                                                           )
                                                               .isEqualTo(RepositoriesViewModel.State.NoData.ShowLoading)
                                                       }

                                                       it("ends up showing content from page 1") {
                                                           Assertions.assertThat(testScreen.latestState)
                                                               .isEqualTo(
                                                                   RepositoriesViewModel.State.WithData.ShowContentState(
                                                                       testData, 1
                                                                   )
                                                               )
                                                       }
                                                   }

                                                   on("user loads next page") {
                                                       testScreen.loadNextPage()

                                                       it("ends up showing content from page 2") {
                                                           Assertions.assertThat(testScreen.latestState)
                                                               .isInstanceOf(
                                                                   RepositoriesViewModel.State.WithData.ShowContentState::class.java
                                                               )
                                                           val actualPage =
                                                               (testScreen.latestState as RepositoriesViewModel.State.WithData).page
                                                           Assertions.assertThat(actualPage)
                                                               .isEqualTo(2)
                                                       }

                                                       it("screen shows both page 1 and page 2") {
                                                           val actualRepos =
                                                               (testScreen.latestState as RepositoriesViewModel.State.WithData).repositories
                                                           val expectedRepos = testData + testData

                                                           Assertions.assertThat(actualRepos)
                                                               .containsExactlyElementsOf(
                                                                   expectedRepos
                                                               )
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                   })


fun testRepositories(): List<GithubRepository> = mutableListOf<GithubRepository>().apply {
    this.addAll((0..5L).map {
        GithubRepository(
            id = it, name = "repo $it", fullName = "/path/to/repo/full_name_$it", stars = it * 10
        )
    })
}

