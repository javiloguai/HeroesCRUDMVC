# HeroesTest Springboot Complete CRUD MVC

Technical Test Spring Boot. 
Standard CRUD API in MVC Pattern 

This application implemented using: 
#### Spring boot 3 
#### spring security
#### java Open JDK 21
#### maven
#### JWT token
#### Swagger open API
#### Mockito
#### Junit
#### Integration Test
#### JPA
#### H2 database
#### lombok
#### mapstruct
#### spring AOP
#### cache
#### liquibase
#### TDD

## Java version
> java version used to compile is Temurin JDK 21+35

## IDE
> IntelliJ IDEA 2023.1.2 (Community Edition)

## To start API on a docker container
> >run mvn clean install
> 
> >run docker build -t superheroes_api .        
> 
> >docker-compose up --build 

#### Testing
> JUnit tests are performed on 'mvn clean install'

> Integration Test should be performed on 'mvn clean install' or 'mvn verify' but I am just able to execute it via IDE menú context. PATH to Integration Test is in 'heroesTest.src.test.java.com.w2m.heroestest.integrationtests'

 

## To access H2 DB console
>  URL : http://localhost:8080/h2-ui/
> >user is sa
> 
> >password is sa
> 
> 
> default data and users are loaded on startup. Default users are:
> > user: admin
>>
> > password :admin
>>
> > role :ADMIN
>
> > user: user
>>
> > password :user
>>
> > role :USER
> 
>> Due to a lack of time ROLE is not used.  

## Swagger api
> URL : http://localhost:8080/swagger-ui/index.html

> URL : http://localhost:8080/v3/api-docs

#### Gets All the superheroes
> GET : http://localhost:8080/api/superheroes

#### Page All the superheroes
> GET : http://localhost:8080/api/superheroes/page

#### Gets All the superheroes by name
> GET : http://localhost:8080/api/superheroes/byName

#### Page All the superheroes by name
> GET : http://localhost:8080/api/superheroes/byName/page

#### Gate All the superheroes by a particular Power
> GET : http://localhost:8080/api/superheroes/byPower

#### Get superheroe by id
> GET : http://localhost:8180/api/superheroes/1

#### Create new superheroe
> POST : http://localhost:8080/api/superheroes
```
{
"name": "string",
"description": "string",
"superPower": [
"INVISIBILITY","FLY"
]
}
```
#### Update an existing superheroe
> PUT : http://localhost:8080/api/superheroes/1
```
{
"name": "string",
"description": "string",
"superPower": [
"INVISIBILITY","FLY"
]
}
```
#### Adds a superpower to an existing hero by providing its id
> PATCH : http://localhost:8080/api/superheroes/1?power=INVISIBILITY
```
Available values : INVISIBILITY, PHASING, MIND_CONTROL, SHAPE_SHIFTING, TIME_TRAVEL, MOLECULAR_COMBUSTION, SUPER_STRENGHT, FIRE_CONTROL, ELECTROCUTION, EXTRAORDINARY_INTELLIGENCE, FORCE_FIELDS, TELEKINESIS, BIOKINESIS, X_RAY_VISION, PRECOGNITION, SUPER_HEALING, SELF_REGENERATION, FLY
```

#### Deletes Hero by id
> DELETE : http://localhost:8080/api/superheroes/1

#### Deletes All Heroes
> DELETE : http://localhost:8080/api/superheroes



## Authentication

#### Register new user
> POST : http://localhost:8180/auth/register

```
{
   "user":"javier",
   "pasword":"javier",
   "role":"USER or ADMIN"
}
```

#### Login user
> POST : http://localhost:8180/auth/login

```
{
   "user":"javier",
   "pasword":"javier"
}
```


