# Fujitsu Java Programming Trial Task 2024

In this repository you can find my solution for creating a sub-functionality of the food delivery application.

Business rules for different fees are stored in database and CRUD operations can be performed to manage those fees (base and extra fees). Here is the diagram of the database:

![](/assets/database_diagram.png)

## How to start the application

H2 database with initial data is located in <code>database</code> directory. All the data is based on the business logic that was described in the exercise.

To start the application, run <code>DeliveryApplication.java</code> in your IDE. By default, app is running on port 8080.

---

## API documentation

***OpenAPI documentation can be found from <code>localhost:8080/swagger-ui/index.html</code> and by typing <code>v3/api-docs</code> into the search box (application must be started).***

### DeliveryFeeController

<code>GET /api/delivery/fee/city/{city}/vehicle/{vehicleId}</code> - Calculates the delivery fee based on city and vehicle and returns a DTO object that contains of city, vehicle, base fee, extra fee and total fee. Parameters are ID-s of city and vehicle which both are required. Optional parameter is dateTime (String) that is used for doing the calculations based on the weather on this time.

### WeatherController

<code>POST /api/weather</code> - Updates weather data in the database by requesting it from Estonian Environmental Agency ("ilmateenistus.ee").

### BaseFeeController

<code>POST /api/delivery/base_fee</code> - Creates a new base fee rule and puts it into database. Takes in parameters: cityId, vehicleId and fee.

<code>GET /api/delivery/base_fee</code> - Returns all base fees that exist currently.

<code>PUT /api/delivery/base_fee/{id}</code> - Updates the fee of the given base fee entry (takes base fee ID as parameter).

<code>DELETE /api/delivery/base_fee/{id}</code> - Deletes the given base fee entry (takes base fee ID as parameter).

### ExtraFeeController

<code>POST /api/delivery/extra_fee/air_temperature</code> - Creates a new extra fee rule for air temperature.

<code>GET /api/delivery/extra_fee/air_temperature</code> - Returns all extra fee rules for air temperature.

<code>PUT /api/delivery/extra_fee/air_temperature/{id}</code> - Updates an existing air temperature extra fee rule entry in the database (takes extra fee rule ID as parameter).

<code>DELETE /api/delivery/extra_fee/air_temperature/{id}</code> - Deletes an existing air temperature extra fee rule from the database (takes extra fee rule ID as parameter).


<code>POST /api/delivery/extra_fee/wind_speed</code> - Creates a new extra fee rule for wind speed.

<code>GET /api/delivery/extra_fee/wind_speed</code> - Returns all extra fee rules for wind speed.

<code>PUT /api/delivery/extra_fee/wind_speed/{id}</code> - Updates an existing wind speed extra fee rule entry in the database (takes extra fee rule ID as parameter).

<code>DELETE /api/delivery/extra_fee/wind_speed/{id}</code> - Deletes an existing wind speed extra fee rule from the database (takes extra fee rule ID as parameter).


<code>POST /api/delivery/extra_fee/phenomenon</code> - Creates a new extra fee rule for weather phenomenon.

<code>POST /api/delivery/extra_fee/phenomenon</code> - Returns all extra fee rules for weather phenomenon.

<code>PUT /api/delivery/extra_fee/phenomenon/{id}</code> - Updates an existing weather phenomenon extra fee rule entry in the database (takes extra fee rule ID as parameter).

<code>DELETE /api/delivery/extra_fee/phenomenon/{id}</code> - Deletes an existing weather phenomenon extra fee rule from the database (takes extra fee rule ID as parameter). 