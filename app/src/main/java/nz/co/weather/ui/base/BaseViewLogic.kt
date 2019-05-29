package nz.co.weather.ui.base

import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * BaseView logic class. Provides subclasses with lifecycle events
 * and rx binding to lifecycle with bindToLifecycle.
 *
 * @param view The view to invoke actions on.
 */
abstract class BaseViewLogic<V : BaseView>(val view: V) {

    private val lifecycleManager = CompositeDisposable()

    open fun onStart(state: Bundle?) {}

    open fun onResume() {}

    open fun onPause(state: Bundle) {
        lifecycleManager.clear()
    }

    open fun onStop() {}

    fun bindToLifeCycle(disposable: Disposable) {
        lifecycleManager.add(disposable)
    }
}