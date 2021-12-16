package Model;

public class Enrolled {

    public static long id = 0;
    private long idStudent;
    private long idCourse;

    public Enrolled(long idStudent, long idCourse) {
        this.idStudent = idStudent;
        this.idCourse = idCourse;
        id++;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public long getIdCourse() {
        return idCourse;
    }

    public void setIdKurs(long idCourse) {
        this.idCourse = idCourse;
    }

    @Override
    public String toString() {
        return "Enrolled{" +
                "idStudent=" + idStudent +
                ", idCourse=" + idCourse +
                '}';
    }
}