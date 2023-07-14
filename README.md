# Vulnerable_Spring_Boot_Project

This Spring Boot Application was part of my Bachelor Thesis.

In this project I included tones of vulnerabilities

```
gradlew build
```


Docker Command to build an image:
```
docker build --build-arg JAR_FILE=build/libs/*.jar -t tk/vuln_spring_boot_application .
```
Docker Command to build a image without build arguments:
```
docker build -t tk/vuln_spring_boot_application
```

```
docker run -p 8080:8080 tk/vuln_spring_boot_application
```
