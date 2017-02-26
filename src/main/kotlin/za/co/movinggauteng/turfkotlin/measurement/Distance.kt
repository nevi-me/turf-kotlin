package za.co.movinggauteng.turfkotlin.measurement

import za.co.movinggauteng.turfkotlin.geojson.Point
import za.co.movinggauteng.turfkotlin.geojson.getCoord
import za.co.movinggauteng.turfkotlin.helpers.Factors
import za.co.movinggauteng.turfkotlin.helpers.Units
import za.co.movinggauteng.turfkotlin.helpers.point
import java.lang.Math.*

/**
 * Created by Neville on 25 Feb 2017.
 */

fun distance(start: Point, end: Point, units: Units = Units.KILOMETERS) : Double {

    val coordinates1 = start.getCoord()
    val coordinates2 = end.getCoord()

    // adapted from https://github.com/acmeism/RosettaCodeData/blob/master/Task/Haversine-formula/Kotlin/haversine-formula.kotlin
    val λ1 = toRadians(coordinates1.second)
    val λ2 = toRadians(coordinates2.second)
    val Δλ = toRadians(λ2 - λ1)
    val Δφ = toRadians(coordinates2.first - coordinates1.first)
    return 2 * Factors[units]!! * asin(sqrt(pow(sin(Δλ / 2), 2.0) + pow(sin(Δφ / 2), 2.0) * cos(λ1) * cos(λ2)))
}

fun main(args: Array<String>) = println("result: " + distance(point(listOf(-86.67, 36.12)), point(listOf(-118.40, 33.94))))
