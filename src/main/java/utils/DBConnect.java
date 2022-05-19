package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

// класс для подключения к бд
public class DBConnect {


    public Connection getDbConnection() throws SQLException {
        String connectionString = String.format("jdbc:mysql://%s:%s/%s", ConfProperties.getProperty("DBHOST"),
                ConfProperties.getProperty("DBPORT"), MyBase64.decode(ConfProperties.getProperty("DBNAME")));

        Connection dbConnection = DriverManager.getConnection(connectionString,
                MyBase64.decode(ConfProperties.getProperty("DBUSER")), MyBase64.decode(ConfProperties.getProperty("DBPASS")));

        return dbConnection;
    }

    // получить результат запроса из бд
    private ResultSet getLetter() {
        ResultSet resSet = null;
        String query = "SELECT subject_text, context_text, email FROM lettersyandex.letter_contact " +
                " INNER JOIN  lettersyandex.contact ON recever_id = idContact INNER JOIN  lettersyandex.letter " +
                "ON idletter = letter_id  INNER JOIN lettersyandex.subjectl ON idsubject = subject_id";
        try {
            resSet = getDbConnection().prepareStatement(query).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    // получить объект класса Letter из результата запроса к бд
    public Letters getDataLetter() {
        ArrayList<Letters> arrayLetter = new ArrayList<>();
        try(ResultSet resultSet = getLetter()) {
            while (resultSet!=null && resultSet.next()) {
                Letters letter = new Letters();
                letter.setSubject(resultSet.getString("subject_text"));
                letter.setContext(resultSet.getString("context_text"));
                letter.setReciver(resultSet.getString("email"));
                arrayLetter.add(letter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (arrayLetter.isEmpty()) {
            Letters letter = new Letters("start", "start", "start@yandex.ru");
            arrayLetter.add(letter);
        }
        Random random = new Random();
        int index = random.nextInt(arrayLetter.size());
        return arrayLetter.get(index);
    }
}
