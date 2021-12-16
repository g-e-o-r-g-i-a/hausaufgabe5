package Controller;

import Model.Teacher;
import Repository.TeacherRepo;
import Exception.AlreadyExist;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TeacherContr implements Contr<Teacher>{

    private TeacherRepo teacher;

    public TeacherContr(TeacherRepo teacher) {
        this.teacher = teacher;
    }

    public TeacherRepo getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherRepo teacher) {
        this.teacher = teacher;
    }


    @Override
    public Teacher create(Teacher obj) throws IOException,AlreadyExist, SQLException {
        return this.teacher.create(obj);
    }


    @Override
    public List<Teacher> getAll() throws SQLException, IOException {
        return this.teacher.getAll();
    }


    @Override
    public Teacher update(Teacher obj) throws IOException, SQLException {
        return this.teacher.update(obj);
    }


    @Override
    public boolean delete(Long id) throws IOException, SQLException {
        return this.teacher.delete(id);
    }


    @Override
    public boolean findOne(Long id) throws IOException, SQLException {
        return this.teacher.findOne(id);
    }
}