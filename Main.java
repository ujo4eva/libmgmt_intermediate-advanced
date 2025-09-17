import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

class Book {

    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    Book(String title, String author, String isbn, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
    }

    Book(Scanner scanner) {
        System.out.println();
        System.out.println("Enter title:");
        title = scanner.nextLine();
        if (title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        System.out.println("Enter author:");
        author = scanner.nextLine();
        if (author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty.");
        }
        System.out.println("Enter ISBN:");
        isbn = scanner.nextLine();
        if (isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty.");
        }
        // Optional: Validate ISBN format (10 or 13 digits)
        if (!isbn.matches("\\d{10}|\\d{13}")) {
            throw new IllegalArgumentException("ISBN must be 10 or 13 digits.");
        }
        isAvailable = true;
        System.out.println("Book successfully created!");
        System.out.println();
    }

    String getTitle() {
        return title;
    }

    String getAuthor() {
        return author;
    }

    String getIsbn() {
        return isbn;
    }

    boolean isAvailable() {
        return isAvailable;
    }

    void toggleAvailability(boolean isBorrowing) {
        isAvailable = !isAvailable;
        System.out.println(
            isBorrowing
                ? "Book borrowed successfully."
                : "Book returned successfully."
        );
        System.out.println();
    }
}

class Library {

    private HashMap<String, Book> library;

    Library() {
        library = new HashMap<>();
    }

    void addBook(Book book) {
        if (library.containsKey(book.getIsbn())) {
            System.out.println("Book already exists in library.");
            System.out.println();
            return;
        }

        library.put(book.getIsbn(), book);
        System.out.println("Book added to library.");
        System.out.println();
    }

    void removeBook(String isbn) {
        if (!library.containsKey(isbn)) {
            System.out.println("Book not found in library.");
            System.out.println();
            return;
        }

        library.remove(isbn);
        System.out.println("Book removed from library.");
        System.out.println();
    }

    void searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            System.out.println("Invalid query.");
            System.out.println();
            return;
        }
        boolean found = false;
        System.out.println("The following books match your query:");
        for (Book book : library.values()) {
            if (
                book.getAuthor().contains(query) ||
                book.getTitle().contains(query) ||
                book.getIsbn().contains(query)
            ) {
                System.out.println(book.getTitle() + " by " + book.getAuthor());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println(
                    "Availability: " +
                        (book.isAvailable() ? "Available" : "Borrowed")
                );
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books matching query found.");
        }
        System.out.println();
    }

    void borrowBook(String isbn) {
        if (!library.containsKey(isbn)) {
            System.out.println("Book not found in library.");
            System.out.println();
            return;
        }
        Book book = library.get(isbn);
        if (!book.isAvailable()) {
            System.out.println("Book is already borrowed.");
            System.out.println();
            return;
        }
        book.toggleAvailability(true);
    }

    void returnBook(String isbn) {
        if (!library.containsKey(isbn)) {
            System.out.println("Book not found in library.");
            System.out.println();
            return;
        }
        Book book = library.get(isbn);
        if (book.isAvailable()) {
            System.out.println("Book is already returned.");
            System.out.println();
            return;
        }
        book.toggleAvailability(false);
    }

    void displayBooks() {
        if (library.isEmpty()) {
            System.out.println("No books in the library.");
            System.out.println();
            return;
        }
        System.out.println("All books in the library:");
        for (Book book : library.values()) {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println(
                "Availability: " +
                    (book.isAvailable() ? "Available" : "Borrowed")
            );
        }
        System.out.println();
    }

    void saveToFile(String filename) throws IOException {
        try (
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename))
        ) {
            for (Book book : library.values()) {
                String title = book.getTitle().replace(",", "\\,");
                String author = book.getAuthor().replace(",", "\\,");
                writer.write(
                    String.format(
                        "%s,%s,%s,%b\n",
                        title,
                        author,
                        book.getIsbn(),
                        book.isAvailable()
                    )
                );
            }
        }
    }

    void loadFromFile(String filename) throws IOException {
        library.clear();
        try (
            BufferedReader reader = new BufferedReader(new FileReader(filename))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String title = parts[0].replace("\\,", ",");
                    String author = parts[1].replace("\\,", ",");
                    String isbn = parts[2];
                    boolean available = Boolean.parseBoolean(parts[3]);
                    library.put(isbn, new Book(title, author, isbn, available));
                } else {
                    System.out.println("Skipping Malformed Line: " + line);
                }
            }
        }
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        String filename = "books.txt";

        // Load books on startup
        try {
            library.loadFromFile(filename);
        } catch (FileNotFoundException e) {
            System.out.println("No book file found. Creating new file.");
            try (
                BufferedWriter writer = new BufferedWriter(
                    new FileWriter(filename)
                )
            ) {
                // Create empty file
            } catch (IOException ex) {
                System.out.println("Error creating file: " + ex.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }

        while (true) {
            System.out.println("==========Library Management==========");
            System.out.println();
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. List All Books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        try {
                            Book book = new Book(scanner);
                            library.addBook(book);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                            System.out.println();
                        }
                        break;
                    case 2:
                        System.out.print("Enter ISBN: ");
                        String isbn = scanner.nextLine();
                        library.removeBook(isbn);
                        break;
                    case 3:
                        System.out.print("Enter search query: ");
                        String query = scanner.nextLine();
                        library.searchBooks(query);
                        break;
                    case 4:
                        System.out.print("Enter ISBN to borrow: ");
                        isbn = scanner.nextLine();
                        library.borrowBook(isbn);
                        break;
                    case 5:
                        System.out.print("Enter ISBN to return: ");
                        isbn = scanner.nextLine();
                        library.returnBook(isbn);
                        break;
                    case 6:
                        library.displayBooks();
                        break;
                    case 7:
                        try {
                            library.saveToFile(filename);
                            System.out.println(
                                "Library data saved successfully."
                            );
                        } catch (IOException e) {
                            System.err.println(
                                "Error saving library data: " + e.getMessage()
                            );
                        }
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice");
                        System.out.println();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                System.out.println();
            }
        }
    }
}
