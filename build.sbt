import ReleaseTransformations._

organization := "com.madgag"

name := "scala-textmatching"

description := "Unifying simple globs, regex & literal matchers"

scalaVersion := "2.13.5"

crossScalaVersions := Seq(scalaVersion.value, "2.12.13", "3.0.0-RC3")

libraryDependencies ++= Seq(
  "com.madgag" % "globs-for-java" % "0.2",
  "org.scalatest" %% "scalatest" % "3.2.8" % Test
)

scmInfo := Some(ScmInfo(
  url("https://github.com/rtyley/scala-textmatching"),
  "scm:git:git@github.com:rtyley/scala-textmatching.git"
))

publishTo := sonatypePublishToBundle.value

pomExtra := (
<url>https://github.com/rtyley/scala-textmatching</url>
<developers>
  <developer>
    <id>rtyley</id>
    <name>Roberto Tyley</name>
    <url>https://github.com/rtyley</url>
  </developer>
</developers>
)

ThisBuild / licenses := Seq("GPLv3" -> url("http://www.gnu.org/licenses/gpl-3.0.html"))

releaseCrossBuild := true // true if you cross-build the project for multiple Scala versions

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  // For non cross-build projects, use releaseStepCommand("publishSigned")
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)