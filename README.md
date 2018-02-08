## Prerequisites
To be able to build this project you will need Gradle to be installed on your system in version 4.2+. Alternatively you may use the Gradle wrapper that comes with this project.


## Building
To check if the project builds completely and correctly simply type 

```
gradle build
```

 in a console with the project's root folder as your current folder.
 If you want the application to be running use
 
 ```
 gradle bootRun
 ```
 
 instead. Of course you may also use an appropriate plug-in for your favourite IDE to invoke the gradle tasks.


## Running
If you're running the application in eclipse then it is strongly recommended, that you install and use the Spring Tool Suite. Using it enables you to run the Application as "Spring Boot App" and such to gracefully shutdown the application taking all shutdown hooks into consideration. Still you have to ensure that the checkbox "Enable Life Cycle Management" is checked..

To issue a sample GET request use the following URL

 ```
 http://localhost:8080/services/helloworld
 ```

## Debugging
Since the application's servlet container is embedded and it can be run as a normal java application it can be debugged just by running with a debug-run-configuration in eclipse.
