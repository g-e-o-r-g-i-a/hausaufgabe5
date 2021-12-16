package Repository;
import Model.Enrolled;
import Exception.EmptyArray;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrolledRepo {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private SQL sql;


    public boolean existStudent(Long id) throws SQLException {

        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT idStudent FROM student");

            while (resultSet.next()) {
                long idStudent = resultSet.getLong("idStudent");
                if (idStudent == id) {
                    connection.close();
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return false;
    }


    public boolean existCourse(Long id) throws SQLException{

        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT idCourse FROM course");

            while (resultSet.next()) {
                long idCourse = resultSet.getLong("idCourse");
                if (idCourse == id) {
                    connection.close();
                    return true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return false;

    }


    public Enrolled create(Enrolled obj) throws IOException, SQLException {

        if (this.existCourse(obj.getIdCourse()) && this.existStudent(obj.getIdStudent())) {
            if(!this.findOne(obj.getIdStudent(),obj.getIdCourse()))
            {
                try {
                    sql = new SQL();
                    connection = sql.startConnection();
                    String query = "INSERT INTO enrolled VALUES(?, ?, ?)";

                    long id = Enrolled.id;
                    long idStudent = obj.getIdStudent();
                    long idCourse = obj.getIdCourse();

                    PreparedStatement preparedStmt = connection.prepareStatement(query);

                    preparedStmt.setLong(1, id);
                    preparedStmt.setLong(2, idStudent);
                    preparedStmt.setLong(3, idCourse);

                    preparedStmt.execute();
                    connection.close();
                    return obj;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }


    public List<Enrolled> getAll() throws IOException, SQLException, EmptyArray {

        List<Enrolled> list = new ArrayList<>();

        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM enrolled");


            while (resultSet.next()) {
                long idStudent = resultSet.getLong("idStudent");
                long idCourse = resultSet.getLong("idCourse");
                Enrolled enrolled = new Enrolled(idStudent, idCourse);
                list.add(enrolled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connection.close();

        if (list.size() == 0)
            throw new EmptyArray("The enrollment list is empty");
        return list;
    }


    public boolean findOne(long idStudent, long idCourse) throws SQLException, IOException {

        boolean found = false;
        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM enrolled");

            List<Enrolled> list = new ArrayList<>();
            while (resultSet.next()) {
                long idStudent2 = resultSet.getLong("idStudent");
                long idCourse2 = resultSet.getLong("idCourse");
                Enrolled enrolled = new Enrolled(idStudent2, idCourse2);
                list.add(enrolled);
            }

            for (Enrolled enrollment : list) {
                if (enrollment.getIdStudent() == idStudent && enrollment.getIdCourse() == idCourse) {
                    found = true;
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return found;
    }


    public void deleteEnrolledAfterCourse(long idCourse) throws SQLException {

        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM enrolled WHERE idCourse = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setLong(1, idCourse);

            preparedStmt.execute();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        connection.close();

    }

}