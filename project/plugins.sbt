// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.0")

//test coverage
addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.5")

//gpg
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")
