[last updated: 2020-03-24]
# NHL Main WebApp
## 1. Developer Setup
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

## 2. How to Just Run
Currently in development.

## 3. HTTP APIs

Code should be working for Forums and Posts. Documentation coming soon.

<!-- HTTP: /GET /forum 
parameters: 
example usage:
curl localhost:8080/forum

returns:
- returns JSON with all forums



    HTTP: /POST /forum/create
    curl -v -X POST localhost:8080/post/delete -H 'Content-Type:application/json' -d '{"id": "5e77b5c8e3d58bd5c347a3e9", "author_id" : "0b483c12-294b-42a3-8431-9119ea9e2e74"}'
### I. Post -->


