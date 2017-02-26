package za.co.movinggauteng.turfkotlin.measurement

import za.co.movinggauteng.turfkotlin.geojson.Point
import za.co.movinggauteng.turfkotlin.helpers.getCoord

/**
 * Created by Neville on 20 Feb 2017.
 */

fun bearing(start: Point, end: Point) : Double {
    val degrees2radians = Math.PI / 180
    val radians2degrees = 180 / Math.PI

    val coordinates1 = getCoord(start)
    val coordinates2 = getCoord(end)

    val lon = Pair(degrees2radians * coordinates1.first, degrees2radians * coordinates2.first)
    val lat = Pair(degrees2radians * coordinates1.second, degrees2radians * coordinates2.second)
    val a = Math.sin(lon.second - lon.first) * Math.cos(lat.second)
    val b = Math.cos(lat.first) * Math.sin(lat.second) -
            Math.sin(lat.first) * Math.cos(lat.second) * Math.cos(lon.second - lon.first)

    return radians2degrees * Math.atan2(a, b)
}