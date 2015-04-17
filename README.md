Colloseum
=====================================

Installation (dev)
=====================================

Dependencies:
	- play-framework 2.3.8
	- a hibernate compatible database (the following sections will use mysql)
	- JDK 8
	
1. Install the play framework as described under http://www.playframework.com/documentation/2.3.x/Installing.
2. Install mysql and setup a database and a user for the application.
3. git-clone the repository.
```
git@github.com:cloudiator/colloseum.git
```
4. Create a configuration file like below which will hold your database configuration, e.g. dev.conf.
```
include "application.conf"

# Secret key
# ~~~~~
# The secret key is used to secure cryptographics functions.
# If you deploy your application to several instances be sure to use the same key!
application.secret="A_VERY_SECRET_KEY"

# Database configuration
# ~~~~~
db.default.driver=org.mariadb.jdbc.Driver
db.default.url="mysql://dbUser:dbPassword@localhost/dbName"
```
5. Switch to the root of your clone and run
```
activator -Dconfig.file=/path/to/your/config.conf run
```
6. Play starts the application and the server is available at http://localhost:9000

Installation and Building (prod)
=====================================

1. Follow steps 1-4 of the Installation guide above.
2. Run "activator clean dist" to build the project.
3. Unzip the dist file at target/universal/colloseum-{version}.zip
4. Run the application
```
bin/colloseum -Dconfig.file=/path/to/your/config.conf
```
5. Colloseum should be available at localhost:9000

Usage
=====================================

If you access the frontend at http://localhost:9000/login you will be asked to provide a password.

A default user is available:

username: john.doe@example.com
password: admin

Documentation
=====================================

[Documentation can be found here](documentation/README.md)

Contact
=====================================

Daniel Baur (University of Ulm)
daniel.baur(at)uni-ulm.de


	
	
