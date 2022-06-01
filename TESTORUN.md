# Запуск тестов

#### Перед запуском тестов ввести команду
* mvn install -Dmaven.test.skip.exec

#### Для запуска тестов UI ввести maven команду
* mvn -pl moduleUi test -Dtest=YandexMailTest

#### Для запуска тестов API ввести maven команду
* mvn -pl moduleUi test -Dtest=YandexApiTest