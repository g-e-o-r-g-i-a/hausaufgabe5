package Repository;
import Exception.AlreadyExist;
import Model.Teacher;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepo implements ICrudRepo<Teacher>{

    private Connection connection;
    private Statement statement;
    private SQL sql;


    public boolean findOne(long id) throws SQLException, IOException {

        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT idTeacher FROM teacher");

            List<Long> idList = new ArrayList<>();
            while (resultSet.next()) {
                long id2 = resultSet.getLong("idTeacher");
                idList.add(id2);
            }

            connection.close();
            return idList.contains(id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Teacher create(Teacher teacher) throws IOException, AlreadyExist, SQLException {

        if(!this.findOne(teacher.getIdTeacher()))
        {

            try{
                sql = new SQL();
                connection = sql.startConnection();
                String query = "INSERT INTO teacher VALUES(?, ?, ?)";

                String tFirstName = teacher.getFirstName();
                String tLastName = teacher.getLastName();
                long id = teacher.getIdTeacher();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong   (1, id);
                preparedStmt.setString (2, tFirstName);
                preparedStmt.setString (3, tLastName);

                preparedStmt.execute();
                connection.close();
                return teacher;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
            throw new AlreadyExist("Teacher exists");

        return null;
    }

    @Override
    public List<Teacher> getAll() throws SQLException, IOException {

        List<Teacher> list = new ArrayList<>();
        try{
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM teacher");

            while(resultSet.next())
            {
                String fisrtName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                long id = resultSet.getLong("idTeacher");
                Teacher teacher = new Teacher(fisrtName, lastName, id);
                list.add(teacher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        if(list.size()>0)
            return list;
        return null;
    }

    @Override
    public Teacher update(Teacher teacher) throws IOException, SQLException {
        if(this.findOne(teacher.getIdTeacher())) {

            try {
                sql = new SQL();
                connection = sql.startConnection();
                String query = "UPDATE teacher SET firstName = ? , lastName = ? WHERE idTeacher = ?";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, teacher.getFirstName());
                preparedStmt.setString(2, teacher.getLastName());
                preparedStmt.setLong(3, teacher.getIdTeacher());

                preparedStmt.execute();
                connection.close();
                return teacher;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long objID) throws  IOException, SQLException {

        if(this.findOne(objID))
        {
            try{
                sql = new SQL();
                connection = sql.startConnection();
                String query = "DELETE FROM teacher WEHERE idTeacher = ?";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong  (1, objID);
                preparedStmt.execute();
                connection.close();
                return true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }


}