# bike-finder
A web app to compare bike products

## Tech stack
### Frontend
+ [ES6](http://es6-features.org/#Constants)
+ [React](https://reactjs.org/)
+ [Redux](https://es.redux.js.org/)
+ [React-Bootstrap](https://react-bootstrap.github.io/)
+ [Chart.js](https://github.com/jerairrest/react-chartjs-2) (react-charts.js)

### Backend
+ [Java 8](https://docs.oracle.com/javase/8/docs/api/)
+ [SpringBoot](http://spring.io/projects/spring-boot)
+ [Hibernate](http://hibernate.org/)
+ [Maven](https://maven.apache.org/)
+ [jsoup](https://jsoup.org/)
+ [Swagger](https://swagger.io/)

### Platform
+ [Heroku - Application Container](https://www.heroku.com/)
+ [Heroku - Database](https://www.heroku.com/postgres)

## Getting started

### Local
+ Setup. Add the following environment variables.
  + DB_URL: Pointing to the database
  + DB_USER: User to access the database
  + DB_PASSWORD: Password to access the database
  
+ Build. The following options are available to build the project.
  + Generate the jar file with the UI (will run the default ***ui*** profile)
    ```
    $ mvn clean package
    ```
  + Generates app containing only the backend. Handy when you want to test REST services with Postman, so you can skip the ***UI*** generation.
    ```
    $ mvn clean package -P backend
    ```
+ Run. You can run the app once the jar is generated in the build or using ***spring-boot***.
   + Running a generated jar file.
     ```
     $ java -jar target/bike-finder-0.0.1-SNAPSHOT.jar.jar
     ```
   + Running with ***spring-boot***. You also can specify the profile as shown above.
     ```
     $ mvn clean spring-boot:run
     ```
      
### Heroku
+ Setup. Add the following environment variables.
  + DB_URL: Pointing to the database
  + DB_USER: User to access the database
  + DB_PASSWORD: Password to access the database
         

