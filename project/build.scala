import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object ScalaTechnologyPartyBuild extends Build {
  val Organization = "com.whyisitdoingthat"
  val Name = "Scala Technology Party"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.7"
  val ScalatraVersion = "2.4.0-RC2-2"
  val jettyVersion = "9.1.3.v20140225"

  lazy val project = Project (
    "scala-technology-party",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      libraryDependencies ++= Seq(
        "org.scalatra"                %% "scalatra" % ScalatraVersion,
        "org.scalatra"                %% "scalatra-scalate"      % ScalatraVersion,
        "org.scalatra"                %% "scalatra-scalate"      % ScalatraVersion,
        "org.scalatra"                %% "scalatra-atmosphere"   % ScalatraVersion,

        "ch.qos.logback"               % "logback-classic"       % "1.1.2" % "runtime",

        "org.eclipse.jetty"            %  "jetty-plus"           % jettyVersion % "container",
        "org.eclipse.jetty.websocket"  %  "websocket-server"     % jettyVersion % "container",
        "org.eclipse.jetty"            %  "jetty-webapp"         % jettyVersion % "container",
        "javax.servlet"                %  "javax.servlet-api"    % "3.1.0" % "provided"
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}