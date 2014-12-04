ExecutionWareFrontend
=====================================

Installation (dev)
=====================================

Dependencies:
	- play-framework 2.3.6
	- a hibernate compatible database (the following sections will use mysql)
	- cloudify (see section cloudify)
	
1. Install the play framework as described under http://www.playframework.com/documentation/2.3.x/Installing.
2. Install mysql and setup a database and a user for the application.
3. git-clone the repository.
```
git@github.com:dbaur/execWareFrontend.git
```
4. Create a configuration file which will hold your database configuration, e.g. dev.conf
```
include 'application.conf'

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
3. Unzip the dist file at target/universal/executionware-1.0-SNAPSHOT.zip
4. Run the application
```
bin/executionware -Dconfig.file=/path/to/your/config.conf
```
5. Frontend should be available at localhost:9000

Usage
=====================================

If you access the frontend at http://localhost:9000 you will be asked to provide a password.

A default user is available:

username: john.doe@example.com
password: admin

Contact
=====================================

Daniel Baur (University of Ulm)
daniel.baur(at)uni-ulm.de


	
	
