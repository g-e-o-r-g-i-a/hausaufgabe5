package Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class SQL {
    String url;
    String user;
    String password;
    private Connection connection;
    private Statement statement;

    public SQL() {
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Connection startConnection() throws IOException, SQLException {

        FileInputStream input = new FileInputStream("C:\\This PC\\Documents\\map5\\target\\config.properties");
        Properties prop = new Properties();
        prop.load(input);
        url = prop.getProperty("db.url");
        user = prop.getProperty("db.user");
        password = prop.getProperty("db.password");
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

}

