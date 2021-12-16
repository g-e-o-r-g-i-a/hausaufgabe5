package Controller;
import Exception.AlreadyExist;
import Model.Student;
import Repository.StudentRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentContr implements Contr<Student> {

    private StudentRepo student;


    public StudentContr(StudentRepo student) {

        this.student = student;

    }

    @Override
    public Student create(Student obj) throws IOException,AlreadyExist, SQLException {
        return this.student.create(obj);
    }


    @Override
    public List<Student> getAll() throws SQLException, IOException {
        return this.student.getAll();
    }


    @Override
    public Student update(Student obj) throws IOException, SQLException {
        return this.student.update(obj);
    }


    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException {
        return this.student.delete(objID);
    }


    @Override
    public boolean findOne(Long id) throws IOException, SQLException {
        return this.student.findOne(id);
    }


    public List<Student> filter() throws SQLException, IOException {
        return this.student.filterList();
    }


    public List<Student> sort() throws SQLException, IOException {
        return this.student.sortList();
    }


    public List<Long> getEnrolledStudents(long idCourse) throws SQLException{
        return this.student.getStudentenEnrolledToCourse(idCourse);
    }
}