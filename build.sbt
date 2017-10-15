
lazy val root = (project in file(".")).aggregate(core)

lazy val core = (project in file("core"))
  .settings(commonSettings: _*)
  .settings(publishSettings: _*)
  .settings(
    name := "akka-persistence-sql-async"
  )

lazy val persistenceQuery = (project in file("persistence-query"))
  .settings(commonSettings: _*)
  .settings(
    name := "akka-persistence-query-sql-async",
    libraryDependencies ++= persistenceQueryDependencies
  )
  .dependsOn(core)

lazy val performanceTest = (project in file("performance-test"))
  .settings(commonSettings: _*)
  .settings(
    name := "akka-persistence-sql-async-performance-test"
  )
  .dependsOn(
    core,
    core % "test->test"
  )

lazy val sample = (project in file("sample"))
  .settings(commonSettings: _*)
  .settings(
    name := "akka-persistence-sql-async-sample",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.6.0-M1"
    )
  )
  .dependsOn(core)

lazy val Scala211 = "2.11.8"

lazy val commonSettings = Seq(
  organization := "com.okumin",
  version := "0.4.1",
  scalaVersion := Scala211,
  crossScalaVersions := Seq(Scala211, "2.12.1"),
  parallelExecution in Test := false,
  libraryDependencies := commonDependencies,
  scalacOptions ++= Seq(
    "-deprecation"
  )
)

val akkaVersion = "2.5.6"
val mauricioVersion = "0.2.21"

lazy val commonDependencies = Seq(
  "com.typesafe.akka"   %% "akka-actor"           % akkaVersion,
  "com.typesafe.akka"   %% "akka-persistence"     % akkaVersion,
  "org.scalikejdbc"     %% "scalikejdbc-async"    % "0.9.0",
  "com.github.mauricio" %% "mysql-async"          % mauricioVersion % "provided",
  "com.github.mauricio" %% "postgresql-async"     % mauricioVersion % "provided",
  "com.typesafe.akka"   %% "akka-persistence-tck" % akkaVersion     % "test",
  "com.typesafe.akka"   %% "akka-slf4j"           % akkaVersion     % "test",
  "com.typesafe.akka"   %% "akka-testkit"         % akkaVersion     % "test",
  "org.slf4j"            % "slf4j-log4j12"        % "1.7.21"        % "test"
)

lazy val persistenceQueryDependencies = Seq(
  "com.typesafe.akka" %% "akka-persistence-query-experimental" % akkaVersion
)

lazy val publishSettings = Seq(
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := {
    <url>https://github.com/okumin/akka-persistence-sql-async</url>
    <licenses>
      <license>
        <name>Apache 2 License</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:okumin/akka-persistence-sql-async.git</url>
      <connection>scm:git:git@github.com:okumin/akka-persistence-sql-async.git</connection>
    </scm>
    <developers>
      <developer>
        <id>okumin</id>
        <name>okumin</name>
        <url>http://okumin.com/</url>
      </developer>
    </developers>
  }
)
