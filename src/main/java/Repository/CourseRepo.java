package Repository;
import Model.Course;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepo implements ICrudRepo<Course> {

    private Connection connection;
    private Statement statement;
    private SQL sql;


    public boolean existTeacher(Long id) {

        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT idTeacher FROM teacher");

            while (resultSet.next()) {
                long idTeacher = resultSet.getLong("idTeacher");
                if (idTeacher == id) {
                    connection.close();
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Course create(Course course) throws IOException, SQLException {

        if (this.existTeacher(course.getTeacher())) {
            if(!this.findOne(course.getId()))
            {
                try {
                    sql = new SQL();
                    connection = sql.startConnection();
                    String query = "INSERT INTO course VALUES(?, ?, ?, ?, ?)";
                    long id = course.getId();
                    String name = course.getName();
                    long teacher = course.getTeacher();
                    int max = course.getMaxEnrollment();
                    int credits = course.getCredits();

                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setLong(1, id);
                    preparedStmt.setString(2, name);
                    preparedStmt.setLong(3, teacher);
                    preparedStmt.setInt(4, max);
                    preparedStmt.setInt(5, credits);


                    preparedStmt.execute();
                    connection.close();
                    return course;


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }


    @Override
    public List<Course> getAll() throws SQLException, IOException {
        List<Course> list = new ArrayList<>();

        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course");

            while (resultSet.next()) {
                long id = resultSet.getLong("idCourse");
                String name = resultSet.getString("Name");
                long idTeacher = resultSet.getLong("idTeacher");
                int max = resultSet.getInt("maxEnrollment");
                int credits = resultSet.getInt("credits");
                Course course = new Course(id, name, idTeacher, max, credits);
                list.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        if (list.size() > 0)
            return list;
        return null;
    }

    @Override
    public Course update(Course course) throws IOException, SQLException {

        if (this.findOne(course.getId()) && this.existTeacher(course.getTeacher())) {
            try {
                sql = new SQL();
                connection = sql.startConnection();

                String query = "UPDATE course SET Name = ? , idTeacher = ?, maxEnrollment = ?, credits = ? WHERE idCourse = ?";

                long id = course.getId();
                String name = course.getName();
                long idTeacher = course.getTeacher();
                int max = course.getMaxEnrollment();
                int credits = course.getCredits();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, name);
                preparedStmt.setLong(2, idTeacher);
                preparedStmt.setInt(3, max);
                preparedStmt.setInt(4, credits);
                preparedStmt.setLong(5, id);

                preparedStmt.execute();
                connection.close();
                return course;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public boolean delete(Long objID) throws IOException, SQLException {
        if (this.findOne(objID)) {
            try {
                sql = new SQL();
                connection = sql.startConnection();
                String query = "DELETE FROM course WHERE idCourse = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong(1, objID);

                preparedStmt.execute();
                connection.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean findOne(long id) throws SQLException, IOException {

        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course");

            List<Long> list = new ArrayList<>();
            while (resultSet.next()) {
                long idCourse = resultSet.getLong("idCourse");
                list.add(idCourse);
            }
            connection.close();
            return list.contains(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * cati studenti sunt inscrisi la un curs
     * @param id, id-ul cursului
     * @return numarul de studenti respectivi
     */
    public int getNumberOfStudents(long id) {

        int nr = 0;
        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM enrolled");
            while (resultSet.next()) {
                if (resultSet.getLong("idCourse") == id)
                    nr++;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nr;
    }

    /**
     * caut cursurile cu locuri libere
     * @return lista cu cursuurile ce imi indeplinesc conditia
     * @throws SQLException, daca nu am conexiunea facuta
     * @throws IOException, daca conexiunea nu se poate realiza
     */
    public List<Course> freePlaces() throws SQLException, IOException {
        List<Course> all = this.getAll();
        List<Course> free = new ArrayList<>();
        for (Course course : all) {
            if ((course.getMaxEnrollment() - this.getNumberOfStudents(course.getId())) != 0)
                free.add(course);
        }
        return free;
    }

    /**
     * sorteaza lista dupa numarul de credite > 10
     * @return lista sortata
     */
    public List<Course> filter() throws SQLException, IOException {
        List<Course> list = this.getAll();
        return list.stream()
                .filter(course -> course.getCredits() > 10).toList();
    }

    /**
     *sortez lista crescator dupa numarul de credite
     */
    public List<Course> sort() throws SQLException, IOException {
        List<Course> list = this.getAll();
        list.sort(Course::compareTo);
        return list;
    }

    /**
     * returnez un curs dupa id-ul sau
     * @param id, id-ul cursului
     * @return cursul respectiv
     */
    public Course getCourseFromId(long id) {

        Course course = null;
        try {
            sql = new SQL();
            connection = sql.startConnection();
            statement = connection.createStatement();
            String query = "SELECT * FROM course WHERE idCourse = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setLong(1, id);
            ResultSet resultSet = preparedStmt.executeQuery();

            if(resultSet.next())
            {
                String name = resultSet.getString("Name");
                long idTeacher = resultSet.getLong("idTeacher");
                int max = resultSet.getInt("maxEnrollment");
                int credits = resultSet.getInt("credits");
                course = new Course(id, name, idTeacher, max, credits);
            }

            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return course;
    }

}