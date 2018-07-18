name := "MatrixHandler"

version := "0.1"

scalaVersion := "2.12.6"

import AssemblyKeys._

assemblySettings

val circeVersion = "0.10.0-M1"
val betterFilesVersion = "3.4.0"
val apachePOIVersion = "3.17"
val catsVersion = "1.1.0"
val scalaTestVersion = "3.0.5"

val circe = Seq (
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
  "com.github.pathikrit" % "better-files_2.11" % betterFilesVersion,
  "org.typelevel" %% "cats-core" % catsVersion
) ++ circe

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
)


