lazy val extTemplateVersion = "2.50.1"

name         := "template-scala"
organization := "com.xmlcalabash"
homepage     := Some(url("https://xmlcalabash.com/"))
version      := extTemplateVersion
scalaVersion := "2.13.5"
//maintainer   := "ndw@nwalsh.com" // for packaging

resolvers += "Restlet" at "https://maven.restlet.com"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.32"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.6"
libraryDependencies += "com.xmlcalabash" % "xml-calabash_2.13" % "2.99.5"
