# Panels ![Maven Central](https://img.shields.io/maven-central/v/io.github.materiiapps.panels/panels?style=flat-square)

Discord-like side panels for Jetpack Compose Multiplatform.

## Installation

```kt
repositories {
  mavenCentral()
}

dependencies {
  implementation("io.github.materiiapps.panels:panels:1.0.0")
  implementation("io.github.materiiapps.panels:panels-material3:1.0.0") // for Material 3 design
}
```

## Usage

### Swipe Panels

Swipe Panels behaves just like the layout in the Discord Android app.

```kt
SwipePanels(
  start = {
    // Start panel
  },
  center = {
    // Center panel
  },
  end = {
    // End panel
  },
)
```

### Static Panels

Static Panels behaves just like the layout in the Discord Desktop app.

```kt
StaticPanels(
  start = {
    // Start panel
  },
  center = {
    // Center panel
  },
  end = {
    // End panel
  },
)
```

These are the most basic ways to use the panels. Check out the [samples](/examples) for more
detailed examples.
