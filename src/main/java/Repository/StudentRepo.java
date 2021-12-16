package Repository;

import Exception.AlreadyExist;
import Model.Student;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentRepo implements ICrudRepo<Student>{

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private SQL sql;


    public boolean findOne(long id) throws SQLException, IOException {

        List<Long> idList = new ArrayList<>();
        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT idStudent FROM student");

            while (resultSet.next()) {
                long id2 = resultSet.getLong("idStudent");
                idList.add(id2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        connection.close();
        return idList.contains(id);
    }


    @Override
    public Student create(Student stud) throws IOException, AlreadyExist, SQLException {

        if(!this.findOne(stud.getIdStudent())) {

            try {
                sql = new SQL();
                connection = sql.startConnection();

                String query = "INSERT INTO student VALUES(?, ?, ?, ?)";

                String firstName = stud.getFirstName();
                String lastName = stud.getLastName();
                long id = stud.getIdStudent();
                int credits = stud.getTotalCredits();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong(1, id);
                preparedStmt.setString(2, firstName);
                preparedStmt.setString(3, lastName);
                preparedStmt.setInt(4, credits);

                preparedStmt.execute();
                connection.close();
                return stud;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            throw new AlreadyExist("Student exists");
        return null;
    }

    @Override
    public List<Student> getAll() throws IOException, SQLException {
        List<Student> list = new ArrayList<>();

        try{
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM student");

            while(resultSet.next())
            {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                long id = resultSet.getLong("idStudent");
                int credits = resultSet.getInt("credits");
                Student student = new Student(firstName,lastName,id,credits);
                list.add(student);
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
    public boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException {

        if(this.findOne(objID))
        {

            try{
                sql = new SQL();
                connection = sql.startConnection();
                statement = connection.createStatement();
                String query = "DELETE FROM student WEHERE idStudent = ?";

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
        else
            throw new IllegalAccessException();

        return false;
    }

    @Override
    public Student update(Student stud) throws IOException,SQLException {
        if(this.findOne(stud.getIdStudent())) {
            try {
                sql = new SQL();
                connection = sql.startConnection();
                String query = "UPDATE student SET firstName = ? , lastName = ?, credits =? WHERE idStudent = ?";

                String firstName = stud.getFirstName();
                String lastName = stud.getLastName();
                long id = stud.getIdStudent();
                int credits = stud.getTotalCredits();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, firstName);
                preparedStmt.setString(2, lastName);
                preparedStmt.setInt(3, credits);
                preparedStmt.setLong(4, id);

                preparedStmt.execute();
                connection.close();
                return stud;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * calculez de cate credite mai are nevoie un student
     * @param id, id-ul studentului
     * @return numarul de credite
     * @throws SQLException, daca nu se poate executa conexiunea
     * @throws IOException, daca nu s-a realizat conexiunea
     */
    public int getEnoughCredits(long id) throws SQLException, IOException {
        List<Student> list = this.getAll();
        for(Student student : list)
        {
            if(id == student.getIdStudent())
                return student.getEnoughCredits();
        }
        return 0;
    }

    /**
     * gibt alle Studenten, die bei einem Kurs teilnehmen
     * @param id des Kurses
     * @return die Liste von angemeldeten Studenten
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    public List<Long> getStudentenEnrolledToCourse(long id) throws SQLException {
        List<Long> listEnrolled = new ArrayList<>();

        try{
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM enrolled");
            while (resultSet.next()) {
                if(resultSet.getLong("idCourse") == id)
                {
                    long idStudent = resultSet.getLong("idStudent");
                    listEnrolled.add(idStudent);
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        connection.close();
        return listEnrolled;
    }


    /**
     * sortez crescator in functie de numarul de credite lista studentilor
     */
    public List<Student> sortList() throws SQLException, IOException {
        List<Student> list = this.getAll();
        list.sort(Student::compareTo);
        return list;
    }

    /**
     * aleg studentii care au 30 de credite
     * @return lista
     */
    public List<Student> filterList() throws SQLException, IOException {
        List<Student> list = this.getAll();
        return list.stream()
                .filter(student->student.getTotalCredits() == 30).toList();
    }

    /**
     * caut un student dupa id-ul lui
     * @param id, id-ul studentului
     * @return studentul respectiv
     */
    public Student getStudentFromId(long id) {

        Student student = null;
        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            String query = "SELECT * FROM student WHERE idStudent = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setLong(1, id);
            ResultSet resultSet = preparedStmt.executeQuery();

            if(resultSet.next())
            {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int credits = resultSet.getInt("credits");
                student = new Student(firstName, lastName, id, credits);
            }

            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return student;
    }
}