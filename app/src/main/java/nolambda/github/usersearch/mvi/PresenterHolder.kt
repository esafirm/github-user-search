package nolambda.github.usersearch.mvi

import androidx.lifecycle.ViewModel

internal class PresenterHolder : ViewModel() {
    var presenter: StatePresenter<*>? = null
}