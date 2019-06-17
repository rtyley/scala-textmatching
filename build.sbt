import ReleaseTransformations._

organization := "com.madgag"

name := "scala-textmatching"

description := "Unifying simple globs, regex & literal matchers"

scalaVersion := "2.13.0"

crossScalaVersions := Seq(scalaVersion.value, "2.12.8")

libraryDependencies ++= Seq(
  "com.madgag" % "globs-for-java" % "0.2",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

scmInfo := Some(ScmInfo(
  url("https://github.com/rtyley/scala-textmatching"),
  "scm:git:git@github.com:rtyley/scala-textmatching.git"
))

publishTo :=
  Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)


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

licenses in ThisBuild := Seq("GPLv3" -> url("http://www.gnu.org/licenses/gpl-3.0.html"))

releaseCrossBuild := true // true if you cross-build the project for multiple Scala versions

releasePublishArtifactsAction := PgpKeys.publishSigned.value // Use publishSigned in publishArtifacts step

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)


