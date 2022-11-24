<h2> Дипломный проект по профессии «Тестировщик» </h2>

<h3> Процедура запуска автотестов </h3>

1. Склонировать репозиторий: git clone https://github.com/yanabialkova/DiplomaProject.git
2. Перейти в папку DiplomaProject
3. Запустить контейнер Docker командой в консоли: docker-compose up

<h3> Запустить сервис с указанием пути к базе данных </h3>

* для mysql

java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

* для postgresql

java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

<h3> Запустить авто-тесты команой в консоли </h3>

* для mysql

gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"

* для postgresql

gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

<h3> Процедура остановки автотестов </h3> 

1. После выполнения всех тестов остановить docker контейнер командой в консоли: docker-compose down
 


