# Panels
Discord-like side panels for Jetpack Compose Multiplatform.

# Usage

## Swipe Panels

Swipe Panels behaves just like the layout in the Discord Android app.

```kt
SwipePanels(
    start = {
        //Panel on the start
    },
    end = {
        //Panel on the end
    },
) {
    //Main content
}
```

## Static Panels

Static Panels behaves just like the layout in the Discord Desktop app.
```kt
StaticPanels(
    start = {
        //Panel on the start
    },
    end = {
        //Panel on the end
    },
) {
    //Main content
}
```

These are the most basic ways to use the panels. Check out the [samples](/examples) directory for more detailed examples.