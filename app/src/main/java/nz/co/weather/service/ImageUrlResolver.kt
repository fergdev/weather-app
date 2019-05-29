package nz.co.weather.service

/**
 * Helper class format image icon urls.
 */
object ImageUrlResolver {

    /**
     * Convert image name into corresponding url.
     *
     * @param image The image name.
     */
    fun resolveImageUrl(image: String): String {
        return "https://openweathermap.org/img/w/%s.png".format(image)
    }
}