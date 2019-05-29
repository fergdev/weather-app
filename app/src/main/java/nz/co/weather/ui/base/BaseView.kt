package nz.co.weather.ui.base

/**
 * Base view for VewLogic pattern.
 */
interface BaseView {

    /**
     * Show an error dialog with the given message.
     *
     * @param messasge The message to show.
     */
    fun showError(message: String)

}

