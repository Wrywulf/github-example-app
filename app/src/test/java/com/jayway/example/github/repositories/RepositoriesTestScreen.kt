package com.jayway.example.github.repositories

import com.jayway.example.github.TestScreen

class RepositoriesTestScreen : TestScreen<RepositoriesViewModel.Action, RepositoriesViewModel.State>() {

    fun loadInitialPage() {
        injectAction(RepositoriesViewModel.Action.LoadInitialPageAction)
    }


    fun scrollToBottomOfPage() {
        injectAction(RepositoriesViewModel.Action.LoadNextPageUserAction)
    }
}