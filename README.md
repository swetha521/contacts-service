# contacts-service 

REST API for storing users contacts information which has 3 end points for
1.	Creating a contact
2.	Retrieving contact based on contact Id
3.	Retrieving list of contact based on list of contact Ids

This implementation uses data structure to hold the contact information in-memory which will persist only till the application is running.
This application can later be extended to store information in database/outside application to separate data from application layer.

## Assumptions
1. FirstName, LastName, address, phoneNumber fields are mandatory
2. Any contact with same firstname //TODO



## Pre-requisites for running application
1. Access to Github project
2. git
3. JDK 8+
4. maven

## Running the application from terminal with below commands
1. Check out the code from git link - 
2. cd contacts-service
3. mvn clean install
4. cd target
5. java -jar contact-service-1.0-SNAPSHOT.jar
6. open link http://localhost:8080/webjars/swagger-ui/index.html in browser to see api spec
7. Try out the apis using swagger-ui








