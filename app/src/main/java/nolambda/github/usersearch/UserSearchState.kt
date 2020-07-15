package nolambda.github.usersearch

import nolambda.github.usersearch.data.User
import nolambda.github.usersearch.mvi.SingleEvent

data class UserSearchState(
    val lastQuery: String = "",
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val err: SingleEvent<Throwable>? = null,
    val currentPage: Int = 1
)