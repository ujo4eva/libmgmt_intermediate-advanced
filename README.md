# 📚 Library Management System (Java CLI)  

A simple command-line application to manage book inventory, track borrow/return operations, and persist data using file I/O.  

---

## 📌 Features  
- **Book Management**: Add/remove books by ISBN.  
- **Search & List**: Search by title/author or view all books.  
- **Borrow/Return**: Track book availability in real-time.  
- **Data Persistence**: Save/load library data to/from a `.txt` file.  

## 📦 Installation  
1. Clone the repository:  
   ```bash
   git clone https://github.com/ujo4eva/libmgmt_intermediate-advanced.git
   ```
2. Compile the Java code:  
   ```bash
   javac -d ./build src/*.java
   ```
3. Run the application:  
   ```bash
   java -cp build Main
   ```

---

## 🧪 Usage  
1. **Start the app**: Run `java -cp build Main` to launch the CLI.  
2. **Commands**: Use these commands to interact with the system:  
   - `add <ISBN> <title> <author>`: Add a book.  
   - `remove <ISBN>`: Remove a book.  
   - `search <keyword>`: Search books by title/author.  
   - `borrow <ISBN>`: Mark a book as borrowed.  
   - `return <ISBN>`: Mark a book as returned.  
   - `list`: View all books and their availability.  

---

## 🚀 Future Enhancements  
- Integrate with a database (e.g., SQLite) for scalable storage.  
- Add a GUI using JavaFX or Swing.  
- Implement user authentication and multiple library branches.  

---

## 📄 License  
This project is licensed under the [MIT License](LICENSE).
