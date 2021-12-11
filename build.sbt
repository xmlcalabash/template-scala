lazy val extTemplateVersion = "2.50.11"

name         := "template-scala"
organization := "com.xmlcalabash"
homepage     := Some(url("https://xmlcalabash.com/"))
version      := extTemplateVersion
scalaVersion := "2.13.5"
//maintainer   := "ndw@nwalsh.com" // for packaging

resolvers += "Restlet" at "https://maven.restlet.com"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.32"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.7"
libraryDependencies += "com.xmlcalabash" % "xml-calabash_2.13" % "2.99.11"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"
