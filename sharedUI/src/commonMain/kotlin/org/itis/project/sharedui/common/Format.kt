package org.itis.project.sharedui.common

import kotlin.math.absoluteValue

fun formatCoord(value: Double, fractionDigits: Int = 2): String {
    val sign = if (value < 0) "-" else ""
    val abs = value.absoluteValue
    return "$sign${"%.${fractionDigits}f".format(abs)}"
}

fun formatBig(value: Double?): String {
    if (value == null) return "—"
    val abs = kotlin.math.abs(value)
    return when {
        abs >= 1e18 -> "${formatCoord(value / 1e18, 2)}·10¹⁸"
        abs >= 1e15 -> "${formatCoord(value / 1e15, 2)}·10¹⁵"
        abs >= 1e12 -> "${formatCoord(value / 1e12, 2)}·10¹²"
        abs >= 1e9 -> "${formatCoord(value / 1e9, 2)} млрд"
        abs >= 1e6 -> "${formatCoord(value / 1e6, 2)} млн"
        abs >= 1e3 -> "${formatCoord(value / 1e3, 2)} тыс"
        else -> formatCoord(value, 2)
    }
}