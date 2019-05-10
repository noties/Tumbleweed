/**
 * @since 2.0.0-SNAPSHOT
 */
package io.noties.tumbleweed.android.kt

import io.noties.tumbleweed.android.types.Argb

public fun Int.toArgbArray(): FloatArray = Argb.toArray(this)

public fun Int.toArgbArray(array: FloatArray): FloatArray = Argb.toArray(this, array)

public fun FloatArray.toColor() = Argb.fromArray(this)