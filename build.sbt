import de.johoop.jacoco4sbt.JacocoPlugin.jacoco

lazy val root = (project in file(".")).enablePlugins(PlayJava)

name := "executionware-frontend"


//val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()
// val buildNumber = conf.getString("build.number")

//version := "b" + buildNumber + "-1.0-SNAPSHOT"
version := "1.2-SNAPSHOT"

resolvers += "eladron-snapshots" at "http://eladron.e-technik.uni-ulm.de:8081/nexus/content/repositories/snapshots"

resolvers += "eladron-releases" at "http://eladron.e-technik.uni-ulm.de:8081/nexus/content/repositories/releases"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager" % "4.3.5.Final",
  cache,
  "org.mariadb.jdbc" % "mariadb-java-client" % "1.1.7",
  "com.theoryinpractise" % "halbuilder-json" % "3.1.3",
  "org.hamcrest" % "hamcrest-all" % "1.3",
  "com.google.inject" % "guice" % "3.0",
  "com.google.inject.extensions" % "guice-multibindings" % "3.0",
  "com.google.guava" % "guava" % "18.0",
  "commons-codec" % "commons-codec" % "1.10",
  "com.google.code.findbugs" % "jsr305" % "1.3.9",
  "de.uniulm.omi.executionware" % "executionware-service" % "1.2.0-SNAPSHOT"
)

TwirlKeys.templateImports += "dtos._"

jacoco.settings

javaOptions in Test += "-Dconfig.file=conf/test.conf"

publishTo := {
  val nexus = "http://eladron.e-technik.uni-ulm.de:8081/nexus/content/repositories/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "snapshots")
  else
    Some("releases" at nexus + "releases")
}

credentials += Credentials(Path.userHome / ".m2" / ".credentials")

ApiDocSettings.apiDocTask
