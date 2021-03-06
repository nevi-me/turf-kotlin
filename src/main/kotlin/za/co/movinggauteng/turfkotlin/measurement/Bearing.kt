package za.co.movinggauteng.turfkotlin.measurement

import za.co.movinggauteng.turfkotlin.geojson.Point
import za.co.movinggauteng.turfkotlin.geojson.getCoord

/**
 * Created by Neville on 20 Feb 2017.
 */

fun bearing(start: Point, end: Point, final: Boolean = false) : Double {

    if (final) return calculateFinalBearing(start, end)

    val degrees2radians = Math.PI / 180
    val radians2degrees = 180 / Math.PI

    val coordinates1 = start.getCoord()
    val coordinates2 = end.getCoord()

    val lon = Pair(degrees2radians * coordinates1.first, degrees2radians * coordinates2.first)
    val lat = Pair(degrees2radians * coordinates1.second, degrees2radians * coordinates2.second)
    val a = Math.sin(lon.second - lon.first) * Math.cos(lat.second)
    val b = Math.cos(lat.first) * Math.sin(lat.second) -
            Math.sin(lat.first) * Math.cos(lat.second) * Math.cos(lon.second - lon.first)

    return radians2degrees * Math.atan2(a, b)
}

private fun calculateFinalBearing(start: Point, end: Point) : Double {
    // swap the end and start
    var bear = bearing(end, start)
    bear = (bear + 180.0) % 360.0
    return bear
}