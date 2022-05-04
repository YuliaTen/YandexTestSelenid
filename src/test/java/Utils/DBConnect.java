package Utils;

import java.sql.*;

public class DBConnect {
    Connection dbConnection;

    public  Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + ConfProperties.getProperty("DBHOST") + ":"
                + ConfProperties.getProperty("DBPORT")+ "/" + Base64.decode(ConfProperties.getProperty("DBNAME"));

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                Base64.decode(ConfProperties.getProperty("DBUSER")), Base64.decode(ConfProperties.getProperty("DBPASS")));

        return dbConnection;
    }

    public  ResultSet getLetter(){
        ResultSet resSet = null;
        String query = "SELECT subject_text, context_text, email FROM lettersyandex.letter_contact " +
                " INNER JOIN  lettersyandex.contact ON recever_id = idContact INNER JOIN  lettersyandex.letter " +
                "ON idletter = letter_id  INNER JOIN lettersyandex.subjectl ON idsubject = subject_id";
        try {
            PreparedStatement stat = getDbConnection().prepareStatement(query);
            resSet = stat.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public  Letters getDataLetter(){
        ResultSet resultSet = getLetter();
        Letters letter = new Letters();
        try {
            while (resultSet.next()) {
                 letter.setSubject(resultSet.getString(1));
                 letter.setContext(resultSet.getString(2));
                 letter.setReciver(resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  letter;

    }
}
