<h2> Дипломный проект по профессии «Тестировщик» </h2>
Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

<h3> Процедура запуска автотестов </h3>

1. Склонировать репозиторий: git clone https://github.com/yanabialkova/DiplomaProject.git
2. Перейти в папку DiplomaProject
3. Запустить контейнер Docker командой в консоли: docker-compose up

<h3> Запустить сервис с указанием пути к базе данных </h3>

* для mysql

java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" "-Dspring.datasource.username=app" "-Dspring.datasource.password=pass" -jar artifacts/aqa-shop.jar

* для postgresql

java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" "-Dspring.datasource.username=app" "-Dspring.datasource.password=pass" -jar artifacts/aqa-shop.jar

<h3> Запустить авто-тесты команой в консоли </h3>

* для mysql

./gradlew test "-Ddb.url=jdbc:mysql://localhost:3306/app" "-Ddb.username=app" "-Ddb.password=pass"

* для postgresql

./gradlew test "-Ddb.url=jdbc:postgresql://localhost:5432/app" "-Ddb.username=app" "-Ddb.password=pass"

<h3> Процедура остановки автотестов </h3> 

 После выполнения всех тестов остановить docker контейнер командой в консоли: docker-compose down
 
