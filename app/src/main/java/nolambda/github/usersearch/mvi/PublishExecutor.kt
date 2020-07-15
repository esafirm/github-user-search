package nolambda.github.usersearch.mvi

interface PublishExecutor {
    fun execute(block: () -> Unit)
}