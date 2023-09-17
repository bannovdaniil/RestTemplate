Задача:
1)	Сделать REST сервис с использованием JDBC и Servlet
2)	Функционал любой на выбор, минимум CRUD сервис с несколькими видами entity
3)	Запрещено использовать Spring, Hibernate, Lombok
4)	Можно использовать Hikari CP, Mapstruckt
5)	Должны быть реализованы связи ManayToOne(OneToMany), ManyToMany https://en.wikibooks.org/wiki/Java_Persistence/ManyToOne, https://en.wikipedia.org/wiki/Many-to-many_(data_model), https://en.wikipedia.org/wiki/One-to-many_(data_model)
6)	Связи должны быть отражены в коде(должны быть соответствующие коллекции внутри энтити)
7)	Сервлет должен возвращать DTO, не возвращаем Entity, принимать также DTO
8)	Должна быть правильная архитектура https://habr.com/ru/articles/269589/
9)	Должен быть сервисный слой
10)	Должны соблюдаться принципы ООП, Solid
11)	Должны быть unit тесты, использовать Mockito и Junit, для проверки работы репозитория(DAO) с БД использовать testcontainers: https://testcontainers.com/, https://habr.com/ru/articles/444982/
12)	Покрытие тестами должно быть больше 80%
13)	Должны быть протестированы все слои приложения
14)	Слой сервлетов, сервисный слой тестировать с помощью Mockito
15)	БД на выбор Pestgres, MySQL
16)	Ставим плагин SonarLint

Execution:
docker-compose down
docker-compose build
docker-compose up -d

http://localhost:8080/user
