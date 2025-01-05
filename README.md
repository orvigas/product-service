# Spring Boot 3.4.* product-service
###### Author: Orlando Villegas 2024
This tutorial is about a Spring Boot 3.4.* based microservice. This time, we are going to create a simple "CRUD" microservice that will allow us to manage a database "product" table upon a Rest Controller that will contain Create, Read, Update, and Delete endpoints. We will also add a List operation with pagination, sorting, and filtering features.
## Concept definitions
Before starting, we need to define a few concepts to have a clear background about the things that we will work on within this project.
### Microservices
A microservice is an architectural style that structures an application as a collection of small, autonomous, and loosely coupled services. Each microservice focuses on a specific business capability, operates independently, and communicates with other services through lightweight protocols such as HTTP or messaging queues. This approach enhances scalability, maintainability, and agility in software development.
### C.R.U.D.
CRUD stands for Create, Read, Update, and Delete, which are the four basic operations performed on data in a database or persistent storage.

- Create: Adding new data (e.g., inserting a new record).
- Read: Retrieving or querying existing data.
- Update: Modifying existing data.
- Delete: Removing data.

These operations form the foundation of most software systems that handle data management.
### Rest Controller
A REST Controller is a component in a web application that handles HTTP requests and provides responses in a RESTful web service. In frameworks like Spring Boot, it is typically defined using the @RestController annotation.

It simplifies development by combining the functionality of @Controller and @ResponseBody, allowing methods to return data directly as JSON or XML without the need for view templates. REST Controllers are commonly used to expose APIs for creating, reading, updating, and deleting resources.
### Services
@Service is an annotation in Spring Framework used to mark a class as a service layer component. It indicates that the class contains business logic and acts as an intermediary between the controller and repository layers in a Spring application.

By using @Service, the class is automatically detected and registered as a Spring bean, enabling dependency injection and making it easier to manage and test the application's business logic.
### Repositories
@Repository is an annotation in the Spring Framework used to indicate that a class is responsible for interacting with the database. It marks the class as a data access layer component.

Key features of @Repository:
- It enables automatic detection and registration as a Spring bean.
- It translates database-related exceptions into Spring's unified exception hierarchy, making exception handling consistent and manageable.

Classes annotated with @Repository typically include methods for CRUD operations and custom queries.

## Building an Application with Spring Boot
If you want to create your own Spring Boot-based project, visit <a href="https://start.spring.io" target="_blank">Spring Initializr</a>, fill in your project details, pick your options, and download a bundled up project as a zip file.
### Product Service Project Conf
Open the Spring Initializr and configure a new project as follows
<figure>
    <img src="/readme-assets/initializr.png"
         alt="Spring Initializr page">
    <figcaption>Spring Boot Project configuration used in this tutorial.</figcaption>
</figure>

Download and unzip the project in your computer
<figure>
    <img src="/readme-assets/unzip.png"
         alt="Unzip the project in your directory">
    <figcaption>Spring Boot Project should be unzipped and after loaded in your IDE.</figcaption>
</figure>

Load your project into your preferred IDE (I'm using <a href="https://www.jetbrains.com/es-es/idea/download/other.html">IntelliJ Idea CE</a>),
download and unzip the project in your computer.
<figure>
    <img src="/readme-assets/intellij.png"
         alt="Load the project into your IDE">
    <figcaption>After loading the project we will be ready to start or configuration process.</figcaption>
</figure>

## Project configuration
From now on we will begin to configure the starters (plug-ins) that we added when creating our project. I want to emphasize that we will not go into specific details of each plugin, but rather we will try to focus on its specific configuration for this project; I recommend that if you want to know more about all the plugins and their configurations, do a search on sites like baeldung.com, which compiles high-quality related material that can be very useful.
### H2 Database
H2 is a lightweight, open-source, in-memory relational database written in Java. It is commonly used for development, testing, and small-scale applications due to its simplicity and fast performance.
Key features of H2 include:
- Embedded and server modes: Can run as an in-memory database or as a standalone server.
- SQL support: Fully compatible with the SQL standard.
- Ease of use: Requires minimal configuration.

H2 is popular in Spring Boot applications for testing because it integrates seamlessly and resets with each application restart.

#### Datasource configuration
In your project open `/product-service/src/main/resources/application.properties` and add the following:
```properties
spring.application.name=product-service

#Spring datasource
spring.datasource.url=jdbc:h2:mem:products
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

#JPA
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.defer-datasource-initialization=true

#H2 DB
spring.h2.console.enabled=true
spring.h2.console.path=/h2-ui
```
Run your application and open a web browser, let's test the H2 console by going to `http://localhost:8080/h2-ui`, once the console login form is loaded, use your credentials defined in the `#Spring datasource` section of your `application.properties` file and test if you have access.
<figure>
    <img src="/readme-assets/h2.png"
         alt="H2 Console">
    <figcaption>After clicking the "Test Connection" button you should see a green "Test successful" message.</figcaption>
</figure>

Click connect, and you should be redirected to the H2 DB console like this.
<figure>
    <img src="/readme-assets/H2-console.png"
         alt="H2 Console">
    <figcaption>In this page you will be able to run SQL queries verify your table structure, etc.</figcaption>
</figure>

### Database Initialization
After configuring the H2 Database in our project, we should be able to load data into the database by following the <a href="https://docs.spring.io/spring-boot/how-to/data-initialization.html">documentation</a>, which says that we are able to load data into our DB on project start by setting spring.jpa.hibernate.ddl-auto to control Hibernate’s database initialization. Supported values are none, validate, update, create, and create-drop.

In addition, a file named data.sql in the root of the classpath (`src/main/resources/data.sql`) is executed on startup if Hibernate creates the schema from scratch (that is, if the ddl-auto property is set to create or create-drop).

```sql
DROP TABLE if EXISTS products CASCADE;
DROP sequence if EXISTS products_seq;
CREATE sequence products_seq start WITH 1 increment by 50;
CREATE TABLE products (price float(53), tax_rate float(53), id bigint NOT NULL, description varchar(255), name varchar(255), sku varchar(255) UNIQUE, PRIMARY KEY (id));

INSERT INTO products (id,sku,name,description,price,tax_rate)
VALUES
  (1,5223,'#ea9c9a','purus mauris a nunc. In at pede. Cras vulputate velit',34,3),
  (2,8192,'#70eace','NULLam feugiat placerat velit. Quisque varius. Nam porttitor',39,2),
  (3,5050,'#f7f25b','Fusce aliquet magna a neque. NULLam ut',103,6);
```

Run the application again, connect to the H2 DB console, you should see a new table called `products`, click on it and run the query
<figure>
    <img src="/readme-assets/h2-with-data.png"
         alt="H2 Console with data loaded from 'data.sql'">
    <figcaption>After run the query you will see a result list with the data inserted in the database.</figcaption>
</figure>

## Microservice structure
The structure of a __Spring Boot microservice__ typically follows a modular and layered architecture, focusing on scalability, maintainability, and separation of concerns. The key components include:

1. __Controller Layer:__ Handles incoming HTTP requests, routes them to the appropriate service, and returns responses (annotated with @RestController).

2. __Service Layer:__ Contains business logic and acts as an intermediary between the controller and repository layers (annotated with @Service).

3. __Repository Layer:__ Manages data persistence, providing CRUD operations and database interactions (annotated with @Repository).

4. __Model:__ Represents the application's domain data, often mapped to database tables using JPA annotations like @Entity.

5. __Configuration:__ Handles application-specific configurations, such as database connections, security, and external integrations (e.g., application.yml or application.properties).

6. __API Gateway (Optional):__ Manages routing, load balancing, and authentication for multiple microservices.

7. __External Communication:__ Utilizes REST APIs, messaging systems (like RabbitMQ or Kafka), or service discovery (like Eureka) to interact with other microservices.

Each microservice is self-contained, has its own database (in a typical design), and communicates with others via lightweight protocols like HTTP or messaging.

### Model
In Spring Boot, a model is a class that represents the application's data or domain objects. It is typically used to encapsulate data that will be processed, stored, or transferred between different layers of the application (e.g., Controller, Service, and Repository).

Key features of a Spring Boot model:

- Contains fields that represent the attributes of the data.
- May include getters, setters, constructors, and other utility methods.
- Often annotated with JPA annotations (e.g., @Entity, @Table, @Id) if it maps to a database table.

Models are central to defining the structure of data and facilitating communication within the application.
#### Product.java
We need to create our model which will contain our database table representation in java in this path `src/main/java/com/example/product/models/Product.java`.

```java
package com.example.product.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "sku", unique = true)
  private String sku;
  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;
  @Column(name = "price")
  private double price;
  @Column(name = "tax_rate")
  private double taxRate;
}
```
Now we can remove some configurations because the table creation process will be handled by the spring boot lifecycle and this configuration is no longer needed. In the `data.sql` we will comment the following:
```sql
--DROP TABLE if EXISTS products CASCADE;
--DROP sequence if EXISTS products_seq;
--CREATE sequence products_seq start WITH 1 increment by 50;
--CREATE TABLE products (price float(53), tax_rate float(53), id bigint NOT NULL, description varchar(255), name varchar(255), sku varchar(255) UNIQUE, PRIMARY KEY (id));
```
### Controller
A __Spring Boot Controller__ is a component in a Spring Boot application that handles incoming HTTP requests and defines the application's routing and request handling logic. It is typically annotated with @Controller or @RestController.

- __@Controller__: Used for traditional web applications, returning view templates like HTML.
- __@RestController__: A specialization of @Controller that combines @Controller and @ResponseBody, directly returning __JSON__ or __XML__ responses.

Controllers process user requests, invoke business logic (usually via services), and return responses to the client.

#### ProductController.java
We need to create a new file inside a new package called "controllers", this results in something like this. `src/main/java/com/example/product/controllers/ProductController.java`.
This si the first content of that file:

```java
package com.example.product.controllers;

public class ProductController {
}
```
After this point we need to add some __Spring Boot annotations__ to add the controller behavior this newly created class as follows

```java
package com.example.product.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/product")
public class ProductController {

  @GetMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  public String read() {
    return "It works!";
  }
}
```
Run your application again and test this url `http://localhost:8080/v1/product`, we should get something like this
<figure>
    <img src="/readme-assets/first-controller.png"
         alt="GET Request response">
    <figcaption>Response from the controller.</figcaption>
</figure>

If our controller was able to show us the result, we are now ready to add the missing methods to complete the structure of a CRUD + L list function.

#### Controller with the Model
Now our controller should be like this:

```java
package com.example.product.controllers;

import com.example.product.models.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/v1/product")
public class ProductController {

  @GetMapping(path = "/list")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<List<Product>> list() {
    return ResponseEntity.ok(List.of(new Product()));
  }

  @GetMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<Product> read(@PathVariable(required = true) long id) {
    return ResponseEntity.ok(new Product());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Product> create(@RequestBody(required = true) @NonNull @Valid Product product) {
    return ResponseEntity.ok(new Product());
  }

  @PutMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<Product> update(@PathVariable(required = true) long id, @RequestBody(required = true) Product product) {
    return ResponseEntity.ok(new Product());
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> delete(@PathVariable(required = true) long id) {
    return ResponseEntity.noContent().build();
  }

}

```
To reinforce the knowledge about them and add them to the endless tools that Java and Spring Boot provide us to build robust and efficient applications.
This a list of the most important ones
- Spring Web
  - `@RequestMapping`
  - `@GetMapping`
  - `@PostMapping`
  - `@PutMapping`
  - `@DeleteMapping`
  - `@ResponseStatus`
  - `@PathVariable`
- Spring Data
  - `@Entity`
  - `@Table`
  - `@Id`
  - `@Column`
  - `@GeneratedValue`
- Lombok
  - `@Data`

### Service
In an application, the business logic resides within the service layer so we use the __@Service Annotation__ to indicate that a class belongs to that layer. It is also a specialization of __@Component Annotation__ like the __@Repository Annotation__. One most important thing about the __@Service Annotation__ is it can be applied only to classes. It is used to mark the class as a service provider. So overall __@Service annotation__ is used with classes that provide some business functionalities.
#### ProductService.java
We need to create a new file inside a new package called "services", this results in something like this. `src/main/java/com/example/product/services/ProductService.java`.
Now our service should be like this:

```java
package com.example.product.services;

import com.example.product.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

  public List<Product> all() {
    return List.of(new Product());
  }

  public Product get(final long id) {
    return new Product();
  }

  public Product save(final Product product) {
    return new Product();
  }

  public Product update(final long id, final Product product) {
    return new Product();
  }

  public void delete(final long id) {

  }
}
```
Now we need to change a couple of things in our `ProductController.java` file.
```java
@RestController
@Validated
@RequestMapping(path = "/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;//We need to inject the services
  //...
}
```
We need to perform the dependency injection process using the @Autowired annotation to create an instance of our newly created service and inject it into the product controller, but since we are using the Lombok library in our project, we can get rid of the @Autowired annotation leveraging the Lombok power of complete code and its dependencies behind the scene.
```java
@Data//This annotation will create the boilerplate code for us
@Validated
@RestController
@RequestMapping(path = "/v1/product")
public class ProductController {
    
    /* Since we are marking this property as final, Lombok will try to satisfy
     * the dependency creating an instance of the services and inject it before
     * instantiate the controllers. 
     */
    private final ProductService productService;
}
```
Now our controller should look like.

```java
package com.example.product.controllers;

import com.example.product.models.Product;
import com.example.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Validated
@RestController
@RequestMapping(path = "/v1/product")
public class ProductController {

  private final ProductService productService;

  @GetMapping(path = "/list")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<List<Product>> list() {
    return ResponseEntity.ok(productService.all());
  }

  @GetMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<Product> read(@PathVariable(required = true) long id) {
    return ResponseEntity.ok(productService.get(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Product> create(@RequestBody(required = true) @NonNull @Valid Product product) {
    return ResponseEntity.ok(productService.save(productService.save(product)));
  }

  @PutMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<Product> update(@PathVariable(required = true) long id, @RequestBody(required = true) Product product) {
    return ResponseEntity.ok(productService.update(id, product));
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> delete(@PathVariable(required = true) long id) {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
```
### Repository
__Spring @Repository annotation__ is a specialization of __@Component annotation__, so Spring Repository classes are auto-detected by __spring framework__ through classpath scanning. Spring Repository is very close to __DAO__ pattern where __DAO__ classes are responsible for providing __CRUD__ operations on database tables.
#### ProductRepository.java
We need to create a new file inside a new package called "repositories", this results in something like this. `src/main/java/com/example/product/repositories/ProductRepository.java`.
Now our repository should be like this:

```java
package com.example.product.repositories;

import com.example.product.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}

```
In the same way that we did with the service and the controller, we have to link the new repository to the service by using dependency injection, and as we already know we can use Lombok for this.
Now our `ProductService.java` should look like:

```java
package com.example.product.services;

import com.example.product.models.Product;
import com.example.product.repositories.ProductRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Data
@Service
public class ProductService {

  private final ProductRepository productRepository;

  public List<Product> all() {
    return StreamSupport.stream(productRepository.findAll().spliterator(), false).toList();
  }

  public Product get(final long id) {
    return productRepository.findById(id).orElseThrow();
  }

  public Product save(final Product product) {
    return productRepository.save(product);
  }

  public Product update(final long id, final Product product) {
    return productRepository.findById(id).map(existing -> {
      product.setId(existing.getId());
      return productRepository.save(product);
    }).orElseThrow();
  }

  public void delete(final long id) {
    productRepository.deleteById(id);
  }
}

```
### Testing our service
Once we've finished, we need to start our service and test this URL `http://localhost:8080/v1/product/list`, if we did everything correctly we should see something like this:
<figure>
    <img src="/readme-assets/results.png"
         alt="Results on chrome browser">
    <figcaption>List of records from the DB</figcaption>
</figure>
We can verify the existing data in the DB console
<figure>
    <img src="/readme-assets/h2-with-data.png"
         alt="H2 Console with data loaded from 'data.sql'">
    <figcaption>After run the query you will see a result list with the data inserted in the database.</figcaption>
</figure>