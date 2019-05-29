package nz.co.weather.ui.base

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import nz.co.weather.R

/**
 * Base activity to extend from. Requires sub classes to implement view logic
 * pattern. Passes relevant events through to ViewLogic's and manages lifecycle.
 */
abstract class BaseActivity<VL : BaseViewLogic<*>> : AppCompatActivity() {

    protected lateinit var viewLogic: VL

    abstract fun initViewLogic(): VL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewLogic = initViewLogic()
        viewLogic.onStart(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewLogic.onPause(outState)
    }

    override fun onResume() {
        super.onResume()
        viewLogic.onResume()
    }

    override fun onStop() {
        super.onStop()
        viewLogic.onStop()
    }

    fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }.create()
            .show()
    }
}