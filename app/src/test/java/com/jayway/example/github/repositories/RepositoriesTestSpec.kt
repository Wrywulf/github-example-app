package com.jayway.example.github.repositories

import com.jayway.example.github.data.service.GithubRepositories
import org.assertj.core.api.Assertions
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.mockito.Mockito


object RepositoriesTestSpec : Spek({
                                       given("Repositories screen is created from scratch") {
                                           val viewModel: RepositoriesViewModel by memoized {
                                               val testGithubRepositories = Mockito.mock(
                                                   GithubRepositories::class.java)
                                               RepositoriesViewModel(testGithubRepositories)
                                           }

                                           val testScreen by memoized {
                                               RepositoriesTestScreen()
                                           }

                                           on("Screen is bound to ViewModel") {
                                               viewModel.bind(testScreen)
                                               it("has state INITIAL") {
                                                   Assertions.assertThat(testScreen.latestState).isEqualTo(RepositoriesViewModel.State.Initial)
                                               }
                                           }
                                       }
                                   })