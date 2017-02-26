# turf-kotlin

turf-kotlin is a direct port of [https://github.com/Turfjs/turf](https://github.com/Turfjs/turf) written in Kotlin.

## Motivation

turf.js is great, and as I couldn't find a JVM alternative that's as easy to use. I've had a look at the [Java Topology Suite](https://en.wikipedia.org/wiki/GeoTools) and its various implementations, and found it to be too much for my use-case (especially for mobile on Android).

I have been using turf.js on the JVM via Nashorn (Java8+) with relative success. I've had to do the below a lot, and I find debugging to be a pain.

```kotlin
val nashorn: ScriptEngine = manager.getEngineByName("nashorn")
nashorn.eval(FileReader("./path/to/turf.js"))
nashorn.eval("JSON.stringify(turf.along($pathFeature, $cumulativeDistance, 'kilometers').geometry)") as String
```

One of the other things that I picked up before embarking on this exercise was that turf.js uses `Pair(Units.KILOMETERS, 6373.0)` instead of `Pair(Units.KILOMETERS, 6371.0)` or other estimates. This was a source of a bug which I struggled to identify because Google use 6371.0m for their Earth radius.

I'm trying an approach where I'll be able to use either 6373.0m or 6371.0m to see if that resolves some of the issues I've faced.

## Status

This is by no means usable. I've started implementing the functions that I use the most, so I can make progress on my projects, so expect a lot of functions to be missing.

I'm also experimenting with ways of using "normal" classes vs. data classes, so the library will evolve over time with regards to how it's used.

## Contributing

You can reach out to me if you'd like to assist me. I'm undertaking to spend 20-30 minutes each day in the evenings working on this. I normally have some down-time before sleeping, so I'll use that low-concentration period to port most of the code.

## License

Apache 2.0, I'll add license files and all other formalities as I go along.