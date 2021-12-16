package Controller;
import Exception.AlreadyExist;
import Model.Enrolled;
import Model.Course;
import Model.Student;
import Repository.EnrolledRepo;
import Repository.CourseRepo;
import Exception.EmptyArray;
import Repository.StudentRepo;
import Repository.TeacherRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public  class EnrolledContr {

    private CourseRepo course;
    private StudentRepo student;
    private EnrolledRepo enrolled;

    public EnrolledContr(CourseRepo course, StudentRepo student, EnrolledRepo enrolled) {
        this.course = course;
        this.student = student;
        this.enrolled = enrolled;
    }


    public Enrolled create(Enrolled obj) throws IOException, AlreadyExist, SQLException, EmptyArray {

        long idCourse = obj.getIdCourse();
        long idStudent = obj.getIdStudent();

        if(this.course.findOne(idCourse) && this.student.findOne(idStudent))
        {
            if(!this.enrolled.findOne(idStudent, idCourse))
            {
                Course course1 = this.course.getCourseFromId(idCourse);
                if((course1.getMaxEnrollment() - this.course.getNumberOfStudents(idCourse) > 0) && (this.student.getEnoughCredits(idStudent) >= course1.getCredits()))
                {
                    this.enrolled.create(obj);
                    Student student1 = this.student.getStudentFromId(idStudent);
                    Student student2 = new Student(student1.getFirstName(),student1.getLastName(),idStudent,(student1.getTotalCredits()+course1.getCredits()));
                    this.student.update(student2);
                    return obj;
                }
            }

        }
        return null;

    }

    public List<Enrolled> getAll() throws SQLException, IOException, EmptyArray {
        return this.enrolled.getAll();
    }


    public boolean findOne(long idCourse, long idStudent) throws IOException, SQLException {
        return this.enrolled.findOne(idStudent, idCourse);
    }
}