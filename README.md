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
* [Play2-Auth](https://github.com/t2v/play2-auth)
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
* (Optional) [Docker Toolbox](https://www.docker.com/products/docker-toolbox) for Mac and Windows, or [Docker Engine](https://www.docker.com/products/docker#/servers) for Linux
* (Optional) [WIX Tool Set](http://wixtoolset.org/releases/) for Windows MSI

-----------------------------------------------------------------------
###Building the Codebase:
-----------------------------------------------------------------------
* From the root folder containing the `build.sbt` file, the code can be compiled and tested with `sbt clean compile test`
    * Note: the application needs PostgreSQL running when using `sbt run` or when deploying the dist, but the unit test will use H2 In-Memory in PostreSQL compatability mode
* Realtime development can be done using `sbt run` - this allows code to be hot-compiled and updated
    * Note: changes to config and build files should ideally be rebuilt by stopping the application ```Ctrl + D``` and running `sbt clean run`
* You will need to update the `slick.dbs.default.db` entries in `conf/application.conf` to point to your PostgreSQL installation

-----------------------------------------------------------------------
###Deploying:
-----------------------------------------------------------------------
* Several methods are available thanks to the `sbt-native-packager`, the simplest being `sbt dist`, which will drop a self-contained zipped Java application in the `target/universal` folder
    * The zip file can be extracted onto any system running Java 8 JRE and started using the appropriate script in the `bin` directory. On Windows, should the batch file give an error, there is an alternative `start.bat` in the root folder.
    * By default, the config from `conf/application.conf` will be used, so make sure the settings for deploy are correct
    * Alternatively, new config files for other environments can be added to the `conf` folder, and used by adding `-Dconfig.resource=conf/prod.conf` to the command line when executing the packaged app
* Another option is to use the `sbt docker:publishLocal` option if you have Docker installed. This will give you a container image to push to your Docker Hub Repo or Amazon ECR account
    * Using this option, the `docker-compose.yml` file is also useful for testing.
    * The `ENTRYPOINT` for the Dockerfile is the Java application, so parameters (such as conf override) can be passed in as `CMD`s.
* You can create an installable MSI file for Windows using `sbt windows:packageBin`
    * This will install the zip package to a folder where the application can be run from. Running the MSI again allows an uninstall. This requires the WIX Toolset
    * Note: Installing the project in the `C:\Program Files (x86)\` folder can cause issues where the application cannot create the `RUNNING_PID file`; recommended to install on the root of a drive, eg. `C:\play-scala-bootstrap-slick-psql\`
* Following the config options [here](http://www.scala-sbt.org/sbt-native-packager/formats/index.html) alternatives such as Debian PKG and Red-Hat RPM files can also be created
