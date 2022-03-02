# Vulnerable_Spring_Boot_Project

gradlew build

docker build --build-arg JAR_FILE=build/libs/*.jar -t tk/vuln_spring_boot_application .

docker build -t tk/vuln_spring_boot_application

docker run -p 8080:8080 tk/vuln_spring_boot_application