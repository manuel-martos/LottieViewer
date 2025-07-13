rootProject.name = "LottieViewer"
include(":composeApp")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositories {
      google()
      mavenCentral()
  }
}

plugins {
  // See https://splitties.github.io/refreshVersions
  id("de.fayard.refreshVersions") version "0.60.5"
}

refreshVersions {
  file("build/tmp/refreshVersions").mkdirs()
  versionsPropertiesFile = file("build/tmp/refreshVersions/versions.properties")
}
