ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "uk.gov.nationalarchives"
ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "da-transform-sample-data"
  )

// disable publish with scala version, otherwise artifact name will include scala version
crossPaths := false