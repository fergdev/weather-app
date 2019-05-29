# Fergs Weather app

This is a pretty simple little app to show how I would write android code. I did not use architecture components and just rolled my own variant of the view logic patten (kind of mvvm pattern). Most of the effort was put into the architecture of the app. It uses a fully testable view logic pattern (with unit tests for the logics). It makes use of retrofit for networking, rxjava for observing state (networking and view) and glide for loading images. The views are very simple and are not meant to be indicitive of good design. To improve on the app, I would like to use Dagger to provide dependencies and work to a decent design and use more data from the service (I have just shown temperature and general weather description), however have run out of time and feel what I have shows how I think about writing android code.

There should be no issues running the app, standard gradle command will work ```./gradlew installDebug``` for app, ```./gradlew test``` for tests.

# Improvements
* State saving
* Use dagger to provide dependencies
* Use current location to show weather data
* A good design
* Remove api key from the repository
