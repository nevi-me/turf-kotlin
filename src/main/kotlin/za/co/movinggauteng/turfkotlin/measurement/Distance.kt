package za.co.movinggauteng.turfkotlin.measurement

import za.co.movinggauteng.turfkotlin.geojson.Point
import za.co.movinggauteng.turfkotlin.geojson.getCoord
import za.co.movinggauteng.turfkotlin.helpers.Factors
import za.co.movinggauteng.turfkotlin.helpers.Units
import java.lang.StrictMath.*

/**
 * Created by Neville on 25 Feb 2017.
 */

fun distance(start: Point, end: Point, units: Units = Units.TURF_KILOMETERS) : Double {

    val coordinates1 = start.getCoord()
    val coordinates2 = end.getCoord()

    val dLat = toRadians(coordinates2.second - coordinates1.second)
    val dLon = toRadians(coordinates2.first - coordinates1.first)
    val lat1 = toRadians(coordinates1.second)
    val lat2 = toRadians(coordinates2.second)

    val a = pow(sin(dLat/2), 2.0) + pow(sin(dLon/2), 2.0) * cos(lat1) * cos(lat2)

    return 2 * Factors[units]!! * atan2(sqrt(a), sqrt(1-a))
}