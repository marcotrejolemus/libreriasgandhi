# libreriasgandhi
An application connected to a relational database. Apply advanced Java and Spring concepts, such as consuming external RES APIs, data persistence with PostgreSQL
# libreriasgandhi

**Literalura** is a Spring Framework application designed to connect to a relational database, consume external APIs, and apply advanced Java and Spring concepts. The application uses PostgreSQL for data persistence and focuses on managing and querying information about books and authors.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Contributing](#contributing)
- [License](#license)

## Features

- **External API Consumption**: Fetch book data from external APIs (e.g., [Gutenberg Project API](https://gutendex.com)).
- **Data Persistence**: Store and manage data in a PostgreSQL database.
- **Advanced Querying**: Utilize Spring Data JPA for complex queries.
- **Interactive Console Menu**: A console-based interface for users to search, list, and analyze book and author data.

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Hibernate
- Maven
- REST APIs

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- PostgreSQL 12+
- An IDE with Spring Boot support (e.g., IntelliJ IDEA, Eclipse)

## Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/literalura.git
   cd literalura
