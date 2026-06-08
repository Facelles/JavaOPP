
import entities.Student;
import enums.StudentStatus;
import services.StudentService;
import java.util.Scanner;

public class Main {
    private static final StudentService studentService = new StudentService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Додамо тестові дані для перевірки
        studentService.addStudent(new Student(1, "Іванов Іван", "ivan@gmail.com", 1, StudentStatus.ACTIVE));
        studentService.addStudent(new Student(2, "Антонов Антон", "anton@gmail.com", 2, StudentStatus.ACTIVE));

        while (true) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Студенти");
            System.out.println("2. Викладачі (Реалізуй самостійно)");
            System.out.println("3. Курси (Реалізуй самостійно)");
            System.out.println("0. Вихід");
            System.out.print("Оберіть пункт: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    studentMenu();
                    break;
                case "0":
                    System.out.println("Дякуємо за використання системи!");
                    return;
                default:
                    System.out.println("Невірний пункт меню. Спробуйте ще раз.");
            }
        }
    }

    private static void studentMenu() {
        System.out.println("\n--- МЕНЮ: СТУДЕНТИ ---");
        System.out.println("1. Показати всіх студентів");
        System.out.println("2. Додати студента");
        System.out.println("3. Сортувати за ПІБ");
        System.out.println("4. Видалити студента");
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
                        System.out.println("Студента видалено.");
                    } else {
                        System.out.println("Студента з таким ID не знайдено.");
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