import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt.{Build, _}

object SriJavaBinDemoCrossPlatform extends Build {

  import Dependencies._
  import LauncherConfigs._

  val Scala211 = "2.11.7"

  lazy val commonSettings =
    Seq(
      organization := "com.kotsbak",
      version := "0.1.0-SNAPSHOT",
      homepage := Some(url("https://github.com/chandu0101/sri-cross-platform-template")),
      licenses +=("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0")),
      scalaVersion := Scala211,
      scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature",
        "-language:postfixOps", "-language:implicitConversions",
        "-language:higherKinds", "-language:existentials"))


  def DefProject(name: String, id: String = "") = {
    Project(if (id.isEmpty) name else id, file(name))
      .settings(commonSettings: _*)
      .enablePlugins(ScalaJSPlugin)
  }

  def addCommandAliases(m: (String, String)*) = {
    val s = m.map(p => addCommandAlias(p._1, p._2)).reduce(_ ++ _)
    (_: Project).settings(s: _*)
  }

  /** ===================  Test frameworks settings   */

  val scalatestJSSettings = Seq(scalatestJS,
    scalaJSStage in Global := FastOptStage,
    jsDependencies += RuntimeDOM
    //    jsEnv in Test := new NodeJSEnv()
  )


  val utestSettings = Seq(utestJS,
    scalaJSStage in Global := FastOptStage,
    jsDependencies += RuntimeDOM,
    testFrameworks += new TestFramework("utest.runner.Framework")
  )


  // ================================ Module definitions  ================================ //
  lazy val SriJavaBinDemoCrossPlatform = DefProject(".", "root")
    .aggregate(universal, web, mobile)
    .configure(addCommandAliases(
    "wt" -> "; test:compile ; web/test",
    "tt" -> ";+test:compile ;+test/test",
    "T" -> "; clean ;t",
    "TT" -> ";+clean ;tt"))


  lazy val universal = DefProject("universal")
    .settings(universalModuleDeps)

  lazy val web = DefProject("web")
    .dependsOn(universal)
    .settings(webModuleDeps)
    .settings(webLauncher)


  lazy val mobile = DefProject("mobile")
    .dependsOn(universal)
    .settings(mobileModuleDeps)
    .settings(mobilelauncher)

}
