# Base64DiffApi

This is a WAES challenge, the purpose is to save and compare base64 data from a Rest API with 2 endpoints.

## Pre requisites

Provide 2 http endpoints that accepts JSON base64 encoded binary data on both
endpoints
```
o <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
```
• The provided data needs to be diff-ed and the results shall be available on a third end
point
```
o <host>/v1/diff/<ID>
```
• The results shall provide the following info in JSON format
* If equal return that
* If not of equal size just return that
* If of same size provide insight in where the diffs are, actual diffs are not needed.
     * So mainly offsets + length in the data

## Technologies used

* [SpringBoot](https://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [TomCat](http://tomcat.apache.org/) - Server
* [H2 Database](https://www.h2database.com/html/main.html) - DataBase

## Installing

This project requires Jdk 1.8 (or later) and Maven 3.5 (or later) to run.

```
$ cd Base64DiffApi
$ mvn package
$ java -jar target/base64DiffApi-1.0.jar 
```

## Running the tests

The tests can be run using Maven command:

```
$ cd Base64DiffApi
$ mvn test
```

## Author

* **Bruno Câmara** 


