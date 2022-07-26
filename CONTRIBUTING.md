This project code is formatted in 2 steps to facilitate `diff` reviews as well as a consistent
format across environments.

### Android Studio

1) `Settings/Preferences` > `Tools` > `Actions on Save`, select the following items:

- [x] Reformat Code
- [x] Optimize Imports

2) `Settings/Preferences` > `Experimental`, select:

- [x] Do not build Gradle task list during Gradle sync

### ktlint

A pre-commit hook file is provided in the root of the project. Enable it using below commands in the
project root folder. (Tested on Ubuntu 20.04 LTS)

* `cp pre-commit-ktlint .git/hooks/pre-commit`
* `chmod 755 .git/hooks/pre-commit`

Below Tasks are executed to check and format code:

*`./gradlew ktlintCheck`
*`./gradlew --continue ktlintFormat`
