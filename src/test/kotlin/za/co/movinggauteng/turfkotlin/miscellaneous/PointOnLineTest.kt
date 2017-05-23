package za.co.movinggauteng.turfkotlin.miscellaneous

import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.FunSpec
import za.co.movinggauteng.turfkotlin.geojson.LineString
import za.co.movinggauteng.turfkotlin.helpers.lineString
import za.co.movinggauteng.turfkotlin.helpers.point

class PointOnLineTest: FunSpec() {
    init {

        test("pointOnLine - first point") {
            val line = LineString()
            line.coordinates = listOf(
                    listOf(-122.45717525482178,37.72003306385638),
                    listOf(-122.45717525482178,37.718242366859215)
            )
            val pt = point(listOf(-122.45717525482178,37.72003306385638))

            val snapped = pointOnLine(line, pt)

            snapped.first.coordinates.first() shouldBe(pt.coordinates.first() plusOrMinus .000001)
            snapped.first.coordinates.last() shouldBe(pt.coordinates.last() plusOrMinus .000001)

            // TODO the JS implementation returns a Feature which has properties, a location prop is tested
            snapped.third shouldBe(0.0 plusOrMinus 0.001) // 1 meter
        }

        test("pointOnLine - points behind first point") {
            val line = LineString()
            line.coordinates = listOf(
                    listOf(-122.45717525482178,37.72003306385638),
                    listOf(-122.45717525482178,37.718242366859215)
            )
            val first = point(line.coordinates.first())

            val pts = listOf(
                    point(listOf(-122.45717525482178,37.72009306385638)),
                    point(listOf(-122.45717525482178,37.82009306385638)),
                    point(listOf(-122.45716525482178,37.72009306385638)),
                    point(listOf(-122.45516525482178,37.72009306385638))
            )

            val expectedLocation = listOf(0.0, 0.0, 0.0, 0.0)

            pts.zip(expectedLocation).forEach {
                val snapped = pointOnLine(line, it.first)
                first.coordinates shouldEqual snapped.first.coordinates
                snapped.third shouldEqual it.second
            }
        }

        test("pointOnLine - points in front of last point") {
            val line = LineString()
            line.coordinates = listOf(
                    listOf(-122.45616137981413,37.72125936929241),
                    listOf(-122.45717525482178,37.72003306385638),
                    listOf(-122.45717525482178,37.718242366859215)
            )
            val last = point(line.coordinates.last())

            val pts = listOf(
                    point(listOf(-122.45696067810057,37.71814052497085)),
                    point(listOf(-122.4573630094528,37.71813203814049)),
                    point(listOf(-122.45730936527252,37.71797927502795)),
                    point(listOf(-122.45718061923981,37.71704571582896))
            )

            val expectedLocation = listOf(
                    0.36215983244260574,
                    0.36215983244260574,
                    0.36215983244260574,
                    0.36215983244260574
            )

            pts.zip(expectedLocation).forEach {
                val snapped = pointOnLine(line, it.first)
                last.coordinates shouldEqual snapped.first.coordinates
                snapped.third shouldEqual(it.second plusOrMinus 0.0001)
            }
        }

        // TODO write more tests
        test("pointOnLine - points on joints") {
            val lines = listOf(
                    lineString(listOf(listOf(-122.45616137981413,37.72125936929241),listOf(-122.45717525482178,37.72003306385638),listOf(-122.45717525482178,37.718242366859215))),
                    lineString(listOf(listOf(26.279296875,31.728167146023935),listOf(21.796875,32.69486597787505),listOf(18.80859375,29.99300228455108),listOf(12.919921874999998,33.137551192346145),listOf(10.1953125,35.60371874069731),listOf(4.921875,36.527294814546245),listOf(-1.669921875,36.527294814546245),listOf(-5.44921875,34.74161249883172),listOf(-8.7890625,32.99023555965106))),
                    lineString(listOf(listOf(-0.10919809341430663,51.52204224896724),listOf(-0.10923027992248535,51.521942114455435),listOf(-0.10916590690612793,51.52186200668747),listOf(-0.10904788970947266,51.52177522311313),listOf(-0.10886549949645996,51.521601655468345),listOf(-0.10874748229980469,51.52138135712038),listOf(-0.10855436325073242,51.5206870765674),listOf(-0.10843634605407713,51.52027984939518),listOf(-0.10839343070983887,51.519952729849024),listOf(-0.10817885398864746,51.51957887606202),listOf(-0.10814666748046874,51.51928513164789),listOf(-0.10789990425109863,51.518624199789016),listOf(-0.10759949684143065,51.51778299991493)))
            )

            var expectedLocation = listOf(
                listOf( 0.0, 0.16298090408353966, 0.36215983244260574 ),
                listOf( 0.0, 435.28883447486135, 848.6494800799092, 1507.098441657023, 1878.3021695683806, 2363.3789829162574, 2952.4473854390017, 3347.5942918385786, 3712.3870894188008 ),
                listOf( 0.0, 0.011358519828719417, 0.02132062044562163, 0.03396549327758369, 0.05703192228718658, 0.08286114111611394, 0.16123397358954772, 0.2072603600715873, 0.24376684566141119, 0.2879229927633058, 0.3206719917049851, 0.3961452220802617, 0.49199418947985657 )
            )

            lines.forEachIndexed { i, line ->
                line.coordinates.map { point(it) }.forEachIndexed { j, pt ->
                    val snapped = pointOnLine(line, pt)
                    pt.coordinates shouldEqual snapped.first.coordinates
                    snapped.third shouldEqual(expectedLocation[i][j] plusOrMinus 0.00001)
                }
            }
        }

        test("pointOnLine - points on top of line") {

        }
    }
}