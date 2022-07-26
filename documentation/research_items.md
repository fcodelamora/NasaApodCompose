## Research Items

Summary of a few of the things I've been trying out while developing this sample app.

### Passing data between screens (Jetpack Compose)

#### `MainActivity`

* Jetpack Compose does not appear to have a straight forward approach to share
  Parcelable/Serializable navigation arguments. Currently trying different approaches on how to
  share this kind of information.
* Testing an approach on how to provide app-wide Progress/Error Views. The current approach requires
  the viewModels to be handled by the MainActivity instead of each View.

### Mock API inside the App

`:mock:apis` allows for continuous development even if the server is not available.

### Provide additional development mode only options

With the mock API in place, additional options can be provided to test long response times, and
specific API errors. It should be possible to provide other debug options, ex. launch test
notifications through the Debug menu.

### :common module for shared resources

define a `:common` module for resources that need to be made available to the `:app` and `:feature`
modules.

Kotlin extensions that need to be accessed through the app can also be found here.

## Location of UI components

`:common:resources` is the preferred location for UI Components that are to be utilized through
different modules.

### Jetpack Compose screens

- Separate the screens entry point and the content
- Use state hoisting to delegate as much as possible to the ViewModel

### UI Components Catalogs

`:common:resources`  hosts the building blocks for all screens are provided as catalogs. (Colors,
Themes, Button Composables, Text Composables...). A default `CatalogView` is provided to
facilitate `@Preview` creation.

### Re-use of composables

- Used the main gallery screen composable with an additional flag to allow for its re-use inside the
  settings screen. This allows the user to have a more visual way to see how the app interface
  changes according to the options selected in the settings.
    - A disadvantage I noticed is that Accessibility functions like "Talkback" will identify this
      composable components as well, this could generate confusion in the user when inactive buttons
      are read aloud.

#### Add anonymous composables to other composables

- As seen in `SettingsScreen#RadioGroupThemes`, anonymous composables are used to re-utilize other
  composables that may require customizing their parameters before returning the composable.

## Pending Items

- Compose Previews are broken when using enums or data classes
- Implement internationalization of Strings with a Library that has Multiplatform support within
  their roadmap.
- Refactor `LocalDate` to a different `Date` util for the app to be capable to target a lower
  Android SDK.
- Refactor `:core` modules as necessary to make it Multiplatform.
- Make `:feature` modules into Android dynamic modules. The current approach to navigation does not
  seem to be compatible with it.
- Consolidate the remaining `Text()` composables into the Text composables catalog.
- Provide alternative screens for `Landscape` orientations.
- Download Image File (Proper Android File Management as of Android 12)
- Share image as file instead of URL
