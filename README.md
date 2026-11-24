# Employee Salary Analyzer

A console-based Employee Salary Analyzer built using **Core Java** and fundamental **Data Structures and Algorithms (DSA)**.

> ⚠️ **Design Principle**: Built purely using native arrays and standard DSA algorithms without high-level collections like `ArrayList`, `HashMap`, or `Collections.sort`.

---

## 🚀 Features

- **Employee Management**: Add employees with unique ID, name, department, and salary.
- **Sorting Algorithms**:
  - **Bubble Sort**: Sort employees by salary (Ascending / Descending) with early-exit optimization ($O(n^2)$).
  - **Selection Sort**: Sort employees alphabetically by name ($O(n^2)$).
  - **Insertion Sort**: Sort employees by ID ($O(n^2)$ worst, $O(n)$ best).
  - **Merge Sort**: Divide-and-conquer algorithm to sort salaries for Top-N ranking ($O(n \log n)$).
- **Searching Algorithms**:
  - **Linear Search**: Case-insensitive search by employee name ($O(n)$).
  - **Binary Search**: Fast lookup by ID on sorted data ($O(\log n)$).
  - **Salary Range Search**: Find all employees within a specified salary bracket ($O(n)$).
- **Analytics & Reporting**:
  - **Salary Statistics**: Computes Total Salary, Average Salary, Highest Paid, Lowest Paid, Second Highest Salary, and Above/Below average distribution ($O(n)$).
  - **Department-wise Report**: Group-by analysis without HashMaps, displaying total and average salary per department ($O(n \times d)$).
  - **Top-N Earners**: View top $N$ highest-earning employees ($O(n \log n)$).

---

## 🛠️ Tech Stack & Concepts

- **Language**: Java
- **Data Structures**: Fixed-size Object Arrays (`Employee[]`)
- **Algorithms**: Bubble Sort, Selection Sort, Insertion Sort, Merge Sort, Linear Search, Binary Search

---

## 💻 How to Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/aditya-cloud2006/Employee-Salary-Analyzer.git
   cd Employee-Salary-Analyzer
   ```

2. **Compile the code:**
   ```bash
   javac Main.java
   ```

3. **Run the program:**
   ```bash
   java Main
   ```

---

## 👤 Author
- **Aditya Kumar** ([@aditya-cloud2006](https://github.com/aditya-cloud2006))
