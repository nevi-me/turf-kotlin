package za.co.movinggauteng.turfkotlin.measurement

import za.co.movinggauteng.turfkotlin.geojson.Point
import za.co.movinggauteng.turfkotlin.helpers.Units
import za.co.movinggauteng.turfkotlin.helpers.distanceToRadians
import za.co.movinggauteng.turfkotlin.helpers.getCoord
import za.co.movinggauteng.turfkotlin.helpers.point

/**
 * Created by Neville on 25 Feb 2017.
 */

fun destination(from: Point, distance: Double, bearing: Double, units: Units) : Point {
    val degrees2radians = Math.PI / 180
    val radians2degrees = 180 / Math.PI
    val coordinates1 = getCoord(from)
    val longitude1 = degrees2radians * coordinates1.first
    val latitude1 = degrees2radians * coordinates1.second
    val bearing_rad = degrees2radians * bearing

    val radians = distanceToRadians(distance, units)

    val latitude2 = Math.asin(Math.sin(latitude1) * Math.cos(radians) +
            Math.cos(latitude1) * Math.sin(radians) * Math.cos(bearing_rad))
    val longitude2 = longitude1 + Math.atan2(Math.sin(bearing_rad) *
            Math.sin(radians) * Math.cos(latitude1),
            Math.cos(radians) - Math.sin(latitude1) * Math.sin(latitude2))

    return point(listOf(radians2degrees * longitude2, radians2degrees * latitude2))
}