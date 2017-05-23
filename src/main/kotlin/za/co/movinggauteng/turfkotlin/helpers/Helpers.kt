package za.co.movinggauteng.turfkotlin.helpers

import za.co.movinggauteng.turfkotlin.geojson.Coordinate
import za.co.movinggauteng.turfkotlin.geojson.Geometry
import za.co.movinggauteng.turfkotlin.geojson.LineString
import za.co.movinggauteng.turfkotlin.geojson.Point

/**
 * Created by Neville on 21 Feb 2017.
 */

/**
 * @param geometry      a Geometry type, expects a `Feature` or `Point`
 */
fun getCoord(geometry: Geometry) : Pair<Double, Double> {
    when(geometry) {
        is Point -> {
            return pairFromPoint(geometry.coordinates)
        }
//        is Feature -> {
//            when(geometry.geometry) {
//                is Point -> return pairFromPoint((geometry.geometry as Point).coordinates)
//                else -> throw Exception("A feature or point geometry is required")
//            }
//        }
        else -> throw Exception("A feature or point geometry is required")
    }
}

fun pairFromPoint(coordinates: List<Double>) : Pair<Double, Double> {
    when(coordinates.size) {
        2 -> return Pair(coordinates.first(), coordinates.last())
        else -> throw Exception("Coordinates should only have two Doubles")
    }
}

fun distanceToRadians(distance: Double, units: Units = Units.TURF_KILOMETERS) : Double {
    val factor = Factors[units] ?: throw Exception("Invalid unit supplied")
    return distance / factor
}

fun point(list: Coordinate) : Point {
    val point = Point()
    point.coordinates = list
    return point
}

fun lineString(listOfLines: List<Coordinate>) : LineString {
    val line = LineString()
    line.coordinates = listOfLines
    return line
}

enum class Units {
    DEGREES, RADIANS, MILES, KILOMETERS, TURF_KILOMETERS, NAUTICAL_MILES, INCHES, YARDS, METERS, FEET
}

val Factors: Map<Units, Double> = mapOf(
        Pair(Units.DEGREES, 57.2957795),
        Pair(Units.RADIANS, 1.0),
        Pair(Units.MILES, 3960.0),
        Pair(Units.KILOMETERS, 6371.0),
        Pair(Units.TURF_KILOMETERS, 6373.0),
        Pair(Units.NAUTICAL_MILES, 3441.145),
        Pair(Units.INCHES, 250905600.0),
        Pair(Units.YARDS, 6969600.0),
        Pair(Units.METERS, 6373000.0),
        Pair(Units.FEET, 20908792.65)
)
