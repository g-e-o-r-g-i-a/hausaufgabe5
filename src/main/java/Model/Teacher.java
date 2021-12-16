package Model;

public class Teacher extends Person{

    private long idTeacher;

    public Teacher(String firstName, String lastName, long idTeacher) {
        super(firstName, lastName);
        this.idTeacher = idTeacher;
    }

    public long getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(long idTeacher) {
        this.idTeacher = idTeacher;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "idTeacher=" + idTeacher +
                '}';
    }

}
