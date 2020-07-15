package nolambda.github.usersearch.mvi

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.Executors

abstract class StatePresenter<STATE> {
    private val stateLiveData: MutableLiveData<STATE> = MutableLiveData()
    private val executor = Executors.newSingleThreadExecutor()

    val currentState get() = stateHolder ?: initialState()

    private var stateHolder: STATE? = null

    abstract fun initialState(): STATE

    internal open fun onAttach() {
        // no-op
    }

    fun attach(lifecycleOwner: LifecycleOwner, onState: (STATE) -> Unit) {
        stateLiveData.observe(lifecycleOwner, Observer {
            onState.invoke(it)
        })

        onAttach()
    }

    private fun createNewState(stateModifier: (STATE) -> STATE): STATE {
        val newState = stateModifier.invoke(currentState)
        stateHolder = newState
        return newState
    }

    fun publish(stateModifier: (STATE) -> STATE) {
        if (PresenterConfig.isTestMode) {
            createNewState(stateModifier)
        } else {
            executor.execute {
                stateLiveData.postValue(createNewState(stateModifier))
            }
        }
    }
}