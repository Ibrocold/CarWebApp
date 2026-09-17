# CarAuction (CarWebApp)

A REST API for managing and selling cars, built with **Spring Boot** and **PostgreSQL**. Each product (car) can store detailed information and an associated image, with full CRUD + search capabilities. Prices are stored in **Naira** (`₦`).

## Tech Stack

- **Java 17**
- **Spring Boot** 4.1.1
- **Spring Data JPA** (Hibernate)
- **Spring Web MVC**
- **PostgreSQL** (relational database)
- **Lombok** (reduces boilerplate code)

## Features

- Add a new car product with an image (image stored as binary data).
- List all car products.
- Get a single product by id.
- Update an existing product together with its image.
- Delete a product by id.
- Search products by **name**, **description**, **brand**, or **category**.
- Serve product images through a dedicated endpoint.

## Project Structure

```
src/main/java/com/Ibrocold/CarPurchase/
├── CarPurchaseApplication.java   # Spring Boot entry point
├── controller/
│   └── ProductController.java    # REST endpoints
├── service/
│   └── ProductService.java       # Business logic & image handling
├── repository/
│   └── ProductRepository.java    # JPA repository + search query
└── model/
    └── Product.java              # JPA entity (table: Product)
```

## Prerequisites

- JDK 17+
- Maven 3.x
- PostgreSQL (running locally)

## Getting Started

### 1. Configure the database

Edit `src/main/resources/application.properties` to match your PostgreSQL setup:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

`ddl-auto=update` lets Hibernate create/update the `Product` table automatically on startup.


### 2. Run the application

```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8080`.

## API Endpoints

| Method   | Endpoint                           | Description                              |
|----------|------------------------------------|------------------------------------------|
| `GET`    | `/api/`                            | Welcome message                          |
| `GET`    | `/api/products`                    | Get all products                         |
| `GET`    | `/api/products/{id}`               | Get product by id                        |
| `POST`   | `/api/products`                    | Add product (multipart form + image)     |
| `PUT`    | `/api/products/{productId}`        | Update product + image                   |
| `DELETE` | `/api/products/{productId}`        | Delete product by id                     |
| `GET`    | `/api/products/{productId}/image`  | Get product image bytes                  |
| `GET`    | `/api/products/search?value=...`   | Search by name/brand/category/description|

### Notes on POST/PUT

`POST` and `PUT` expect **multipart form data**:
- `product` → the JSON part representing the `Product` entity
- `imageFile` → the image file

Example with curl:

```bash
curl -X POST http://localhost:8080/api/products \
  -F "product={ \"name\": \"Toyota Camry\", \"description\": \"Full option\", \"brand\": \"Toyota\", \"price\": 18500000.00, \"category\": \"Sedan\", \"releaseDate\": \"15-03-2021\", \"available\": true, \"quantity\": 5 };type=application/json" \
  -F "imageFile=@/path/to/car.jpg"
```

## Product Fields

| Field         | Type      | Description                          |
|---------------|-----------|--------------------------------------|
| `id`          | Integer   | Auto-generated primary key           |
| `name`        | String    | Product/car name                     |
| `description` | String    | Short description                    |
| `brand`       | String    | Car brand (e.g. Toyota, Lexus)       |
| `price`       | BigDecimal| Price in Naira (₦)                   |
| `category`    | String    | e.g. Sedan, SUV, Pickup, Hatchback   |
| `releaseDate` | Date      | Release date (`dd-MM-yyyy`)          |
| `available`   | Boolean   | Stock availability flag              |
| `quantity`    | Integer   | Available units                      |
| `imageName`   | String    | Original uploaded file name          |
| `imageType`   | String    | Uploaded image MIME type             |
| `imageDate`   | byte[]    | Binary image data                    |

## License
No license specified.