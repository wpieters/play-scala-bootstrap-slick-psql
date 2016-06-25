play-scala-bootstrap-slick-psql
===============================

This project was creates off various example projects to get Play 2.4 working with Bootstrap, Bootswatch and Slick (with PostgreSQL)

It can be run using either `activator` or `sbt`, packaged using the `sbt-native-packager`, which includes zip, tarball and tar-gzip Java Applications, Docker Containers, Windows MSI installers and others.

-----------------------------------------------------------------------
###Frameworks/Tools Used:
-----------------------------------------------------------------------
* [Play Framework](http://www.playframework.com/)
* [Bootstrap](http://getbootstrap.com/css/)
* [Bootswatch](http://bootswatch.com/)
* [WebJars](http://www.webjars.org/)
* [Slick](http://slick.lightbend.com/)
* [SBT Native Packager](http://www.scala-sbt.org/sbt-native-packager/)

-----------------------------------------------------------------------
###Development Requirements:
-----------------------------------------------------------------------
* [Git](https://git-scm.com/downloads)
* [Scala](http://www.scala-lang.org/download/install.html) and [SBT](http://www.scala-sbt.org/1.0/docs/Setup.html)
    * You can also use [Activator](https://www.lightbend.com/community/core-tools/activator-and-sbt#getting-started) (included in this repo), but it adds a lot of bloat. Great for bootstrapping templates, but then just use `sbt`
* [Postgres](https://www.postgresql.org/download/)
    * Alternatively, [Virtualbox](https://www.virtualbox.org/wiki/Downloads) and [Vagrant](https://www.vagrantup.com/downloads.html)
    * The steps [here](https://wiki.postgresql.org/wiki/PostgreSQL_For_Development_With_Vagrant#Database_Setup) will show how to setup the DB locally

-----------------------------------------------------------------------
###Building the Codebase:
-----------------------------------------------------------------------
* From the root folder containing the `build.sbt` file, the code can be compiled and tested with `sbt clean compile test`
    * Note: In this commit, the Unit Tests require a DB to be available, this still needs to be mocked
* Realtime development can be done using `sbt run` - this allows code to be hot-compiled and updated
    * Note: changes to config and build files should ideally be rebuilt by stopping the application ```Ctrl + D``` and running `sbt clean run`
* You will need to update the `slick.dbs.default.db` entries in `conf/application.conf` to point to your PostgreSQL installation

-----------------------------------------------------------------------
###Deploying:
-----------------------------------------------------------------------
* Several methods are available thanks to the `sbt-native-packager`, the simplest being `sbt dist`, which will drop a self-contained zipped Java application in the `target/universal` folder
* Another option is to use the `sbt docker:publish-local` option if you have Docker installed. This will give you a container image to push to your Docker Hub Repo or Amazon ECR account
    * Using this option, the `docker-compose.yml` file is also useful for testing
* Following the config options [here](http://www.scala-sbt.org/sbt-native-packager/formats/index.html), Windows MSI, Debian PKG and Red-Hat RPM files can also be created
* New config files for other environments can be added to the `conf` folder, and used by adding `-Dconfig.resource=prod.conf` to the command line when executing the packaged app 