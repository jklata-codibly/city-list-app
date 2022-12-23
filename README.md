## HOW TO RUN THE APP

### Run on Docker
If You want to run whole app on Docker, all You need to do is run `docker-compose up` in main directory (location of docker-compose.yml file)

You can also change properties in config file -> `.env`. After changes You can use `docker-compose up --build` to rebuild images.

---
## API DOCUMENTATION

Swagger URL: `/api/v1/api-docs`

## .ENV PROPERTIES

* `APP_PORT` - port used by app inside container (default 8080)
* `CONTAINER_PORT` - port outside container (default 8080)
* `PROFILE` - used Spring profiles, (default `csvImport` which means - app will populate DB with given CSV)
* `MONGODB_URI` - URI od MongoDB
* `CSV_PATH` - path to CSV file (default `src/main/resources/cities.csv`)
* `DROP_BEFORE_CSV_IMPORT` - flag (default true) which enables deleting all records before populating with CSV (without enabled `csvImport` profile this flag has no impact on app)