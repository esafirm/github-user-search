package nolambda.github.usersearch

import nolambda.github.usersearch.data.Api
import nolambda.github.usersearch.data.ApiInterface
import nolambda.github.usersearch.data.User
import nolambda.github.usersearch.mvi.StatePresenter
import nolambda.github.usersearch.mvi.asSingleEvent
import nolambda.github.usersearch.utils.call

class UserSearchPresenter(
    private val api: ApiInterface = Api()
) : StatePresenter<UserSearchState>() {

    companion object {
        const val FIRST_PAGE = 1
    }

    override fun initialState(): UserSearchState = UserSearchState()

    private fun showNextPage(users: List<User>) {
        publish {
            val newList = (it.users + users).distinctBy { u -> u.login }
            it.copy(users = newList, currentPage = it.currentPage + 1, isLoading = false)
        }
    }

    private fun showInitialPage(users: List<User>) {
        publish {
            it.copy(users = users, currentPage = FIRST_PAGE, isLoading = false)
        }
    }

    private fun showError(err: Throwable) {
        publish {
            it.copy(err = err.asSingleEvent(), isLoading = false)
        }
    }

    private fun showEmptyPage() = publish { initialState() }

    fun loadNextPage() {
        val lastQuery = currentState.lastQuery
        val requestedPage = currentState.currentPage + 1
        search(lastQuery, requestedPage)
    }

    fun search(query: String, page: Int = FIRST_PAGE) {
        if (query.isBlank()) {
            showEmptyPage()
            return
        }

        val isLoadFirstPage = page == FIRST_PAGE
        publish { it.copy(isLoading = isLoadFirstPage, lastQuery = query) }

        api.search(query, page).call(
            onSuccess = {
                if (isLoadFirstPage) {
                    showInitialPage(it.items)
                } else {
                    showNextPage(it.items)
                }
            },
            onError = { showError(it) }
        )
    }
}