import sbt.Keys._

lazy val root = (project in file("."))
  .aggregate(`org-policies-core`, `sbt-org-policies`)
  .settings(noPublishSettings: _*)

lazy val `org-policies-core` = (project in file("core"))
  .enablePlugins(BuildInfoPlugin)
  .settings(moduleName := "org-policies-core")
  .settings(coreSettings: _*)
  .settings(
    buildInfoKeys := Seq(name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "sbtorgpolicies",
  )

lazy val `sbt-org-policies` = (project in file("plugin"))
  .enablePlugins(SbtPlugin)
  .dependsOn(`org-policies-core`)
  .settings(moduleName := "sbt-org-policies")
  .settings(pluginSettings: _*)

pgpPassphrase := Some(Option(System.getenv().get("PGP_PASSPHRASE")).getOrElse("").toCharArray)
pgpPublicRing := file(s"$gpgFolder/pubring.gpg")
pgpSecretRing := file(s"$gpgFolder/secring.gpg")

ThisBuild / parallelExecution := false
Global / cancelable := true
