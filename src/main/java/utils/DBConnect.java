package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

// класс для подключения к бд
public class DBConnect {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = String.format("jdbc:mysql://%s:%s/%s", ConfProperties.getProperty("DBHOST"),
                ConfProperties.getProperty("DBPORT"), Base64.decode(ConfProperties.getProperty("DBNAME")));

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                Base64.decode(ConfProperties.getProperty("DBUSER")), Base64.decode(ConfProperties.getProperty("DBPASS")));

        return dbConnection;
    }

    // получить результат запроса из бд
    public ResultSet getLetter() {
        ResultSet resSet = null;
        String query = "SELECT subject_text, context_text, email FROM lettersyandex.letter_contact " +
                " INNER JOIN  lettersyandex.contact ON recever_id = idContact INNER JOIN  lettersyandex.letter " +
                "ON idletter = letter_id  INNER JOIN lettersyandex.subjectl ON idsubject = subject_id";
        try {
            PreparedStatement stat = getDbConnection().prepareStatement(query);
            resSet = stat.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    // получить объект класса Letter из результата запроса к бд
    public Letters getDataLetter() {
        ResultSet resultSet = getLetter();
        Letters letter = new Letters("start", "start", "start@yandex.ru");
        ArrayList<Letters> arrayLetter = new ArrayList<>();
        try {
            while (resultSet.next()) {
                letter = new Letters();
                letter.setSubject(resultSet.getString(1));
                letter.setContext(resultSet.getString(2));
                letter.setReciver(resultSet.getString(3));
                arrayLetter.add(letter);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        int index = random.nextInt(arrayLetter.size());
        return arrayLetter.get(index);
    }
}
