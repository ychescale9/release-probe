package reactivecircus.blueprint.interactor.rx2

import io.reactivex.Scheduler
import io.reactivex.Single
import reactivecircus.blueprint.interactor.InteractorParams

/**
 * Abstract class for a use case, representing an execution unit of asynchronous work.
 * This use case type uses [Single] as the return type.
 * Upon subscription a use case will execute its job in the thread specified by the [ioScheduler].
 * and will post the result to the thread specified by [uiScheduler].
 */
abstract class SingleInteractor<P : InteractorParams, T>(
    private val ioScheduler: Scheduler,
    private val uiScheduler: Scheduler
) {

    lateinit var params: P

    protected abstract fun createInteractor(): Single<T>

    /**
     * Build a use case single with the provided execution thread and post execution thread
     * @param params
     * @param blocking - when set to true the single will be subscribed and observed on the current thread.
     * @return
     */
    fun buildSingle(params: P, blocking: Boolean = false): Single<T> {
        // update params for the next execution
        this.params = params
        return if (blocking) {
            createInteractor()
        } else {
            createInteractor()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
        }
    }
}