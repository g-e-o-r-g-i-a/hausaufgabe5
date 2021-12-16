package Controller;
import Exception.AlreadyExist;
import Model.Course;
import Repository.EnrolledRepo;
import Repository.CourseRepo;
import Repository.TeacherRepo;
import Repository.StudentRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CourseContr implements Contr<Course> {

    private CourseRepo course;
    private StudentRepo student;
    private TeacherRepo teacher;
    private EnrolledRepo enrolled;

    public CourseContr(CourseRepo course, StudentRepo student, TeacherRepo teacher, EnrolledRepo enrolled) {

    }

    public CourseRepo getCourse() {
        return course;
    }

    public void setCourse(CourseRepo course) {
        this.course = course;
    }

    public StudentRepo getStudent() {
        return student;
    }

    public void setStudent(StudentRepo student) {
        this.student = student;
    }

    public TeacherRepo getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherRepo teacher) {
        this.teacher = teacher;
    }

    public EnrolledRepo getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(EnrolledRepo enrolled) {
        this.enrolled = enrolled;
    }


    @Override
    public Course create(Course obj) throws IOException, AlreadyExist, SQLException {
        return course.create(obj);
    }


    @Override
    public List<Course> getAll() throws SQLException, IOException {
        return course.getAll();
    }


    @Override
    public Course update(Course obj) throws IOException, SQLException {
        return course.update(obj);
    }


    @Override
    public boolean delete(Long objID) throws IOException, SQLException {
        return course.delete(objID);
    }


    @Override
    public boolean findOne(Long id) throws IOException, SQLException {
        return course.findOne(id);
    }


    public List<Course> filter() throws SQLException, IOException {
        return course.filter();
    }


    public List<Course> sort() throws SQLException, IOException {
        return course.sort();
    }


    public void deleteCourse(long idCourse, long idTeacher) throws SQLException, IOException {
        if(this.course.findOne(idCourse) && this.teacher.findOne(idTeacher))
        {
            Course course1 = this.course.getCourseFromId(idCourse);
            if(course1.getTeacher() == idTeacher)
            {
                this.enrolled.deleteEnrolledAfterCourse(idCourse);
                this.course.delete(idCourse);
            }
        }
    }


    public List<Course> getFreePlaces() throws SQLException, IOException {
        return this.course.freePlaces();
    }


}