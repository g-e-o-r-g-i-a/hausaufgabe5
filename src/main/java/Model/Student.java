package Model;

public class Student extends Person implements Comparable<Student>{

    private long idStudent;
    private int totalCredits;

    public Student(String firstName, String lastName, long idStudent, int totalCredits) {
        super(firstName,lastName);
        this.idStudent = idStudent;
        this.totalCredits = totalCredits;
    }

    public Student(long id, String firstName, String lastName) {
        super(firstName,lastName);
        this.idStudent = id;
        this.totalCredits = 0;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", totalCredits=" + totalCredits +
                '}';
    }

    /**
     * socotesc cat mai are nevoie un student pana la 30 de credite
     * @return numarul de credite necesar
     */
    public int getEnoughCredits()
    {
        return (30 - this.getTotalCredits());
    }


    /**
     * compar numarul de credite la doi studenti
     * @param student, cu care compar
     */
    @Override
    public int compareTo(Student student) {
        return Integer.compare(this.getTotalCredits(), student.getTotalCredits());

    }
}