/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import sbt.Keys._
import sbt._

// Adapted from: https://github.com/yesnault/Play20StartApp
object ApiDocSettings {

  val apiDocTask = TaskKey[Unit]("api-doc", "run scaladoc and javadoc, placing results in target/api") <<= (fullClasspath in Test, compilers, streams) map { (classpath, cs, s) =>

    val apiDir = "target/doc/api"

    IO.delete(file(apiDir))

    // Scaladoc
    var scalaVersionForSbt = Option(System.getProperty("scala.version")).getOrElse("2.10.0")

    val sourceFiles =
      (file("app") ** "*.scala").get ++
        (file("test") ** "*.scala").get ++
        (file("target/scala-" + scalaVersionForSbt + "/src_managed/main/views/html") ** "*.scala").get
    new Scaladoc(10, cs.scalac)("Scala API", sourceFiles, classpath.map(_.data), file(apiDir + "/scala"), Nil, s.log)

    // Javadoc
    val javaSources = Seq(file("test"), file("app")).mkString(":")
    val javaApiTarget = file(apiDir + "/java")
    val javaClasspath = classpath.map(_.data).mkString(":")
    val javaPackages = "controllers:models:tests:components:forms"

    val cmd = <x>javadoc -Xdoclint:none -linksource -sourcepath
      {javaSources}
      -d
      {javaApiTarget}
      -subpackages
      {javaPackages}
      -classpath
      {javaClasspath}
    </x>
    //println("Executing: "+cmd.text)
    cmd ! s.log

    println("API documentation in " + apiDir)
  }
}
