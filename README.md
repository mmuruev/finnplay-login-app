# LoginService

Test task for login service

## Development


To build the final jar the  application, run:

```
./gradlew bootRun
```

For create docker image run. Maybe symlink will not work properly for your OS. In this case copy docker file in root dir as quick fix.

``` 
docker build --build-arg JAR_FILE=build/libs/\*.jar -t finnplaylogin .
```


Docker compose run

```
docker-compose

```

For registration use [http://localhost:8080/registration](http://localhost:8080/registration)

## Known issues

- docker-compose need proper image upload
