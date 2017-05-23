package za.co.movinggauteng.turfkotlin.measurement

import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.FunSpec
import za.co.movinggauteng.turfkotlin.helpers.point

class BearingTest : FunSpec() {
    init {
        test("bearing") {
            val start = point(listOf(-75.0, 45.0))
            val end = point(listOf(20.0, 60.0))

            val initialBearing = bearing(start, end)
            initialBearing shouldEqual(37.75 plusOrMinus 0.009)

            val finalBearing = bearing(start, end, true)
            finalBearing shouldEqual(120.01 plusOrMinus 0.009)
        }
    }
}