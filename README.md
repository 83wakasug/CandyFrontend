# Candy Application

The Candy Application is a simple web application built with Spring Boot, designed to manage and display information about candies.

## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
    - [Accessing the Application](#accessing-the-application)
    - [Navigation](#navigation)
- [Endpoints](#endpoints)
- [JSON Format Example](#json-format-example)
- [Development Environment](#development-environment)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

Ensure you have the following installed:

- Java Development Kit (JDK)
- Maven
- Your preferred IDE (e.g., IntelliJ, Eclipse)

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/83wakasug/CandyFrontend.git
    ```

2. Navigate to the project directory:

    ```bash
    cd candy-application
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar target/candy-application.jar
    ```

## Usage

### Accessing the Application

Open your web browser and go to http://localhost:8081/candy/index.

### Navigation

- Main page: http://localhost:8081/candy/index
- Search Entry: http://localhost:8081/candy/searchEntry
- List All Entries: http://localhost:8081/candy/all
- Create an Entry: http://localhost:8081/candy/create
- Update/Delete an Entry: http://localhost:8081/candy/edit

## Endpoints

- `/candy/index`: Main page.
- `/candy/searchEntry`: Search for candy entries.
- `/candy/all`: List all candy entries.
- `/candy/create`: Create a new candy entry.
- `/candy/edit`: Update or delete existing candy entries.

## JSON Format Example

Example JSON format for a candy entry:

```json
{
  "id": null,
  "name": "Sample Candy",
  "manufacturingCompany": "ABC Sweets",
  "price": 10
}
```
## Development Environment

- Spring Boot
- Thymeleaf (for HTML templates)
- Maven