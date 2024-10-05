import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private int enrolled;

    public Course(String code, String title, String description, int capacity) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public boolean enroll() {
        if (enrolled < capacity) {
            enrolled++;
            return true;
        }
        return false;
    }

    public void drop() {
        if (enrolled > 0) {
            enrolled--;
        }
    }

    public String getDetails() {
        return String.format("%s - %s: %s (Enrolled: %d/%d)", code, title, description, enrolled, capacity);
    }
}

class Student {
    private String studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean isRegistered(Course course) {
        return registeredCourses.contains(course);
    }
}

class CourseRegistrationSystem {
    private List<Course> courses;
    private List<Student> students;

    public CourseRegistrationSystem() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
        initializeCourses();
    }

    private void initializeCourses() {
        courses.add(new Course("CS101", "Introduction to Computer Science", "Learn the basics of computer science.", 30));
        courses.add(new Course("CS102", "Data Structures", "Study data structures and algorithms.", 25));
        courses.add(new Course("CS201", "Database Systems", "Learn about databases and SQL.", 20));
        courses.add(new Course("CS301", "Web Development", "Introduction to web technologies.", 15));
    }

    public void registerStudent(String studentId, String name) {
        students.add(new Student(studentId, name));
    }

    public List<Course> getAvailableCourses() {
        return courses;
    }

    public void enrollStudentInCourse(Student student, Course course) {
        if (course.enroll()) {
            student.registerCourse(course);
            System.out.println("Successfully registered for " + course.getTitle());
        } else {
            System.out.println("Course " + course.getTitle() + " is full.");
        }
    }

    public void dropStudentFromCourse(Student student, Course course) {
        if (student.isRegistered(course)) {
            course.drop();
            student.dropCourse(course);
            System.out.println("Successfully dropped from " + course.getTitle());
        } else {
            System.out.println("Not registered for course " + course.getTitle());
        }
    }

    public void displayCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println(course.getDetails());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        System.out.println("Welcome to the Course Registration System");
        System.out.print("Enter your Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter your Name: ");
        String studentName = scanner.nextLine();
        
        system.registerStudent(studentId, studentName);
        Student currentStudent = new Student(studentId, studentName);

        int choice;
        do {
            System.out.println("\n1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    system.displayCourses();
                    break;
                case 2:
                    system.displayCourses();
                    System.out.print("Enter the course code to register: ");
                    String courseCodeToRegister = scanner.next();
                    Course courseToRegister = system.getAvailableCourses().stream()
                            .filter(c -> c.getCode().equalsIgnoreCase(courseCodeToRegister))
                            .findFirst().orElse(null);
                    if (courseToRegister != null) {
                        system.enrollStudentInCourse(currentStudent, courseToRegister);
                    } else {
                        System.out.println("Invalid course code.");
                    }
                    break;
                case 3:
                    System.out.print("Enter the course code to drop: ");
                    String courseCodeToDrop = scanner.next();
                    Course courseToDrop = system.getAvailableCourses().stream()
                            .filter(c -> c.getCode().equalsIgnoreCase(courseCodeToDrop))
                            .findFirst().orElse(null);
                    if (courseToDrop != null) {
                        system.dropStudentFromCourse(currentStudent, courseToDrop);
                    } else {
                        System.out.println("Invalid course code.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
