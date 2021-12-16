import Controller.EnrolledContr;
import Controller.CourseContr;
import Controller.TeacherContr;
import Controller.StudentContr;
import Model.Enrolled;
import Model.Course;
import Model.Teacher;
import Model.Student;
import Exception.AlreadyExist;
import Exception.EmptyArray;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;


public class Console {

    private CourseContr courseContr;
    private TeacherContr teacherContr;
    private StudentContr studentContr;
    private EnrolledContr enrolledContr;

    public Console(CourseContr courseContr, TeacherContr teacherContr, StudentContr studentContr, EnrolledContr enrolledContr) {
        this.courseContr = courseContr;
        this.teacherContr = teacherContr;
        this.studentContr = studentContr;
        this.enrolledContr = enrolledContr;
    }

    public EnrolledContr getEnrolledContr() {
        return enrolledContr;
    }

    public void setEnrolledController(EnrolledContr enrolledContr) {
        this.enrolledContr = enrolledContr;
    }

    public CourseContr getCourseContr() {
        return courseContr;
    }

    public void setCourseContr(CourseContr courseContr) {
        this.courseContr = courseContr;
    }

    public TeacherContr getTeacherContr() {
        return teacherContr;
    }

    public void setTeacherContr(TeacherContr teacherContr) {
        this.teacherContr = teacherContr;
    }

    public StudentContr getStudentContr() {
        return studentContr;
    }

    public void setStudentContr(StudentContr studentContr) {
        this.studentContr = studentContr;
    }


    public void Menu() {
        System.out.println("""
                1.Register\s
                2.Add\s
                3.Courses with free places\s
                4.Students enrolled for a course\s
                5.Get all courses\s
                6.Sort and filter\s
                7.Exit\s
                """);
    }

    public void start() throws IOException, AlreadyExist, InterruptedException, SQLException, EmptyArray {
        while (true) {
            Menu();
            Scanner keyboard = new Scanner(System.in);
            int option;
            do {
                System.out.print("Choose an option: ");
                option = keyboard.nextInt();
            }
            while (option < 1 && option > 7);

            long id;
            long idCourse;
            long idStudent;
            long idTeacher;

            switch (option) {
                case 1 -> {
                    do {
                        System.out.print("Choose a course: ");
                        idCourse = keyboard.nextInt();
                    }
                    while (!courseContr.findOne(idCourse));
                    do {
                        System.out.print("Choose a student ");
                        idStudent = keyboard.nextInt();
                    }
                    while (!studentContr.findOne(idStudent));

                    if (this.enrolledContr.create(new Enrolled(idStudent, idCourse)) != null)
                        System.out.println("The registration was successful");
                    else
                        System.out.println("The registration failed");
                }
                case 2 -> {
                    chooseWhatToAdd();
                    AddMenu();

                }

                case 3 -> {
                    System.out.println("Courses with free places:\n" + courseContr.getFreePlaces());
                }

                case 4 -> {
                    System.out.println("ID of the course:");
                    id = keyboard.nextLong();
                    if (courseContr.findOne(id)) {
                        System.out.println(studentContr.getEnrolledStudents(id));
                    } else
                        System.out.println("Invalid ID.\n");
                }

                case 5 -> {
                    System.out.println("All courses:\n" + courseContr.getAll());
                }
                case 6 -> {
                    chooseWhatToSortorFilter();
                    filterOrSortMenu();

                }
                case 7 -> {
                    System.exit(0);
                }
            }
        }
    }




    public void chooseWhatToAdd()
    {
        System.out.println("""
                1.Add a course\s
                2.Add a teacher\s
                3.Add a studenten\s
                """);
    }

    public Student createStudent() throws SQLException, IOException {
        Scanner scan= new Scanner(System.in);
        System.out.println("firstName:");
        String firstName= scan.nextLine();
        System.out.println("lastName:");
        String lastName= scan.nextLine();
        long id;
        do{
            System.out.println("ID:");
            id= scan.nextLong();
        }while(studentContr.findOne(id));

        return new Student(id, firstName, lastName);

    }

    public Teacher createTeacher() throws SQLException, IOException {
        Scanner scan= new Scanner(System.in);
        System.out.println("fisrtName:");
        String firstName= scan.nextLine();
        System.out.println("lastName:");
        String lastName= scan.nextLine();
        long id;
        do{
            System.out.println("ID:");
            id= scan.nextLong();
        }while(teacherContr.findOne(id));

        return new Teacher(firstName, lastName, id);

    }

    public Course createCourse() throws SQLException, IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Name:");
        String name = scan.nextLine();

        long id;
        do {
            System.out.println("ID:");
            id = scan.nextLong();
        } while (courseContr.findOne(id));

        long idTeacher;
        do {
            System.out.println("Teacher:");
            idTeacher = scan.nextLong();
        } while (!teacherContr.findOne(idTeacher));

        int maxEnrollment;
        do {
            System.out.println("The maximal number of enrolled students:");
            maxEnrollment = scan.nextInt();
        } while (maxEnrollment <= 0);

        int credits;
        do {
            System.out.println("Credits:");
            credits = scan.nextInt();
        } while (credits <= 0);

        return new Course(id, name, idTeacher, maxEnrollment, credits);
    }



        public void AddMenu() throws IOException, AlreadyExist, SQLException {
        Scanner scan= new Scanner(System.in);
        int option;
        do {
            System.out.print("Choose an option ");
            option = scan.nextInt();
        }
        while(option<1 && option >3);

        switch (option) {
            case 1 -> {
                Course course = this.createCourse();
                courseContr.create(course);
            }
            case 2 -> {
                Teacher teacher = this.createTeacher();
                teacherContr.create(teacher);
            }
            case 3 -> {
                Student student = this.createStudent();
                studentContr.create(student);
            }
        }
    }

    public void chooseWhatToSortorFilter()
    {
        System.out.println("""
                1.Filter a course\s
                2.Sort a course\s
                3.Filter a student\s
                4.Sort a student\s
                """);
    }


    public void filterOrSortMenu() throws InterruptedException, SQLException, IOException {
        Scanner scan= new Scanner(System.in);
        int option;
        do {
            System.out.print("Choose a option: ");
            option = scan.nextInt();
        }
        while(option<1 && option >4);

        switch (option) {
            case 1 -> {
                System.out.println(courseContr.filter());
            }
            case 2 -> {
                courseContr.sort();
                System.out.println(courseContr.sort());
            }
            case 3 -> {
                System.out.println(studentContr.filter());
            }
            case 4 -> {
                studentContr.sort();
                System.out.println(studentContr.sort());
            }
        }
    }
}


