Introduction:
The Bank Transfer Application is a simple Java-based web application that simulates money transfer operations between two bank accounts. It supports features such as deposit, transfer, transaction fee calculation, commission calculation, transaction analysis, and generating daily transaction summaries.

Features:
Deposit money into an account
Transfer money between accounts
Calculate and store transaction fees and commissions
Analyze transactions to mark them as commission-worthy
Generate and retrieve daily transaction summaries
Scheduled jobs for daily transaction analysis and summary generation

Technologies Used:
Java
Spring Boot
H2 Database
Lombok
Mockito (for testing)
Setup and Installation


Bank Transfer Application
Table of Contents
Introduction
Features
Technologies Used
Setup and Installation
Usage
API Endpoints
Testing
Scheduled Jobs
Project Structure
Contributing
License
Introduction
The Bank Transfer Application is a simple Java-based web application that simulates money transfer operations between two bank accounts. It supports features such as deposit, transfer, transaction fee calculation, commission calculation, transaction analysis, and generating daily transaction summaries.

Features
Deposit money into an account
Transfer money between accounts
Calculate and store transaction fees and commissions
Analyze transactions to mark them as commission-worthy
Generate and retrieve daily transaction summaries
Scheduled jobs for daily transaction analysis and summary generation
Technologies Used
Java
Spring Boot
H2 Database
Lombok
Mockito (for testing)


Setup and Installation
Clone the Repository:
git clone https://github.com/yourusername/bank-transfer-application.git
cd bank-transfer-application


Build the Application:
mvn clean install

Run the Application:
mvn spring-boot:run
Access the Application:
The application will be available at http://localhost:8080.

Usage
Initial Data Setup
Insert initial account data into the H2 database:
INSERT INTO account (id, account_number, balance) VALUES (1, '123456', 1000.00);
INSERT INTO account (id, account_number, balance) VALUES (2, '654321', 1500.00);

Access H2 Console
You can access the H2 console at http://localhost:8080/h2-console with the following settings:

JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password: password

API Endpoints:
Deposit Money
URL: POST /api/accounts/{accountId}/deposit
Request Params: amount (required)
Example Request:
 POST "http://localhost:8080/api/accounts/1/deposit?amount=500"

Transfer Money
URL: POST /api/accounts/transfer
Request Body:
json
Copy code
{
    "fromAccountId": 1,
    "toAccountId": 2,
    "amount": 200
}
Example Request:
POST "Content-Type: application/json" -d '{"fromAccountId": 1, "toAccountId": 2, "amount": 200}' "http://localhost:8080/api/accounts/transfer"

Get Transactions
URL: GET /api/transactions
Request Params: status, accountNumber, dateRange (optional)
Example Request:
 GET "http://localhost:8080/api/transactions?status=SUCCESSFUL"

Generate Transaction Summary
URL: POST /api/summary/generate
Example Request:
POST "http://localhost:8080/api/summary/generate?date=2024-07-03"

Run Transaction Analysis
URL: POST /api/analysis/run
Example Request:
POST "http://localhost:8080/api/analysis/run"

Testing
The application includes unit tests to ensure proper functionality. To run the tests, use the following command:
mvn test

Scheduled Jobs
Transaction Analysis
Cron Expression: @Scheduled(cron = "0 * * * * ?")
Description: This job runs every minute and analyzes transactions to update the successful ones as commission-worthy with the corresponding commission value.

Transaction Summary
Cron Expression: @Scheduled(cron = "0 0 0 * * ?")
Description: This job runs at midnight every day to generate and store the summary of the transactions for the specified day.

Project Structure
src/
├── main/
│   ├── java/com/example/banktransferapplication/
│   │   ├── controller/        # REST Controllers
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── exception/         # Custom Exceptions
│   │   ├── model/             # Entities
│   │   ├── repository/        # Repositories
│   │   ├── service/           # Services
│   │   ├── BankTransferApplication.java # Main Application Class
├── test/                      # Unit Tests
│   ├── java/com/example/banktransferapplication/

Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.


