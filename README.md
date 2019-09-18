## Shopping Cart API with Spring Boot

REST API that allows the following scenarios:
* View a product catalog
* View product details
* Add a product to cart
* Remove a product from the cart
* View cart contents

## Run the Project

- with Maven
    
    ```
    mvn spring-boot:run
    ```

- with Docker
    - Build
        ```
        mvn clean package
        ```
        ```
        docker build -t shopping-cart .
        ```
    
    - Run
        ```
        docker run -p 8080:8080 shopping-cart
        ```

### API Documentation

* [Swagger UI](http://localhost:8080/cart/v1/swagger-ui.html)