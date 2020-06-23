# Customer Relationship Management

### This is project written by Spring boot + MySQL

Step to run:

1. Create your own MySQL database with name: `customer-relationship-management`

2. Open project by your favorite IDE and waiting for Maven download some dependence.

3. In the source code, go to file `application.properties` and modified your MySQL connection string, username, password.

```java
spring.datasource.url = jdbc:mysql://localhost:3306/customer-relationship-management?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username = {Your username of MySql here} 
spring.datasource.password = {Your password of MySql here} 
```

4. Run the project.

5. In the root folder open scrip file `initSQL.sql` in MySQL, to generate some init data.

6. Open browser with url: http://localhost:8080/swagger-ui.html

7. Create your account nested in `auth-controller` and login nested in this controller to receive the token

8. In the top left click button `Authorize` and input the token with format: `Bearer {Your token}`

9. Enjoy my app :)

- NOTE: After the first time run the project, the table had been generate, go to file `application.properties` and change the `spring.jpa.hibernate.ddl-auto = create` to `spring.jpa.hibernate.ddl-auto = none`.

- For more information contact me: cuongnguyen190899@gmail.com
