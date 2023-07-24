# BeyonnexAutomation

# Project Structure : 
- This framework followed Page object Model methodology, Pages package include 3 classes for each screen ( Home Page , Products Selection , checkout ) and it has a one Test Page (WeatherShopperTest) .
- All configuration Data (  Browser Name - email - Credit card info - Zip code ) exist in TestData package (ConfigData)
- the solution is build on docker

# Run test from docker container using the simple command from makefile as follows;
- Clone the project
- Navigate to project root directory
- Open chrome browser -> http://localhost:7900 / password: secret
- Build the docker image run: make image
- Start container run: make start
- Execute test run: make test
- Stop container run: make stop 



- # Tech Stack:
- Selenium Webdriver 
- Maven 
- TestNG 
- Extent Report for reporting in HTML5 format
