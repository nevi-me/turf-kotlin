package za.co.movinggauteng.turfkotlin.geojson

import za.co.movinggauteng.turfkotlin.helpers.getCoord

/**
 * A collection of GeoJSON data structures
 */

open class Geometry

class Point : Geometry() {
    val type: String = "Point"
    var coordinates: Coordinate = mutableListOf()
}

class LineString : Geometry() {
    val type: String = "LineString"
    var coordinates: List<Coordinate> = mutableListOf(mutableListOf())
}

class Polygon : Geometry() {
    val type: String = "Polygon"
    var coordinates: List<List<Coordinate>> = mutableListOf(mutableListOf(mutableListOf()))
}

class MultiPoint : Geometry() {
    val type = "MultiPoint"
    var coordinates: List<Coordinate> = mutableListOf(mutableListOf())
}

class MultiLineString : Geometry() {
    val type = "MultiLineString"
    var coordinates: List<List<Coordinate>> = mutableListOf(mutableListOf(mutableListOf()))
}

class MultiPolygon : Geometry() {
    val type = "MultiPolygon"
    var coordinates: List<List<List<Coordinate>>> = mutableListOf(mutableListOf(mutableListOf(mutableListOf())))
}

class Feature() {
    val type: String = "Feature"
    var geometry : Geometry = Geometry()
    var properties: Any = Unit

    constructor(geom: Geometry) : this() {
        this.geometry = geom
    }

    constructor(geom: Geometry, properties: Any) : this() {
        this.geometry = geom
        this.properties = properties
    }
}

class FeatureCollection {
    val type = "FeatureCollection"
    var features: MutableList<Feature> = mutableListOf()
}

class GeometryCollection : Geometry() {
    val type: String = "GeometryCollection"
    var geometries: MutableList<Geometry> = mutableListOf()
    var properties: Any = Unit
}

// extensions
fun Point.getCoord() : Pair<Double, Double> {
    return getCoord(this)
}

// type aliass
typealias Coordinate = List<Double>