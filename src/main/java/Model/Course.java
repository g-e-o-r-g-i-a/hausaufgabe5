package Model;

public class Course implements Comparable<Course>{

    private long id;
    private String name;
    private long teacher;
    private int maxEnrollment;
    private int credits;

    public Course(long id, String name, long teacher, int maxEnrollment, int credits) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.maxEnrollment = maxEnrollment;
        this.credits = credits;
    }

    public Course(long id,int credits) {
        this.id = id;
        this.name = "";
        this.teacher = 0L;
        this.maxEnrollment = 0;
        this.credits = credits;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTeacher() {
        return teacher;
    }

    public void setTeacher(long teacher) {
        this.teacher = teacher;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", maxEnrollment=" + maxEnrollment +
                ", credits=" + credits +
                '}';
    }

    /**
     * compar numarul de credite
     * @param course
     */
    @Override
    public int compareTo(Course course) {
        if(this.getCredits() > course.getCredits())
            return -1;

        if(this.getCredits() < course.getCredits())
            return 0;

        return 1;
    }

}