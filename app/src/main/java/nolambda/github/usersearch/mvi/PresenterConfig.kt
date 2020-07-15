package nolambda.github.usersearch.mvi

object PresenterConfig {
    var isTestMode: Boolean = false

    fun setTestMode() {
        this.isTestMode = true
    }
}