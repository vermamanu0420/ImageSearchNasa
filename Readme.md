<h1 align="center">Nasa Image Search API</h1>

## App Architecture 

This app uses MVVM architecture (Model View ViewModel) which is also recommended by google which facilitates the separation between the view and the business logic or the backend, Here viewmodel is responsible for exposing only the data objects(LiveData oe Mutable data) to the view and view responds to the changes to the data rather than caring about the businesslogic. 

#### Components of MVVM:

* **Model:** This layer is responsible for the abstraction of the data sources. Model and ViewModel work together to get and save the data.

* **View:** The purpose of this layer is to inform the ViewModel about the user’s action. This layer observes the ViewModel and does not contain any kind of application logic.

* **ViewModel:** It exposes those data streams which are relevant to the View. Moreover, it servers as a link between the Model and the View.


## Libraries used

* **Jetpack Compose:** Jetpack Compose is a modern UI toolkit and declarative framework for building native Android apps. It is part of the larger Android Jetpack set of libraries and tools provided by Google. Jetpack Compose simplifies the process of building user interfaces by enabling developers to create UI components using a reactive and composable approach. It has gained popularity and is increasingly being adopted by Android developers as the preferred approach for building UIs.

* **Retrofit:**  Retrofit is a popular and fast Library for remote server communication. Retrofit supports both synchronous, asynchronous network requests and also support dynamic URLs. It also caches responses to avoid sending duplicate requests and provide better user experience.

* **Coil:** Coil is an image loading library for Android developed by Colin White. It is designed to be a fast, efficient, and lightweight solution for loading images from various sources, such as network URLs, local storage, or even resources within the app. Coil is built using Kotlin and leverages modern Android libraries and features. Coil automatically caches images, reducing network requests and improving performance. It supports both memory caching and disk caching.

* **Hilt:** Hilt is a dependency injection library for Android app development, developed by Google. It is built on top of the popular DI (Dependency Injection) framework Dagger and provides a simplified and opinionated approach to dependency injection in Android applications. Hilt aims to reduce the complexity and boilerplate code typically associated with setting up and managing dependencies in Android projects.

* **Mockito:** Mockito is a popular mocking framework used for unit testing. It provides a simple and flexible API for creating mock objects and defining their behavior during testing scenarios. Mockito is widely used in Android development communities to create mock objects and stub method calls in order to isolate and test specific parts of an application.


## Build/Run

Unzip the project and Open the Project using Android Studio and Build/Run the project either using an emulator or a physical android device.