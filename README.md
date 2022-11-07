# How to run:

## Prerequisites:
    This project is using Java 18 and Maven 3.8.6. No additional dependencies are required to run. 
    For testing API use Curl or Postman (recommended)

## Install and run:
1. Clone project from here: https://github.com/carousel/landrouter.git or using SSH git@github.com:carousel/landrouter.git 
2. Enable annotation processor in the IDE and align pom.xml versions with local Java version
3. Move to the root of the project and run `mvn clean install`to install dependencies. By default server is listening on port `:8080`
4. Run with `mvn spring-boot:run` from the CLI or from the IDE.
5. Test using Postman (or Curl)

## Example test
Routing from ITA to SWE http://localhost:8080/ITA/SWE will return:
`"routes": [
    "ITA",
    "AUT",
    "CZE",
    "POL",
    "RUS",
    "FIN",
    "SWE"
]`
