package za.co.movinggauteng.turfkotlin.measurement

import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.FunSpec
import za.co.movinggauteng.turfkotlin.geojson.Point
import za.co.movinggauteng.turfkotlin.helpers.Units

/**
 * Created by Neville on 18 Mar 2017.
 */

class DistanceTest : FunSpec() {
    init {
        val pt1 = Point()
        pt1.coordinates = listOf(-75.343, 39.984)
        val pt2 = Point()
        pt2.coordinates = listOf(-75.534, 39.123)

        val kmTolerance = (97.159578031319 plusOrMinus 0.0000001)

        test("Distance calculation should return the correct distance for all units") {
            distance(pt1, pt2, Units.MILES) shouldBe (60.372184058374906 plusOrMinus 0.000001)
            distance(pt1, pt2, Units.TURF_KILOMETERS) shouldBe kmTolerance
//            distance(pt1, pt2, Units.KILOMETERS) shouldBe kmTolerance
            distance(pt1, pt2, Units.RADIANS) shouldBe (0.015245501024842147 plusOrMinus 0.000001)
            distance(pt1, pt2, Units.DEGREES) shouldBe (0.8735028650863798 plusOrMinus 0.000001)
            distance(pt1, pt2) shouldBe kmTolerance
        }
    }
}