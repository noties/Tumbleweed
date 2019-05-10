package ru.noties.tumbleweed.android.kt

import ru.noties.tumbleweed.android.types.Argb

public fun Int.toArgbArray(): FloatArray = Argb.toArray(this)

public fun Int.toArgbArray(array: FloatArray): FloatArray = Argb.toArray(this, array)

public fun FloatArray.toColor() = Argb.fromArray(this)