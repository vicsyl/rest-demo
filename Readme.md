#Rest demo

## Building and running the demo

* building: mvn clean package spring-boot:run
* running: java -Dspring.profiles.active=dev -jar target/rest-demo-0.0.1-SNAPSHOT.jar

## REST API

* Users and their preferences:
** GET at http://localhost:8080/user/all : get all users' info
** POST at http://localhost:8080/user/ : create new user and her prefs
*** example of JSON body {"name":"Alan Turing","salutation":"Mr.","email":"alan.turing@gmail.com","emailPreferences":[{"id":1},{"id":5}]}
** PUT at http://localhost:8080/user/{id} : update user and/or her prefs
*** example of JSON body {"name":"Alan Turing jr.","salutation":"Mr.","email":"alan.turing@gmail.com","emailPreferences":[{"id":1},{"id":4}]}
** DELETE at http://localhost:8080/user/{id} : delete a user along with her prefs

* User preferences options:
** GET at http://localhost:8080/emailPreferences/all : get all possible preferences options

* Devel operations:
** GET at http://localhost:8080/dev/create : creates test data


## Configuration

* Default, dev and prod (template) conf files:
* application.properties
* application-dev.properties
* application-prod.properties


## Mocks

### Exceptions:
* PreferencesUpdateMock will throw an exception four times in 10 calls.
** this is to test the email sync method how it will cope with errors and to test the retry functionality
** this will render as two exceptions (one on either side of the REST call) in the log
** one of the exceptions has message: Even a mock will sometimes let you down
* PreferencesUpdateMock and UserTestController will be disabled in other than dev profile
** see application-dev.properties and the classes

##TODOs

* Unit tests
* Thorough testing
* I suspect the User <-> EmailPreferenceItem many to many relationship might have some issues




