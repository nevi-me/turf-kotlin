package za.co.movinggauteng.turfkotlin.geojson

import za.co.movinggauteng.turfkotlin.helpers.getCoord

/**
 * A collection of GeoJSON data structures
 */

open class Geometry {
    open val type: String = ""
}

class Point : Geometry() {
    override val type: String = "Point"
    var coordinates: List<Double> = mutableListOf()
}

class LineString : Geometry() {
    override val type: String = "LineString"
    var coordinates: List<List<Double>> = mutableListOf(mutableListOf())
}

class Polygon : Geometry() {
    override val type: String = "Polygon"
    var coordinates: List<List<List<Double>>> = mutableListOf(mutableListOf(mutableListOf()))
}

class MultiPoint : Geometry() {
    override val type = "MultiPoint"
    var coordinates: List<List<Double>> = mutableListOf(mutableListOf())
}

class MultiLineString : Geometry() {
    override val type = "MultiLineString"
    var coordinates: List<List<List<Double>>> = mutableListOf(mutableListOf(mutableListOf()))
}

class MultiPolygon : Geometry() {
    override val type = "MultiPolygon"
    var coordinates: List<List<List<List<Double>>>> = mutableListOf(mutableListOf(mutableListOf(mutableListOf())))
}

class Feature {
    var type: String = "Feature"
    var geometry : Geometry = Geometry()
    var properties: Any = Unit
}

class FeatureCollection {
    val type = "FeatureCollection"
    var features: MutableList<Feature> = mutableListOf()
}

class GeometryCollection : Geometry() {
    override var type: String = "GeometryCollection"
    var geometries: MutableList<Geometry> = mutableListOf()
    var properties: Any = Unit
}

// extensions
fun Point.getCoord() : Pair<Double, Double> {
    return getCoord(this)
}