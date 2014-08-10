import sbtrelease._
import ReleaseStateTransformations._
import Dependencies._

releaseSettings

sonatypeSettings

organization := "com.madgag"

name := "scala-textmatching"

description := "Unifying simple globs, regex & literal matchers"

scalaVersion := "2.11.2"

crossScalaVersions := Seq("2.10.4", "2.11.2")

libraryDependencies ++= Seq(
  globs,
  specs2 % "test"
)

scmInfo := Some(ScmInfo(
  url("https://github.com/rtyley/scala-textmatching"),
  "scm:git:git@github.com:rtyley/scala-textmatching.git"
))

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

ReleaseKeys.releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(
    action = state => Project.extract(state).runTask(PgpKeys.publishSigned, state)._1,
    enableCrossBuild = true
  ),
  setNextVersion,
  commitNextVersion,
  ReleaseStep(state => Project.extract(state).runTask(SonatypeKeys.sonatypeReleaseAll, state)._1),
  pushChanges
)

