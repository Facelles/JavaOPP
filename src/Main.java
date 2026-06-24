import entities.Course;
import entities.Student;
import entities.Teacher;
import enums.Grade;
import enums.StudentStatus;
import enums.TeacherPosition;
import services.CourseService;
import services.EnrollmentService;
import services.StudentService;
import services.TeacherService;
import util.GPAUtils;

import java.util.Scanner;

public class Main {
    private static final StudentService studentService = new StudentService();
    private static final TeacherService teacherService = new TeacherService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initTestData();

        while (true) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Студенти");
            System.out.println("2. Викладачі");
            System.out.println("3. Курси");
            System.out.println("4. Зарахування");
            System.out.println("5. Звіти / Пошук");
            System.out.println("6. Зберегти дані у JSON");
            System.out.println("7. Завантажити дані з JSON");
            System.out.println("0. Вихід");
            System.out.print("Оберіть пункт: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    studentMenu();
                    break;
                case "2":
                    teacherMenu();
                    break;
                case "3":
                    courseMenu();
                    break;
                case "4":
                    enrollmentMenu();
                    break;
                case "5":
                    reportMenu();
                    break;
                case "6":
                    saveData();
                    break;
                case "7":
                    loadData();
                    break;
                case "0":
                    System.out.println("Дякуємо за використання системи!");
                    return;
                default:
                    System.out.println("Невірний пункт меню. Спробуйте ще раз.");
            }
        }
    }

    private static void saveData() {
        util.JsonStorage.saveStudents(studentService.getAllStudents(), "students.json");
        util.JsonStorage.saveTeachers(teacherService.getAllTeachers(), "teachers.json");
        util.JsonStorage.saveCourses(courseService.getAllCourses(), "courses.json");
        util.JsonStorage.saveEnrollments(enrollmentService.getAllEnrollments(), "enrollments.json");
        System.out.println("Дані успішно збережено у JSON-файли!");
    }

    private static void loadData() {
        studentService.setAllStudents(util.JsonStorage.loadStudents("students.json"));
        teacherService.setAllTeachers(util.JsonStorage.loadTeachers("teachers.json"));
        courseService.setAllCourses(util.JsonStorage.loadCourses("courses.json"));
        enrollmentService.setAllEnrollments(util.JsonStorage.loadEnrollments("enrollments.json"));

        // Відновлення посилань
        for (Course c : courseService.getAllCourses()) {
            if (c != null && c.getTeacher() != null) {
                Teacher realTeacher = teacherService.findById(c.getTeacher().getId());
                if (realTeacher != null) c.setTeacher(realTeacher);
            }
        }

        for (entities.Enrollment e : enrollmentService.getAllEnrollments()) {
            if (e != null) {
                if (e.getStudent() != null) {
                    Student realStudent = studentService.findById(e.getStudent().getId());
                    if (realStudent != null) e.setStudent(realStudent);
                }
                if (e.getCourse() != null) {
                    Course realCourse = courseService.findById(e.getCourse().getId());
                    if (realCourse != null) e.setCourse(realCourse);
                }
            }
        }

        System.out.println("Дані успішно завантажено та зв'язки відновлено!");
    }

    private static void initTestData() {
        Student s1 = new Student(1, "Іванов Іван", "ivan@gmail.com", 1, StudentStatus.ACTIVE);
        Student s2 = new Student(2, "Антонов Антон", "anton@gmail.com", 2, StudentStatus.ACTIVE);
        Student s3 = new Student(3, "Петров Петро", "petro@gmail.com", 3, StudentStatus.ON_LEAVE);
        studentService.addStudent(s1);
        studentService.addStudent(s2);
        studentService.addStudent(s3);

        Teacher t1 = new Teacher(1, "Васильєв Василь", "vasyl@univ.edu", TeacherPosition.PROFFESOR);
        Teacher t2 = new Teacher(2, "Олексіїв Олексій", "olexiy@univ.edu", TeacherPosition.LECTURER);
        teacherService.addTeacher(t1);
        teacherService.addTeacher(t2);

        Course c1 = new Course(1, "Java Basics", 3, t1);
        Course c2 = new Course(2, "Advanced Java", 4, t1);
        Course c3 = new Course(3, "Web Development", 5, t2);
        courseService.addCourse(c1);
        courseService.addCourse(c2);
        courseService.addCourse(c3);
        
        enrollmentService.enrollStudent(s1, c1, 1);
        enrollmentService.setGrade(s1.getId(), c1.getId(), Grade.A);
        enrollmentService.payForCourse(s1.getId(), c1.getId());
        
        enrollmentService.enrollStudent(s1, c2, 2);
        enrollmentService.setGrade(s1.getId(), c2.getId(), Grade.B);
        
        enrollmentService.enrollStudent(s2, c3, 1);
    }

    private static void studentMenu() {
        while (true) {
            System.out.println("\n--- МЕНЮ: СТУДЕНТИ ---");
            System.out.println("1. Показати всіх студентів");
            System.out.println("2. Додати студента");
            System.out.println("3. Сортувати за ПІБ");
            System.out.println("4. Видалити студента");
            System.out.println("5. Оновити дані студента");
            System.out.println("0. Назад");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        studentService.printAllStudents();
                        break;
                    case "2":
                        System.out.print("Введіть ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введіть ПІБ: ");
                        String name = scanner.nextLine();
                        System.out.print("Введіть Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Введіть курс (1-6): ");
                        int year = Integer.parseInt(scanner.nextLine());

                        Student newStudent = new Student(id, name, email, year, StudentStatus.ACTIVE);
                        studentService.addStudent(newStudent);
                        System.out.println("Студента успішно додано!");
                        break;
                    case "3":
                        studentService.sortAndPrint();
                        break;
                    case "4":
                        System.out.print("Введіть ID студента для видалення: ");
                        int idToDelete = Integer.parseInt(scanner.nextLine());
                        if (studentService.deleteStudent(idToDelete)) {
                            enrollmentService.deleteEnrollmentsForStudent(idToDelete);
                            System.out.println("Студента та всі його зарахування видалено.");
                        } else {
                            System.out.println("Студента з таким ID не знайдено.");
                        }
                        break;
                    case "5":
                        System.out.print("Введіть ID студента для оновлення: ");
                        int updateId = Integer.parseInt(scanner.nextLine());
                        Student st = studentService.findById(updateId);
                        if (st == null) {
                            System.out.println("Студента не знайдено.");
                            break;
                        }
                        System.out.print("Введіть нове ПІБ (поточне " + st.getName() + "): ");
                        String newName = scanner.nextLine();
                        System.out.print("Введіть новий Email (поточний " + st.getEmail() + "): ");
                        String newEmail = scanner.nextLine();
                        System.out.print("Введіть новий курс (поточний " + st.getYear() + "): ");
                        int newYear = Integer.parseInt(scanner.nextLine());
                        System.out.println("Оберіть статус (1-ACTIVE, 2-ON_LEAVE, 3-EXPELLED, 4-GRADUATED): ");
                        int statChoice = Integer.parseInt(scanner.nextLine());
                        StudentStatus newStatus = switch (statChoice) {
                            case 2 -> StudentStatus.ON_LEAVE;
                            case 3 -> StudentStatus.EXPELLED;
                            case 4 -> StudentStatus.GRADUATED;
                            default -> StudentStatus.ACTIVE;
                        };
                        
                        studentService.updateStudent(updateId, newName, newEmail, newYear, newStatus);
                        System.out.println("Дані студента оновлено.");
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Невірна дія.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введено текст замість числа!");
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
            }
        }
    }

    private static void teacherMenu() {
        while (true) {
            System.out.println("\n--- МЕНЮ: ВИКЛАДАЧІ ---");
            System.out.println("1. Показати всіх викладачів");
            System.out.println("2. Додати викладача");
            System.out.println("3. Видалити викладача");
            System.out.println("4. Оновити дані викладача");
            System.out.println("0. Назад");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        teacherService.printAllTeachers();
                        break;
                    case "2":
                        System.out.print("Введіть ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введіть ПІБ: ");
                        String name = scanner.nextLine();
                        System.out.print("Введіть Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Оберіть посаду (1-ASISTENT, 2-LECTURER, 3-PROFFESOR): ");
                        int pos = Integer.parseInt(scanner.nextLine());
                        TeacherPosition position = switch (pos) {
                            case 1 -> TeacherPosition.ASISTENT;
                            case 2 -> TeacherPosition.LECTURER;
                            default -> TeacherPosition.PROFFESOR;
                        };

                        Teacher newTeacher = new Teacher(id, name, email, position);
                        teacherService.addTeacher(newTeacher);
                        System.out.println("Викладача успішно додано!");
                        break;
                    case "3":
                        System.out.print("Введіть ID викладача для видалення: ");
                        int idToDelete = Integer.parseInt(scanner.nextLine());
                        if (teacherService.deleteTeacher(idToDelete)) {
                            System.out.println("Викладача видалено.");
                        } else {
                            System.out.println("Викладача з таким ID не знайдено.");
                        }
                        break;
                    case "4":
                        System.out.print("Введіть ID викладача для оновлення: ");
                        int tUpdateId = Integer.parseInt(scanner.nextLine());
                        Teacher t = teacherService.findById(tUpdateId);
                        if (t == null) {
                            System.out.println("Викладача не знайдено.");
                            break;
                        }
                        System.out.print("Введіть нове ПІБ (поточне " + t.getName() + "): ");
                        String tNewName = scanner.nextLine();
                        System.out.print("Введіть новий Email (поточний " + t.getEmail() + "): ");
                        String tNewEmail = scanner.nextLine();
                        System.out.print("Оберіть посаду (1-ASISTENT, 2-LECTURER, 3-PROFFESOR): ");
                        int tPos = Integer.parseInt(scanner.nextLine());
                        TeacherPosition tNewPos = switch (tPos) {
                            case 1 -> TeacherPosition.ASISTENT;
                            case 2 -> TeacherPosition.LECTURER;
                            default -> TeacherPosition.PROFFESOR;
                        };
                        teacherService.updateTeacher(tUpdateId, tNewName, tNewEmail, tNewPos);
                        System.out.println("Дані викладача оновлено.");
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Невірна дія.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введено текст замість числа!");
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
            }
        }
    }

    private static void courseMenu() {
        while (true) {
            System.out.println("\n--- МЕНЮ: КУРСИ ---");
            System.out.println("1. Показати всі курси");
            System.out.println("2. Додати курс");
            System.out.println("3. Видалити курс");
            System.out.println("4. Оновити курс");
            System.out.println("5. Фільтр за викладачем");
            System.out.println("6. Фільтр за кредитами");
            System.out.println("0. Назад");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        courseService.printAllCourses();
                        break;
                    case "2":
                        System.out.print("Введіть ID курсу: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введіть назву: ");
                        String name = scanner.nextLine();
                        System.out.print("Введіть кількість кредитів: ");
                        int credits = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введіть ID викладача: ");
                        int teacherId = Integer.parseInt(scanner.nextLine());
                        Teacher teacher = teacherService.findById(teacherId);
                        if (teacher == null) {
                            System.out.println("Помилка: викладача з таким ID не знайдено.");
                            break;
                        }

                        Course newCourse = new Course(id, name, credits, teacher);
                        courseService.addCourse(newCourse);
                        System.out.println("Курс успішно додано!");
                        break;
                    case "3":
                        System.out.print("Введіть ID курсу для видалення: ");
                        int idToDelete = Integer.parseInt(scanner.nextLine());
                        if (courseService.deleteCourse(idToDelete)) {
                            enrollmentService.deleteEnrollmentsForCourse(idToDelete);
                            System.out.println("Курс та всі пов'язані зарахування видалено.");
                        } else {
                            System.out.println("Курс з таким ID не знайдено.");
                        }
                        break;
                    case "4":
                        System.out.print("Введіть ID курсу для оновлення: ");
                        int cUpdateId = Integer.parseInt(scanner.nextLine());
                        Course cToUpdate = courseService.findById(cUpdateId);
                        if (cToUpdate == null) {
                            System.out.println("Курс не знайдено.");
                            break;
                        }
                        System.out.print("Введіть нову назву (поточна " + cToUpdate.getName() + "): ");
                        String cNewName = scanner.nextLine();
                        System.out.print("Введіть нові кредити (поточні " + cToUpdate.getCredits() + "): ");
                        int cNewCredits = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введіть ID нового викладача (або 0 щоб залишити " + cToUpdate.getTeacher().getName() + "): ");
                        int cNewTeacherId = Integer.parseInt(scanner.nextLine());
                        Teacher cNewTeacher = null;
                        if (cNewTeacherId != 0) {
                            cNewTeacher = teacherService.findById(cNewTeacherId);
                            if (cNewTeacher == null) {
                                System.out.println("Помилка: викладача з таким ID не знайдено.");
                                break;
                            }
                        }
                        courseService.updateCourse(cUpdateId, cNewName, cNewCredits, cNewTeacher);
                        System.out.println("Курс оновлено.");
                        break;
                    case "5":
                        System.out.print("Введіть ID викладача: ");
                        int filterTeacherId = Integer.parseInt(scanner.nextLine());
                        courseService.printCoursesByTeacher(filterTeacherId);
                        break;
                    case "6":
                        System.out.print("Введіть кількість кредитів: ");
                        int filterCredits = Integer.parseInt(scanner.nextLine());
                        courseService.printCoursesByCredits(filterCredits);
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Невірна дія.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введено текст замість числа!");
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
            }
        }
    }

    private static void enrollmentMenu() {
        while (true) {
            System.out.println("\n--- МЕНЮ: ЗАРАХУВАННЯ ---");
            System.out.println("1. Зарахувати студента на курс");
            System.out.println("2. Поставити оцінку");
            System.out.println("3. Позначити оплату");
            System.out.println("4. Переглянути всі зарахування");
            System.out.println("5. Транскрипт студента (зарахування та GPA)");
            System.out.println("0. Назад");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        System.out.print("Введіть ID студента: ");
                        int studentId = Integer.parseInt(scanner.nextLine());
                        Student student = studentService.findById(studentId);
                        if (student == null) {
                            System.out.println("Студента не знайдено.");
                            break;
                        }

                        System.out.print("Введіть ID курсу: ");
                        int courseId = Integer.parseInt(scanner.nextLine());
                        Course course = courseService.findById(courseId);
                        if (course == null) {
                            System.out.println("Курс не знайдено.");
                            break;
                        }

                        System.out.print("Введіть семестр: ");
                        int semester = Integer.parseInt(scanner.nextLine());

                        enrollmentService.enrollStudent(student, course, semester);
                        System.out.println("Студента успішно зараховано на курс.");
                        break;
                    case "2":
                        System.out.print("Введіть ID студента: ");
                        int sId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введіть ID курсу: ");
                        int cId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введіть оцінку (A, B, C, D, F, NA): ");
                        String gStr = scanner.nextLine().toUpperCase();
                        try {
                            Grade grade = Grade.valueOf(gStr);
                            enrollmentService.setGrade(sId, cId, grade);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Невірна оцінка.");
                        }
                        break;
                    case "3":
                        System.out.print("Введіть ID студента: ");
                        int paySId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введіть ID курсу: ");
                        int payCId = Integer.parseInt(scanner.nextLine());
                        enrollmentService.payForCourse(paySId, payCId);
                        break;
                    case "4":
                        enrollmentService.printAllEnrollments();
                        break;
                    case "5":
                        System.out.print("Введіть ID студента: ");
                        int transId = Integer.parseInt(scanner.nextLine());
                        Student st = studentService.findById(transId);
                        if (st != null) {
                            System.out.println("Транскрипт студента: " + st.getName());
                            enrollmentService.printEnrollmentsByStudent(transId);
                            double gpa = GPAUtils.calculateGPA(enrollmentService.getEnrollmentsForStudent(transId));
                            System.out.println("Загальний GPA: " + String.format("%.2f", gpa));
                        } else {
                            System.out.println("Студента не знайдено.");
                        }
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Невірна дія.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введено текст замість числа!");
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
            }
        }
    }

    private static void reportMenu() {
        while (true) {
            System.out.println("\n--- МЕНЮ: ЗВІТИ ТА ПОШУК ---");
            System.out.println("1. Пошук студента за частиною ПІБ або email");
            System.out.println("2. Фільтр студентів за статусом");
            System.out.println("3. Фільтр студентів за курсом (роком навчання)");
            System.out.println("4. Список неоплачених курсів");
            System.out.println("5. Топ-N студентів за GPA");
            System.out.println("6. Середній GPA по курсу");
            System.out.println("0. Назад");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        System.out.print("Введіть частину імені або email для пошуку: ");
                        String query = scanner.nextLine();
                        studentService.search(query);
                        break;
                    case "2":
                        System.out.print("Введіть статус (ACTIVE, ON_LEAVE, EXPELLED, GRADUATED): ");
                        String statusStr = scanner.nextLine().toUpperCase();
                        try {
                            StudentStatus status = StudentStatus.valueOf(statusStr);
                            studentService.filterByStatus(status);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Невірний статус.");
                        }
                        break;
                    case "3":
                        System.out.print("Введіть курс (рік навчання 1-6): ");
                        int year = Integer.parseInt(scanner.nextLine());
                        studentService.filterByYear(year);
                        break;
                    case "4":
                        enrollmentService.printUnpaidEnrollments();
                        break;
                    case "5":
                        Student[] allStudents = studentService.getAllStudents();
                        if (allStudents.length == 0) {
                            System.out.println("Студентів немає.");
                            break;
                        }
                        System.out.print("Введіть кількість топ-студентів (N): ");
                        int topN = Integer.parseInt(scanner.nextLine());
                        if (topN <= 0) break;
                        GPAUtils.sortStudentsByGPA(allStudents, allStudents.length, enrollmentService);
                        System.out.println("Топ " + topN + " студентів за GPA:");
                        for (int i = 0; i < Math.min(topN, allStudents.length); i++) {
                            Student s = allStudents[i];
                            double gpa = GPAUtils.calculateGPA(enrollmentService.getEnrollmentsForStudent(s.getId()));
                            System.out.println((i + 1) + ". " + s.getName() + " - GPA: " + String.format("%.2f", gpa));
                        }
                        break;
                    case "6":
                        System.out.print("Введіть ID курсу для підрахунку середнього GPA: ");
                        int avgCourseId = Integer.parseInt(scanner.nextLine());
                        double avgGpa = GPAUtils.calculateAverageGpaForCourse(avgCourseId, enrollmentService.getAllEnrollments());
                        System.out.println("Середній GPA по курсу: " + String.format("%.2f", avgGpa));
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Невірна дія.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введено текст замість числа!");
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
            }
        }
    }
}