# Distributed Systems Project**
```
Overview
The purpose of this project is to gain practical experience in interprocess communication
protocols used in modern distributed systems. You’ll build a distributed user account
management system in two parts. Each part of the project will involve developing a
service which will be able to connect to other services via APIs. Each service will be
submitted separately. The two interconnected services you’ll develop are as follows:

• Part 1: gRPC Password service
• Part 2: RESTful User Account Service
```

# PasswordService
**Run the Jar file**
```
mvn compile

cd target
run java -jar PasswordService.jar
```

**PasswordServer**
```
Once the .jar file is ran, it should be listening on port 2104
```


# user-RESTfulAPI
**Run the jar file**
```
mvn package

run java -jar user-RESTfulAPI-1.0-SNAPSHOT.jar server userAPIconfig.yml
```

**Swaggerhub**
https://app.swaggerhub.com/apis/SMoran985/UserAPI/1#free


**Name,URL and EMail**
```
Name: Shane Moran
URL: https://github.com/smoran98/PasswordService
Email: G00338607@gmit.ie
```

**References**
```
https://github.com/john-french/distributed-systems-labs
https://github.com/john-french/artistAPI-dropwizard
https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/
```
