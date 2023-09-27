Задача:

1) Сделать REST сервис с использованием JDBC и Servlet
2) Функционал любой на выбор, минимум CRUD сервис с несколькими видами entity
3) Запрещено использовать Spring, Hibernate, Lombok
4) Можно использовать Hikari CP, Mapstruckt
5) Должны быть реализованы связи ManayToOne(OneToMany),
   ManyToMany https://en.wikibooks.org/wiki/Java_Persistence/ManyToOne, https://en.wikipedia.org/wiki/Many-to-many_(data_model), https://en.wikipedia.org/wiki/One-to-many_(data_model)
6) Связи должны быть отражены в коде(должны быть соответствующие коллекции внутри энтити)
7) Сервлет должен возвращать DTO, не возвращаем Entity, принимать также DTO
8) Должна быть правильная архитектура https://habr.com/ru/articles/269589/
9) Должен быть сервисный слой
10) Должны соблюдаться принципы ООП, Solid
11) Должны быть unit тесты, использовать Mockito и Junit, для проверки работы репозитория(DAO) с БД использовать
    testcontainers: https://testcontainers.com/, https://habr.com/ru/articles/444982/
12) Покрытие тестами должно быть больше 80%
13) Должны быть протестированы все слои приложения
14) Слой сервлетов, сервисный слой тестировать с помощью Mockito
15) БД на выбор Pestgres, MySQL
16) Ставим плагин SonarLint

### Role:

GET http://localhost:8080/role/all - получить все роли

GET http://localhost:8080/role/{roleId} - получить роль с {roleId}

POST http://localhost:8080/role - создать новую роль

{
"name": "New role name"
}

DELETE http://localhost:8080/role/{roleId} - удалить роль с {roleId}

PUT http://localhost:8080/role - изменить роль

{
"id": 1,
"name": "New role name"
}

### Phone Number:

GET http://localhost:8080/phone/all - получить все номера телефонов

GET http://localhost:8080/phone/{phoneId} - получить телефон с {phoneId}

POST http://localhost:8080/phone - сохранить в базу новый номер телефона
{
"number": "+7 888 123 1234"
}

DELETE http://localhost:8080/phone/{phoneId} - удалить телефон с {phoneId}

PUT http://localhost:8080/phone - изменить телефонный номер
{
"id": 5,
"number": "+7 777 777 1234",
"userId": 1
}

### Department:

GET http://localhost:8080/department/all - получить все номера отделы

GET http://localhost:8080/department/{departmentId} - получить отдел {departmentId}

POST http://localhost:8080/department - сохранить в базу новый отдел
{
"name": "new department"
}

DELETE http://localhost:8080/department/{departmentId} - удалить отдел {departmentId}

PUT http://localhost:8080/department - изменить наименование отдела

{
"id": 2,
"name": "Edit BackEnd разработка"
}

DELETE http://localhost:8080/department/{departmentId}/deleteUser/{userId} - удалить пользователя из отдела

PUT http://localhost:8080/department/{departmentId}/addUser/{userId} - добавить пользователя в отдел

### User:

GET http://localhost:8080/user/all - получить всех пользователей

GET http://localhost:8080/user/{userId} - получить пользователя {userId}

DELETE http://localhost:8080/user/{userId} - удалить пользователя {userId}

POST http://localhost:8080/user - добавить в базу нового пользователя

{
"firstName": "New firstName",
"lastName": "New LastName",
"role": {
"id": 4,
"name": "Администратор"
}
}
PUT http://localhost:8080/user - изменить пользователя

{
"id": 1,
"firstName": "Иван Edit2333344",
"lastName": "Субботин Edit3",
"role": {
"id": 2
},
"phoneNumberList": [
{
"id": 1,
"number": "+1(123)123 1111"
},
{
"id": 2,
"number": "+1(123)123 2222"
},
{
"id": null,
"number": "+1(555)123 2222"
}
],
"departmentList": [

    {
      "id": 2
    }

]
}

