package za.co.movinggauteng.turfkotlin.measurement

import za.co.movinggauteng.turfkotlin.geojson.LineString
import za.co.movinggauteng.turfkotlin.geojson.Point
import za.co.movinggauteng.turfkotlin.helpers.Units
import za.co.movinggauteng.turfkotlin.helpers.point

/**
 * Created by Neville on 21 Feb 2017.
 */

fun along(line: LineString, distance: Double, units: Units) : Point {
    val coords: List<List<Double>> = line.coordinates

    var travelled = 0.0

    coords.forEachIndexed { i, list ->
        if (distance >= travelled && i == coords.size - 1) {
            return@forEachIndexed
        } else if (travelled >= distance) {
            val overshot = distance - travelled
            if (overshot == 0.0) {
                val pt = Point()
                pt.coordinates = list
                return pt
            } else {
                val direction = bearing(point(list), point(coords[i - 1])) - 180
                return destination(point(list), overshot, direction, units)
            }
        } else {
            travelled += distance(point(list), point(coords[i + 1]), units)
        }
    }

    return point(coords[coords.size - 1])
}

