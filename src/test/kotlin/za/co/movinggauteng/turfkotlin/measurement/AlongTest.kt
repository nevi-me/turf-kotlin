package za.co.movinggauteng.turfkotlin.measurement

import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.FunSpec
import za.co.movinggauteng.turfkotlin.geojson.*
import za.co.movinggauteng.turfkotlin.helpers.Units

class AlongTest : FunSpec() {
    init {
        val dcLine = LineString()
        dcLine.coordinates = listOf(
                listOf(-77.0316696166992, 38.878605901789236),
                listOf(-77.02960968017578, 38.88194668656296),
                listOf(-77.02033996582031, 38.88408470638821),
                listOf(-77.02566146850586, 38.885821800123196),
                listOf(-77.02188491821289, 38.88956308852534),
                listOf(-77.01982498168944, 38.89236892551996),
                listOf(-77.02291488647461, 38.89370499941828),
                listOf(-77.02291488647461, 38.89958342598271),
                listOf(-77.01896667480469, 38.90011780426885),
                listOf(-77.01845169067383, 38.90733151751689),
                listOf(-77.02291488647461, 38.907865837489105),
                listOf(-77.02377319335936, 38.91200668090932),
                listOf(-77.02995300292969, 38.91254096569048),
                listOf(-77.03338623046875, 38.91708222394378),
                listOf(-77.03784942626953, 38.920821865485834),
                listOf(-77.03115463256836, 38.92830055730587),
                listOf(-77.03596115112305, 38.931505469602044)
        )

        val dcPoints = FeatureCollection()

        val coordinates: List<Coordinate> = listOf(
                listOf(-77.0316696166992, 38.87406218243845),
                listOf(-77.02325820922852, 38.885688179036094),
                listOf(-77.0222282409668, 38.89744587262311),
                listOf(-77.02377319335936, 38.910804525446686),
                listOf( -77.02840805053711, 38.91441093075183),
                listOf(-77.02840805053711, 38.92402711565758),
                listOf(-77.04008102416992, 38.932707274379595),
                listOf(-76.99390411376953, 38.91387666004744),
                listOf(-77.03269958496094, 38.898648254305215),
                listOf(-77.02342987060545, 38.870587377511235)
        )

        test("along - returns correct Points along a LineString") {
            val fc = FeatureCollection()
            fc.features.add(Feature(geom = along(dcLine, 1.0, Units.MILES)))
            fc.features.add(Feature(geom = along(dcLine, 1.2, Units.MILES)))
            fc.features.add(Feature(geom = along(dcLine, 1.4, Units.MILES)))
            fc.features.add(Feature(geom = along(dcLine, 1.6, Units.MILES)))
            fc.features.add(Feature(geom = along(dcLine, 1.8, Units.MILES)))
            fc.features.add(Feature(geom = along(dcLine, 2.0, Units.MILES)))
            fc.features.add(Feature(geom = along(dcLine, 100.0, Units.MILES)))
            fc.features.add(Feature(geom = along(dcLine, 0.0, Units.MILES)))

            fc.features.forEach {
                println((it.geometry as Point).coordinates)
                it::class.java.shouldBe(Feature::class.java)
                it.type shouldBe "Feature"
                (it.geometry as Point).type shouldBe "Point"
            }

            fc.features.size shouldEqual 8
//            (fc.features.last().geometry as Point).coordinates[0]
        }
    }
}