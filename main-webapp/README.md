# NHL Main WebApp

## 1. Diagram 
[Diagram Link](https://drive.google.com/file/d/1LDsYrOMU5iv1zKUV4BS5BGrOAQDP3gso/view?usp=sharing)

## 2. Developer Setup
highly recommend downloading [IntelliJ Community Edition](https://www.jetbrains.com/idea/download/#section=windows) 
- If using IntelliJ:
    1. Open Project: file > open > select ./main-webapp/nhlweb/pom.xml
    2. Download Maven Sources: On the projects tab, right click nhlweb > maven > download sources
    3. Run project (nhlwebApplication) - note: microservices should already be running
- If using bash/cmd/terminal:
    1. download maven and java: [TutorialsPoint Link](https://www.tutorialspoint.com/maven/maven_environment_setup.htm)
    2. navigate to nhl/web: cd ./main-webapp/nhlweb
    3. run `mvn clean install`
    4. run `./mvnw spring-boot:run`

## 3. HTTP APIs

[HTTP API Document](https://docs.google.com/document/d/1OP6uCUMxIhgY1QN-kXb0mfI4Wn1CGSd8GzCfjmizEWM/edit?usp=sharing)
