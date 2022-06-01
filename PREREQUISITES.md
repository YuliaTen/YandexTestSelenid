# Подготовка к запуску

###  Cоздать БД в mySQL 

##### Основные сущности:

* письмо (Letter);
* контакт (Contact);
* тема письма (Subject);
* письмо-контакт (Letter_Contact);

[ER-диаграмма](.\moduleUtils\src\main\resources\modelER.png)

#### Заполнить данными БД

##### Указать в файле [myConf.properties](.\moduleUtils\src\main\resources\myConf.properties)
* Логин и пароль БД в зашифрованном виде
* Название БД в зашифрованном виде

##### Шифрование производится с помощью плагина Base64
* Вызвать метод encode класса MyBase64, передать ему текст, получить его в зашфрованном виде