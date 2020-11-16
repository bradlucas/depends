# depends

[![Clojars Project](https://img.shields.io/clojars/v/com.bradlucas/depends.svg)](https://clojars.org/com.bradlucas/depends)

A Clojure application designed to walk a work directory, find all your project.clj files, extract the dependencies and produce a report.

Why is this useful?

If you work on a number of Clojure projects you may at some time want to make sure they are all using the same versions of libraries in use between projects. The report produced by depends helps figure out which projects are using which versions.

## Usage

- java -jar target/depends-1.0.0-standalone.jar DIRECTORY

## License

Copyright © 2015 Brad Lucas

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
