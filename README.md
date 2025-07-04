# Backup Management System

A web-based application for managing and tracking database backup records, built with ReactJS on the frontend and Java Spring Boot on the backend. This system allows users to create, view, update, delete, search, filter, and export backup history, providing a reliable solution for database maintenance and audit.

---

## Table of Contents

1. [Features](#features)
2. [Tech Stack](#tech-stack)
3. [Prerequisites](#prerequisites)
4. [Installation](#installation)

   * [Backend](#backend)
   * [Frontend](#frontend)
   * [Database Configuration](#database-configuration)
5. [Usage](#usage)

   * [API Endpoints](#api-endpoints)
   * [Export to Excel](#export-to-excel)
6. [Testing](#testing)

   * [Postman](#postman)
   * [cURL](#curl)
   * [Swagger UI](#swagger-ui)
7. [Directory Structure](#directory-structure)

---

## Features

* **Backup Record Management**: Create, read, update, delete backup entries.
* **Detailed Metadata**: Track timestamp, operator, backup path, and notes.
* **Search & Filter**: Find records by date, user, or keywords.
* **Export History**: Download backup history as Excel file.
* **RESTful API**: Clean API for integration with external tools.
* **User-Friendly UI**: Responsive React dashboard with form validation and alerts.

## Tech Stack

* **Frontend**: ReactJS, JavaScript, Axios, React Router, Ant Desgin
* **Backend**: Java 17+, Spring Boot, Spring Data JPA, Swagger UI
* **Database**: Microsoft SQL Server
* **Libraries**: Apache POI (Excel export), Lombok, Bean Validation

## Prerequisites

* Java 17 or newer
* Maven 3.6+
* Node.js 18+ and npm or yarn
* Microsoft SQL Server instance
* Git

## Installation

### Backend

1. Clone the repository:

   ```bash
   git clone https://github.com/HoangKhang5207/Lab-DataBackupManagement.git
   cd data-backup-management
   ```
2. Build and run:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. Default backend URL: `http://localhost:8080`

### Frontend

1. Clone the repository:

   ```bash
   git clone https://github.com/HoangKhang5207/Lab-DataBackupManagement.git
   cd backup-management-frontend
   ```
2. Install dependencies and start:

   ```bash
   npm install
   npm run dev
   ```
3. Default frontend URL: `http://localhost:5173`

### Database Configuration

In `application.properties` (backend):

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=<DB_NAME>;encrypt=true;trustServerCertificate=true
spring.datasource.username=<DB_USER>
spring.datasource.password=<DB_PASSWORD>
```

## Usage

### API Endpoints

| Method | Endpoint              | Description                      |
| ------ | --------------------- | -------------------------------- |
| GET    | `/api/backups`        | List all backups                 |
| GET    | `/api/backups/{id}`   | Get details of a backup          |
| POST   | `/api/backups`        | Create a new backup record       |
| PUT    | `/api/backups/{id}`   | Update an existing backup record |
| DELETE | `/api/backups/{id}`   | Delete a backup record           |
| GET    | `/api/backups/export` | Export all records to Excel      |

#### Example Request/Response

* **List Backups**

  ```bash
  GET http://localhost:8080/api/backups
  Accept: application/json
  ```

  **Response** (200 OK):

  ```json
  [
    {
      "id": 1,
      "backupTime": "2025-07-04T10:15:02",
      "fullName": "Khang Nguyễn",
      "backupPath": "D:\\SQLServer\\backup_data\\v1.bak",
      "note": "Full backup v1"
    },
    ...
  ]
  ```

* **Create Backup**

  ```bash
  POST http://localhost:8080/api/backups
  Content-Type: application/json

  {
    "backupTime": "2025-07-04T11:45:20",
    "userId": 1,
    "backupPath": "D:\\SQLServer\\backup_data\\v5.bak",
    "note": "Full backup v5"
  }
  ```

  **Response** (201 Created):

  ```json
  {
    "id": 8,
    "backupTime": "2025-07-04T11:45:20",
    "fullName": "Khang Nguyễn",
    "backupPath": "D:\\SQLServer\\backup_data\\v5.bak",
    "note": "Full backup v5"
  }
  ```

* **Export to Excel**

  ```bash
  GET http://localhost:8080/api/backups/export
  Accept: application/octet-stream
  ```

  **Response** (200 OK): Binary Excel file with `Content-Disposition: attachment; filename="backup_history_<YYYYMMDD>.xlsx"`

## Export to Excel

On the frontend dashboard, click the **Export Excel** button. The system will download an `.xlsx` file (`backup_history_<YYYYMMDD>.xlsx`) containing all backup records with UTF-8 support for Vietnamese.

## Testing

### Postman

1. Import or create requests matching the API endpoints.
2. Set correct headers and JSON bodies.
3. Send and verify responses.

### cURL

* List backups:

  ```bash
  curl -X GET \
    "http://localhost:8080/api/backups" \
    -H "Accept: application/json"
  ```
* Create backup:

  ```bash
  curl -X POST \
    "http://localhost:8080/api/backups" \
    -H "Content-Type: application/json" \
    -d '{"backupTime":"2025-07-04T14:45:20","userId":5,"backupPath":"D:\\SQLServer\\backup_data\\v3.bak","note":"Backup v3"}'
  ```

### Swagger UI

Navigate to `http://localhost:8080/swagger-ui/`, select desired endpoint, click **Try it out**, enter parameters, and execute.

## Directory Structure

```
Lab-DataBackupManagement/
├─ data-backup-management/                  # Spring Boot application
│  ├─ src/main/java          # Java source code
│  ├─ src/main/resources     # application.properties, Swagger config
│  └─ target/                # Compiled artifacts
├─ backup-management-frontend/                 # ReactJS application
│  ├─ src/                   # Components, services
│  └─ public/                # Static assets
├
└─ README.md                 # Project documentation
```

---

*Last updated: July 4, 2025*
