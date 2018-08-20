package com.jayway.example.github.repositories

import com.jayway.example.github.TestScreen

class RepositoriesTestScreen :
    TestScreen<RepositoriesViewModel.Action, RepositoriesViewModel.State>() {

    fun loadNextPage() {
        injectAction(RepositoriesViewModel.Action.LoadNextPageAction)
    }
}