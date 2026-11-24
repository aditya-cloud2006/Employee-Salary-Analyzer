import java.util.Scanner;

/*
 * Employee Salary Analyzer
 * Core Java + DSA (arrays, sorting, searching) only.
 * No ArrayList / HashMap / Collections.sort used.
 *
 * Run:  javac Main.java
 *       java Main
 */

// ------------------------------------------------------------
// Employee: simple data holder
// ------------------------------------------------------------
class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    public void display() {
        System.out.printf("%-6d %-20s %-15s %12.2f%n", id, name, department, salary);
    }
}

// ------------------------------------------------------------
// SalaryAnalyzer: all operations (sorting, searching, analysis)
// ------------------------------------------------------------
class SalaryAnalyzer {
    private static final int MAX = 100;
    private Employee[] emp = new Employee[MAX];
    private int count = 0;

    // ---------------- Basic helpers ----------------

    public int getCount() { return count; }

    public boolean idExists(int id) {
        for (int i = 0; i < count; i++) {
            if (emp[i].getId() == id) return true;
        }
        return false;
    }

    // Returns false if array is full or id is duplicate
    public boolean addEmployee(Employee e) {
        if (count >= MAX) {
            System.out.println("Array is full! Cannot add more than " + MAX + " employees.");
            return false;
        }
        if (idExists(e.getId())) {
            System.out.println("Employee ID " + e.getId() + " already exists!");
            return false;
        }
        emp[count++] = e;
        return true;
    }

    private void printHeader() {
        System.out.println("---------------------------------------------------------");
        System.out.printf("%-6s %-20s %-15s %12s%n", "ID", "Name", "Department", "Salary");
        System.out.println("---------------------------------------------------------");
    }

    private void printTable(Employee[] arr, int n) {
        printHeader();
        for (int i = 0; i < n; i++) arr[i].display();
        System.out.println("---------------------------------------------------------");
    }

    public void displayAll() {
        if (count == 0) {
            System.out.println("No employees to display.");
            return;
        }
        printTable(emp, count);
    }

    // ---------------- SORTING ----------------

    // Bubble Sort by salary - Time: O(n^2), Space: O(1)
    public void bubbleSortBySalary(boolean ascending) {
        for (int i = 0; i < count - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < count - i - 1; j++) {
                boolean needSwap = ascending
                        ? emp[j].getSalary() > emp[j + 1].getSalary()
                        : emp[j].getSalary() < emp[j + 1].getSalary();
                if (needSwap) {
                    Employee temp = emp[j];
                    emp[j] = emp[j + 1];
                    emp[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // already sorted
        }
    }

    // Selection Sort by name (alphabetical) - Time: O(n^2), Space: O(1)
    public void selectionSortByName() {
        for (int i = 0; i < count - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < count; j++) {
                if (emp[j].getName().compareToIgnoreCase(emp[minIdx].getName()) < 0) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                Employee temp = emp[i];
                emp[i] = emp[minIdx];
                emp[minIdx] = temp;
            }
        }
    }

    // Insertion Sort by id - Time: O(n^2) worst, O(n) best, Space: O(1)
    public void insertionSortById() {
        for (int i = 1; i < count; i++) {
            Employee key = emp[i];
            int j = i - 1;
            while (j >= 0 && emp[j].getId() > key.getId()) {
                emp[j + 1] = emp[j];
                j--;
            }
            emp[j + 1] = key;
        }
    }

    // Merge Sort by salary - Time: O(n log n), Space: O(n)
    private void mergeSortBySalary(Employee[] a, int left, int right, boolean ascending) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSortBySalary(a, left, mid, ascending);
        mergeSortBySalary(a, mid + 1, right, ascending);
        merge(a, left, mid, right, ascending);
    }

    private void merge(Employee[] a, int left, int mid, int right, boolean ascending) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        Employee[] L = new Employee[n1];
        Employee[] R = new Employee[n2];
        for (int i = 0; i < n1; i++) L[i] = a[left + i];
        for (int j = 0; j < n2; j++) R[j] = a[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            boolean takeLeft = ascending
                    ? L[i].getSalary() <= R[j].getSalary()
                    : L[i].getSalary() >= R[j].getSalary();
            if (takeLeft) a[k++] = L[i++];
            else a[k++] = R[j++];
        }
        while (i < n1) a[k++] = L[i++];
        while (j < n2) a[k++] = R[j++];
    }

    // ---------------- SEARCHING ----------------

    // Linear Search by name (case-insensitive) - Time: O(n)
    public void searchByName(String name) {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (emp[i].getName().equalsIgnoreCase(name)) {
                if (!found) printHeader();
                emp[i].display();
                found = true;
            }
        }
        if (found) System.out.println("---------------------------------------------------------");
        else System.out.println("No employee found with name: " + name);
    }

    // Binary Search by id - Time: O(log n). Array is sorted by id first.
    public void searchById(int id) {
        insertionSortById(); // binary search needs sorted data
        int low = 0, high = count - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (emp[mid].getId() == id) {
                printHeader();
                emp[mid].display();
                System.out.println("---------------------------------------------------------");
                return;
            } else if (emp[mid].getId() < id) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("No employee found with ID: " + id);
    }

    // Salary range search using array traversal - Time: O(n)
    public void searchBySalaryRange(double min, double max) {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            double s = emp[i].getSalary();
            if (s >= min && s <= max) {
                if (!found) printHeader();
                emp[i].display();
                found = true;
            }
        }
        if (found) System.out.println("---------------------------------------------------------");
        else System.out.println("No employee found in salary range " + min + " - " + max);
    }

    // ---------------- ANALYSIS ----------------

    // Highest, lowest, average, total, second highest - Time: O(n)
    public void showStatistics() {
        if (count == 0) {
            System.out.println("No employees available.");
            return;
        }
        Employee highest = emp[0];
        Employee lowest = emp[0];
        double total = 0;

        for (int i = 0; i < count; i++) {
            if (emp[i].getSalary() > highest.getSalary()) highest = emp[i];
            if (emp[i].getSalary() < lowest.getSalary()) lowest = emp[i];
            total += emp[i].getSalary();
        }
        double average = total / count;

        // Second highest distinct salary
        boolean hasSecond = false;
        double second = 0;
        for (int i = 0; i < count; i++) {
            double s = emp[i].getSalary();
            if (s < highest.getSalary() && (!hasSecond || s > second)) {
                second = s;
                hasSecond = true;
            }
        }

        int above = 0, below = 0;
        for (int i = 0; i < count; i++) {
            if (emp[i].getSalary() > average) above++;
            else if (emp[i].getSalary() < average) below++;
        }

        System.out.println("========== SALARY STATISTICS ==========");
        System.out.printf("Total Employees      : %d%n", count);
        System.out.printf("Total Salary Expense : %.2f%n", total);
        System.out.printf("Average Salary       : %.2f%n", average);
        System.out.printf("Highest Paid         : %s (%.2f)%n", highest.getName(), highest.getSalary());
        System.out.printf("Lowest Paid          : %s (%.2f)%n", lowest.getName(), lowest.getSalary());
        if (hasSecond) System.out.printf("Second Highest Salary: %.2f%n", second);
        else System.out.println("Second Highest Salary: N/A (all salaries are equal)");
        System.out.printf("Above Average Count  : %d%n", above);
        System.out.printf("Below Average Count  : %d%n", below);
        System.out.println("=======================================");
    }

    // Department-wise total and average - Time: O(n * d), d = number of departments
    public void departmentReport() {
        if (count == 0) {
            System.out.println("No employees available.");
            return;
        }
        String[] depts = new String[MAX];
        double[] totals = new double[MAX];
        int[] counts = new int[MAX];
        int dCount = 0;

        for (int i = 0; i < count; i++) {
            int idx = -1;
            for (int j = 0; j < dCount; j++) {
                if (depts[j].equalsIgnoreCase(emp[i].getDepartment())) {
                    idx = j;
                    break;
                }
            }
            if (idx == -1) {
                depts[dCount] = emp[i].getDepartment();
                idx = dCount++;
            }
            totals[idx] += emp[i].getSalary();
            counts[idx]++;
        }

        System.out.println("---------------------------------------------------------");
        System.out.printf("%-15s %10s %15s %15s%n", "Department", "Employees", "Total Salary", "Avg Salary");
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < dCount; i++) {
            System.out.printf("%-15s %10d %15.2f %15.2f%n", depts[i], counts[i], totals[i], totals[i] / counts[i]);
        }
        System.out.println("---------------------------------------------------------");
    }

    // Top N highest paid: copy array, Merge Sort (descending), print first N
    public void topN(int n) {
        if (count == 0) {
            System.out.println("No employees available.");
            return;
        }
        if (n > count) n = count;
        Employee[] copy = new Employee[count];
        for (int i = 0; i < count; i++) copy[i] = emp[i];
        mergeSortBySalary(copy, 0, count - 1, false);
        System.out.println("Top " + n + " highest paid employees:");
        printTable(copy, n);
    }
}

// ------------------------------------------------------------
// Main: menu-driven program
// ------------------------------------------------------------
public class Main {
    private static Scanner sc = new Scanner(System.in);

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Input cannot be empty!");
        }
    }

    private static void loadSampleData(SalaryAnalyzer a) {
        a.addEmployee(new Employee(105, "Rahul Sharma", "IT", 65000));
        a.addEmployee(new Employee(101, "Priya Verma", "HR", 48000));
        a.addEmployee(new Employee(110, "Amit Patel", "Finance", 72000));
        a.addEmployee(new Employee(103, "Neha Singh", "IT", 81000));
        a.addEmployee(new Employee(108, "Karan Mehta", "Sales", 39000));
        a.addEmployee(new Employee(102, "Sneha Joshi", "HR", 52000));
        a.addEmployee(new Employee(107, "Vikas Gupta", "Finance", 91000));
        a.addEmployee(new Employee(104, "Anjali Rao", "Sales", 45000));
        a.addEmployee(new Employee(109, "Rohit Yadav", "IT", 58000));
        a.addEmployee(new Employee(106, "Pooja Nair", "Marketing", 61000));
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== EMPLOYEE SALARY ANALYZER =====");
        System.out.println(" 1. Add Employee");
        System.out.println(" 2. Display All Employees");
        System.out.println(" 3. Sort by Salary (Asc/Desc)");
        System.out.println(" 4. Sort by Name");
        System.out.println(" 5. Sort by ID");
        System.out.println(" 6. Search by Name (Linear Search)");
        System.out.println(" 7. Search by ID (Binary Search)");
        System.out.println(" 8. Search by Salary Range");
        System.out.println(" 9. Show Salary Statistics");
        System.out.println("10. Department-wise Report");
        System.out.println("11. Top N Highest Paid Employees");
        System.out.println("12. Exit");
        System.out.println("====================================");
    }

    public static void main(String[] args) {
        SalaryAnalyzer analyzer = new SalaryAnalyzer();
        loadSampleData(analyzer);
        System.out.println("10 sample employees loaded.");

        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: {
                    int id = readInt("Enter ID: ");
                    if (id <= 0) {
                        System.out.println("ID must be a positive number!");
                        break;
                    }
                    if (analyzer.idExists(id)) {
                        System.out.println("Employee ID " + id + " already exists!");
                        break;
                    }
                    String name = readString("Enter Name: ");
                    String dept = readString("Enter Department: ");
                    double salary = readDouble("Enter Salary: ");
                    if (salary < 0) {
                        System.out.println("Salary cannot be negative!");
                        break;
                    }
                    if (analyzer.addEmployee(new Employee(id, name, dept, salary))) {
                        System.out.println("Employee added successfully.");
                    }
                    break;
                }
                case 2:
                    analyzer.displayAll();
                    break;
                case 3: {
                    System.out.println("1. Ascending  2. Descending");
                    int ord = readInt("Choose order: ");
                    if (ord != 1 && ord != 2) {
                        System.out.println("Invalid order choice!");
                        break;
                    }
                    analyzer.bubbleSortBySalary(ord == 1);
                    System.out.println("Sorted by salary (Bubble Sort):");
                    analyzer.displayAll();
                    break;
                }
                case 4:
                    analyzer.selectionSortByName();
                    System.out.println("Sorted by name (Selection Sort):");
                    analyzer.displayAll();
                    break;
                case 5:
                    analyzer.insertionSortById();
                    System.out.println("Sorted by ID (Insertion Sort):");
                    analyzer.displayAll();
                    break;
                case 6: {
                    String name = readString("Enter name to search: ");
                    analyzer.searchByName(name);
                    break;
                }
                case 7: {
                    int id = readInt("Enter ID to search: ");
                    analyzer.searchById(id);
                    break;
                }
                case 8: {
                    double min = readDouble("Enter minimum salary: ");
                    double max = readDouble("Enter maximum salary: ");
                    if (min > max) {
                        System.out.println("Minimum cannot be greater than maximum!");
                        break;
                    }
                    analyzer.searchBySalaryRange(min, max);
                    break;
                }
                case 9:
                    analyzer.showStatistics();
                    break;
                case 10:
                    analyzer.departmentReport();
                    break;
                case 11: {
                    int n = readInt("Enter N: ");
                    if (n <= 0) {
                        System.out.println("N must be greater than 0!");
                        break;
                    }
                    analyzer.topN(n);
                    break;
                }
                case 12:
                    System.out.println("Thank you for using Employee Salary Analyzer. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 12.");
            }
        } while (choice != 12);

        sc.close();
    }
}
