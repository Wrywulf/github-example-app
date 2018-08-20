package com.jayway.example.github.repositories

import com.jayway.android.test.TestSchedulersRule
import com.jayway.android.test.TimberRuleJUnit
import com.jayway.example.github.TestScreen
import com.jayway.example.github.data.dto.GithubRepository
import com.jayway.example.github.data.service.GithubRepositories
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class RepositoriesViewModelTest {

    // REMOVE COMMENT TO ENABLE LOGGING
    companion object {
        @JvmField
        @ClassRule
        val TIMBER_RULE = TimberRuleJUnit("RepositoriesViewModelTest")
    }

    @Rule
    @JvmField
    val rxRule = TestSchedulersRule()


    @Mock
    lateinit var testGithubRepositories: GithubRepositories

    private lateinit var viewModel: RepositoriesViewModel
    private lateinit var testScreen: TestScreen<RepositoriesViewModel.Action, RepositoriesViewModel.State>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = RepositoriesViewModel(testGithubRepositories)
        testScreen = TestScreen()
    }

    @Test
    fun `initial state is Initial`() {
        viewModel.bind(testScreen)

        Assertions.assertThat(testScreen.latestState)
            .isEqualTo(RepositoriesViewModel.State.NoData.Initial)
    }

    @Test
    fun `GIVEN Initial, WHEN LoadInitialPageAction, THEN ShowLoading`() {
        viewModel.bind(testScreen)
        testScreen.injectAction(RepositoriesViewModel.Action.LoadNextPageAction)

        Assertions.assertThat(testScreen.latestState)
            .isEqualTo(RepositoriesViewModel.State.NoData.ShowLoading)
    }


    @Test
    fun `GIVEN ShowLoading, WHEN PageLoadedAction, THEN ShowContent`() {

        val repositories = mutableListOf<GithubRepository>().apply {
            this.addAll((0..5L).map {
                GithubRepository(
                    id = it,
                    name = "repo $it",
                    fullName = "/path/to/repo/full_name_$it",
                    stars = it * 10
                )
            })
        }
        val page = 1
        val expectedState = RepositoriesViewModel.State.WithData.ShowContentState(
            repositories = repositories, page = page
        )
        whenever(testGithubRepositories.getAllRepos(Mockito.anyInt())).thenReturn(
            Single.just(
                repositories
            )
        )

        viewModel.bind(testScreen)
        testScreen.injectAction(RepositoriesViewModel.Action.LoadNextPageAction)

        rxRule.testScheduler.triggerActions()

        Assertions.assertThat(testScreen.latestState)
            .isEqualTo(expectedState)
    }
}

