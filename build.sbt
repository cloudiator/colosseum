
import de.johoop.jacoco4sbt.JacocoPlugin.jacoco

lazy val root = (project in file(".")).enablePlugins(PlayJava, DebianPlugin)

updateOptions := updateOptions.value.withCachedResolution(true)

scalaVersion := "2.11.8"

name := "colosseum"

//disable scala version suffix
crossPaths := false

description := "A cloud orchestration software."

homepage := Some(url("https://github.com/cloudiator/colosseum"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/cloudiator/colosseum.git"),
    "scm:git:git@github.com:cloudiator/colosseum.git",
    Some("scm:git:git@github.com:cloudiator/colosseum.git")
  )
)

organization := "io.github.cloudiator"

publishMavenStyle := true

version := "0.2.0-SNAPSHOT"

resolvers += Resolver.mavenLocal

resolvers += ("ossrh Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")

resolvers += {
  "jclouds-snapshots" at "https://repository.apache.org/content/repositories/snapshots"
}

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager" % "4.3.5.Final",
  cache,
  "org.mariadb.jdbc" % "mariadb-java-client" % "1.1.7",
  "org.hamcrest" % "hamcrest-all" % "1.3",
  "com.google.inject" % "guice" % "4.1.0",
  "com.google.inject.extensions" % "guice-multibindings" % "4.1.0",
  "com.google.inject.extensions" % "guice-assistedinject" % "4.1.0",
  "com.google.guava" % "guava" % "18.0",
  "commons-codec" % "commons-codec" % "1.10",
  "com.google.code.findbugs" % "jsr305" % "1.3.9",
  "com.github.drapostolos" % "type-parser" % "0.5.0",
  "io.github.cloudiator" % "colosseum-client" % "0.2.0-SNAPSHOT", // sub-dependency of axe-aggr.
  "io.github.cloudiator.sword" % "service" % "0.2.0-SNAPSHOT" exclude("javax.ws.rs", "jsr311-api"),
  "io.github.cloudiator.lance" % "client" % "0.2.0-SNAPSHOT",
  "io.github.cloudiator" % "common" % "0.2.0-SNAPSHOT",
  "io.github.cloudiator" % "visor-rest-client" % "0.2.0-SNAPSHOT",
  "io.github.cloudiator.axe" % "axe-aggregator-common" % "0.2.0-SNAPSHOT",
  "org.reflections" % "reflections" % "0.9.10",
  "org.jgrapht" % "jgrapht-core" % "0.9.2",
  "org.jgrapht" % "jgrapht-ext" % "0.9.2",
  "org.apache.commons" % "commons-lang3" % "3.4"
)

TwirlKeys.templateImports += "dtos._"

jacoco.settings

javaOptions in Test += "-Dconfig.file=conf/test.conf"

// we are skipping the components.execution package
// https://issues.scala-lang.org/browse/SI-4744
// causes problem with SimpleFifoPriorityBlockingQueue
// the api-doc task will still generate scala and java doc
// but ignores the problematic files.

scalacOptions in(Compile, doc) := List("-skip-packages", "components.execution")

publishTo := {
  val snapshots = "https://oss.sonatype.org/content/repositories/snapshots"
  val releases = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at snapshots)
  else
    Some("releases" at releases)
}

pomExtra := (
  <licenses>
    <license>
      <name>The Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
    <developers>
      <developer>
        <name>Daniel Baur</name>
        <email>daniel.baur@uni-ulm.de</email>
        <organization>University Ulm</organization>
        <organizationUrl>
          https://www.uni-ulm.de/en/in/institute-of-information-resource-management.html
        </organizationUrl>
      </developer>
      <developer>
        <name>Joerg Domaschka</name>
        <email>joerg.domaschka@uni-ulm.de</email>
        <organization>University Ulm</organization>
        <organizationUrl>
          https://www.uni-ulm.de/en/in/institute-of-information-resource-management.html
        </organizationUrl>
      </developer>
      <developer>
        <name>Frank Griesinger</name>
        <email>frank.griesinger@uni-ulm.de</email>
        <organization>University Ulm</organization>
        <organizationUrl>
          https://www.uni-ulm.de/en/in/institute-of-information-resource-management.html
        </organizationUrl>
      </developer>
      <developer>
        <name>Daniel Seybold</name>
        <email>daniel.seybold@uni-ulm.de</email>
        <organization>University Ulm</organization>
        <organizationUrl>
          https://www.uni-ulm.de/en/in/institute-of-information-resource-management.html
        </organizationUrl>
      </developer>
    </developers>
  )

useGpg := true

credentials += Credentials(Path.userHome / ".m2" / ".credentials")

ApiDocSettings.apiDocTask

PlayKeys.externalizeResources := false

//Debian configuration

maintainer in Linux := "Daniel Baur <daniel.baur@uni-ulm.de>"

packageSummary in Linux := "Colosseum component of the Cloudiator toolset."

packageDescription := "Colosseum is a cloud orchestration tool. It belongs to the Cloudiator toolset. More information on https://cloudiator.github.io."
