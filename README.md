<h1 align="center">Popular Movies App</h1>

<p align="center">
  <img src="https://github.com/mpontus/PopularMoviesApp/raw/master/media/banner.png" alt="Banner image" height="440" />
</p>

<p align="center">This app connects you with <a href="https://www.themoviedb.org/">The Movie Database</a> to help you find new movies.</center>

## Table of Contents

- [Security](#security)
- [Background](#background)
- [Install](#install)
- [Contribute](#contribute)
- [License](#license)

## Security

No data is exchanged with any servers outside of TMDb. TMDb exchange is limitted to requests for movie listings and details.

## Background

This app was build as part of [Udacity Android Nanodegree Program](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801) which I participated in thanks to the scholarship from Google.

## Install

Building the source code depends upon a knowledge of [Android Studio](https://developer.android.com/studio/index.html) and having the correct version of SDK installed.

Create TMDb account and acquire API key (v3) from this page:

https://www.themoviedb.org/settings/api

Save the API key in the global gradle.properies file (`$HOME/.gradle/gradle.properties`):

```
TMDB_API_KEY=<YOUR_API_KEY>
```

Alternatively, you can store the API key in an environment variable:

```bash
$ export ORG_GRADLE_PROJECT_TMDB_API_KEY=<YOUR_API_KEY>
```

Run the following from the root of the source directory:

```bash
$ ./gradlew build
```

This command will create two files:

```
app/build/outputs/apk/release/app-release-unsigned.apk
app/build/outputs/apk/debug/app-debug.apk
```

To install the debug version of the app, run the following:

```bash
$ adb install app/build/outputs/apk/debug/app-debug.apk
```

Installing the release version requires [signing the APK with your certificate](https://developer.android.com/studio/publish/app-signing.html). 

## Contribute

PRs accepted.

## License

[MIT © Mikhail Pontus](./LICENSE)
