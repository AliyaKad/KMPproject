package org.itis.project.sharedui.common

import kotlin.math.absoluteValue

fun formatCoord(value: Double, fractionDigits: Int = 2): String {
    val sign = if (value < 0) "-" else ""
    val abs = value.absoluteValue
    return "$sign${"%.${fractionDigits}f".format(abs)}"
}