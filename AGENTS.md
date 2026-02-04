# Agent Guide - NASA APOD

This document provides guidance for agents working on the NASA APOD project.

## Project Overview

NASA APOD is a sample application that demonstrates the use of Jetpack Compose and Clean Architecture in a multi-module Android project. It fetches data from the NASA Astronomical Picture of the Day API.

## Architecture

The project follows Clean Architecture principles, divided into several modules. For a detailed overview of the architecture, refer to the [Architecture section in README.md](README.md#architecture).

### Dependency Flow

The dependency flow generally follows:
`app -> features -> common -> core <- provide`

- `app` depends on `features`, `common`, and `provide` (for DI).
- `features` depends on `common` and `core`.
- `provide` depends on `core` (implementing its interfaces).
- `core` is the central module with no external project dependencies.

### Modules

- **`:app`**: The main Android application module.
  - [app/build.gradle.kts](app/build.gradle.kts)
- **`:core`**: Contains business logic and is intended to be framework-independent.
  - [core/entities](core/entities) - Domain objects.
  - [core/usecases](core/usecases) - Business logic. Reference: [GetPicturesOfTheDayForMonthUseCase.kt](core/usecases/src/main/kotlin/com/training/nasa/apod/core/usecases/feature/gallery/GetPicturesOfTheDayForMonthUseCase.kt)
  - [core/repositories](core/repositories) - Repository interfaces. Reference: [IPictureOfTheDayRepository.kt](core/repositories/src/main/kotlin/com/training/nasa/apod/core/repository/IPictureOfTheDayRepository.kt)
  - [core/apis](core/apis) - API interfaces. Reference: [INasaApodApi.kt](core/apis/src/main/kotlin/com/training/nasa/apod/core/api/INasaApodApi.kt)
  - [core/datasources](core/datasources) - Data persistence interfaces. Reference: [IUserPreferencesDataSource.kt](core/datasources/src/main/kotlin/com/training/nasa/apod/core/datasources/IUserPreferencesDataSource.kt)
- **`:features`**: Contains feature-specific UI and ViewModels.
  - [features/gallery](features/gallery) - Gallery feature. Reference: [GalleryScreen.kt](features/gallery/src/main/kotlin/com/training/nasa/apod/features/gallery/ui/screens/GalleryScreen.kt)
- **`:provide`**: Implementations of the interfaces defined in `:core`.
  - [provide/apis](provide/apis) - API implementations.
  - [provide/repositories](provide/repositories) - Repository implementations.
- **`:common`**: Shared components and resources.
  - [common/resources](common/resources) - UI components and resources.
  - [common/di](common/di) - Dependency injection modules.
- **`buildSrc`**: Dependency management and common Gradle scripts.
  - [buildSrc/build.gradle.kts](buildSrc/build.gradle.kts)
  - [Dependencies.kt](buildSrc/src/main/kotlin/Dependencies.kt) - Centralized dependency versions and libraries.
  - [BuildModules.kt](buildSrc/src/main/kotlin/BuildModules.kt) - List of project modules.

## Project Structure

- **[settings.gradle.kts](settings.gradle.kts)**: Defines the project name and included modules.

## Setup and Development

### Configuration

The project requires a NASA API key. See [README.md](README.md#building-the-project--nasa-apis-key) for instructions on how to set it up in `project.properties`.

### Coding Standards

The project uses `ktlint` for code formatting. Detailed instructions on setting up the pre-commit hook and Android Studio are in [CONTRIBUTING.md](CONTRIBUTING.md).

- **Formatting**: `./gradlew ktlintFormat`
- **Linting**: `./gradlew ktlintCheck`

### Testing

Tests should be run to ensure no regressions.
- **Running Tests**: `./gradlew test` (Runs all unit tests)
- **Feature Tests**: Specific module tests can be run, e.g., `./gradlew :core:usecases:test`.

### Flavors

The app has multiple flavors (`dev`, `qa`, `product`). The `dev` flavor is particularly useful for development as it uses mock data.
- Reference: [app/build.gradle.kts](app/build.gradle.kts) (See `productFlavors` block)

## Technical Debt and Research

A list of research items and WIP goals can be found in [documentation/research_items.md](documentation/research_items.md).
