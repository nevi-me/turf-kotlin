package za.co.movinggauteng.turfkotlin.measurement

import za.co.movinggauteng.turfkotlin.geojson.LineString
import za.co.movinggauteng.turfkotlin.geojson.Point
import za.co.movinggauteng.turfkotlin.helpers.Units
import za.co.movinggauteng.turfkotlin.helpers.point

/**
 * Takes a Point and a LineString, and calculates the closest Point on the LineString
 */

fun pointOnLine(line: LineString, pt: Point, units: Units = Units.KILOMETERS) : Triple<Point, Int, Double> {
    val coords = line.coordinates

    var closestPt = point(listOf(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
    var closestPtDist = Double.POSITIVE_INFINITY

    var length = 0.0

    var closestPtIndex = 0
    var closestPtLocation = 0.0


    coords.take(coords.size - 1).forEachIndexed { index, list ->

        var start = point(list)
        var stop = point(coords[index + 1])
        var startDist = distance(pt, start, units)
        var stopDist = distance(pt, stop, units)
        var sectionLength = distance(start, stop, units)
        // perpendicular
        var heightDistance = maxOf(startDist, stopDist)
        var direction = bearing(start, stop)
        var perpendicularPt1 = destination(pt, heightDistance, direction + 90.0, units)
        var perpendicularPt2 = destination(pt, heightDistance, direction - 90.0, units)
        val intersect = lineIntersects(
                perpendicularPt1.coordinates[0],
                perpendicularPt1.coordinates[1],
                perpendicularPt2.coordinates[0],
                perpendicularPt2.coordinates[1],
                start.coordinates[0],
                start.coordinates[1],
                stop.coordinates[0],
                stop.coordinates[1]
        )

        var intersectPt: Point? = null
        var intersectPtDist: Double = 0.0
        var intersectPtLocation: Double = 0.0

        if (intersect.first) {
            intersectPt = intersect.second
            intersectPtDist = distance(pt, intersectPt, units)
            intersectPtLocation = length + distance(start, intersectPt, units)
        }

        if (startDist < closestPtDist) {
            closestPt = start
            closestPtDist = startDist
            closestPtIndex = index
            closestPtLocation = length
        }

        if (stopDist < closestPtDist) {
            closestPt = stop
            closestPtDist = stopDist
            closestPtIndex = index + 1
            closestPtLocation = length + sectionLength
        }

        if (intersectPt != null && intersectPtDist < closestPtDist) {
            closestPt = intersectPt
            closestPtDist = intersectPtDist
            closestPtIndex = index
            closestPtLocation = intersectPtLocation
        }

        // update length
        length += sectionLength
    }

    return Triple(closestPt, closestPtIndex, closestPtLocation)
}

/**
 * TODO the turf.js equivalent of this function returns a JS object instead of a Boolean
 * I will change to an appropriate data structure if I encounter other places where this intersection is used
 */
fun lineIntersects(line1StartX: Double, line1StartY: Double, line1EndX: Double, line1EndY: Double, line2StartX: Double,
                   line2StartY: Double, line2EndX: Double, line2EndY: Double) : Pair<Boolean, Point> {

    val denominator: Double = ((line2EndY - line2StartY) * (line1EndX - line1StartX)) - ((line2EndX - line2StartX)
            * (line1EndY - line1StartY))

    val numerator1: Double
    val numerator2: Double

    var resultX = Double.POSITIVE_INFINITY
    var resultY = Double.POSITIVE_INFINITY
    var onLine1 = false
    var onLine2 = false

    if (denominator == 0.0) {
        if (resultX != Double.POSITIVE_INFINITY && resultY !== Double.POSITIVE_INFINITY) {
            return Pair(true, point(listOf(resultY, resultX)))
        } else {
            return Pair(false, point(listOf(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)))
        }
    }

    var a: Double = line1StartY - line2StartY
    var b: Double = line1StartX - line2StartX
    numerator1 = ((line2EndX - line2StartX) * a) - ((line2EndY - line2StartY) * b)
    numerator2 = ((line1EndX - line1StartX) * a) - ((line1EndY - line1StartY) * b)

    a = numerator1 / denominator
    b = numerator2 / denominator

    // if we cast these lines infinitely in both directions, they intersect here:
    resultX = line1StartX + (a * (line1EndX - line1StartX))
    resultY = line1StartY + (a * (line1EndY - line1StartY))

    // if line1 is a segment and line2 is infinite, they intersect if:
    if (a > 0 && a < 1) {
        onLine1 = true
    }
    // if line2 is a segment and line1 is infinite, they intersect if:
    if (b > 0 && b < 1) {
        onLine2 = true
    }
    // if line1 and line2 are segments, they intersect if both of the above are true
    if (onLine1 && onLine2) {
        return Pair(true, point(listOf(resultX, resultY)))
    } else {
        return Pair(false, point(listOf(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)))
    }
}