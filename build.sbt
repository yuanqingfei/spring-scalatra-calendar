import com.earldouglas.xwp.ContainerPlugin.autoImport._
import com.earldouglas.xwp.TomcatPlugin
import com.earldouglas.xwp.WebappPlugin.autoImport._

name := "spring-scalatra-angular-calendar"
version := "1.0.0"
scalaVersion := "2.11.8"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")


val springBootVersion = "1.3.5.RELEASE"
val ScalatraVersion = "2.4.0"
val scalatraStack = Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "com.alibaba" % "fastjson" % "1.2.12",

  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime"
//  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
)

val springbootStack = Seq(
  "org.springframework.boot" % "spring-boot-starter" % "1.3.5.RELEASE"
)

val calendarStack = Seq(
  "com.ibm.icu" % "icu4j" % "57.1"
)

libraryDependencies ++= scalatraStack ++ springbootStack ++ calendarStack


enablePlugins(TomcatPlugin)
//containerPort := 9090

containerLibs in Tomcat := Seq("com.github.jsimone" % "webapp-runner" % "8.0.33.0" intransitive())
//containerMain in Tomcat := "webapp.runner.launch.Main"

enablePlugins(sbtdocker.DockerPlugin)

docker <<= docker.dependsOn(webappPrepare)

dockerfile in docker := {
  //  target in webappPrepare := target.value / "WebContent"
  val webDir: File = target.value / "webapp"

  new Dockerfile {
    from("192.168.0.117:5000/tomcat:8.0-jre8")
    run("rm", "-rf", "/usr/local/tomcat/webapps/ROOT")
    copy(webDir, "/usr/local/tomcat/webapps/ROOT")
    //copyRaw("target/webapp", "/var/lib/jetty/webapps/ROOT") //for jetty
    expose(8080)
  }
}




