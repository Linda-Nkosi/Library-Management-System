package com.mycompany.librarymanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The menu. It only asks questions and prints answers;
 * all the rules live in the other classes.
 */
public class LibraryManagementSystem {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Library library = new Library();

        loadSampleData(library);   // delete this line if you want an empty library

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt(input, "Choose an option (1-8): ");

            if (choice == 1) {
                addItemOption(input, library);
            } else if (choice == 2) {
                registerMemberOption(input, library);
            } else if (choice == 3) {
                borrowOption(input, library);
            } else if (choice == 4) {
                returnOption(input, library);
            } else if (choice == 5) {
                searchOption(input, library);
            } else if (choice == 6) {
                displayAvailableOption(library);
            } else if (choice == 7) {
                displayMemberOption(input, library);
            } else if (choice == 8) {
                running = false;
                System.out.println("Goodbye!");
            } else {
                System.out.println("Please choose a number from 1 to 8.");
            }
        }
        input.close();
    }

    private static void showMenu() {
        System.out.println();
        System.out.println(" **********LIBRARY MENU**********");
        System.out.println("1. Add an item to the catalogue");
        System.out.println("2. Register a member");
        System.out.println("3. Borrow an item");
        System.out.println("4. Return an item");
        System.out.println("5. Search by ID or title");
        System.out.println("6. Display all available items");
        System.out.println("7. Display a member's borrowed items");
        System.out.println("8. Exit");
    }

    private static void addItemOption(Scanner input, Library library) {
        System.out.println("Item type: 1. Book  2. DVD  3. Magazine");
        int type = readInt(input, "Choose a type: ");
        if (type < 1 || type > 3) {
            System.out.println("Please choose 1, 2 or 3.");
            return;
        }

        String id = readText(input, "Item ID: ");
        String title = readText(input, "Title: ");

        try {
            LibraryItem item = null;
            if (type == 1) {
                String author = readText(input, "Author: ");
                item = new Book(id, title, author);
            } else if (type == 2) {
                int minutes = readInt(input, "Duration in minutes: ");
                item = new DVD(id, title, minutes);
            } else {
                int issue = readInt(input, "Issue number: ");
                item = new Magazine(id, title, issue);
            }

            if (library.addItem(item)) {
                System.out.println("Added: " + item);
            } else {
                System.out.println("An item with ID " + id + " already exists.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add the item. ID, title and author cannot be empty, "
                    + "and numbers must be greater than 0.");
        }
    }

    private static void registerMemberOption(Scanner input, Library library) {
        System.out.println("Member type: 1. Student  2. Staff");
        int type = readInt(input, "Choose a type: ");
        if (type < 1 || type > 2) {
            System.out.println("Please choose 1 or 2.");
            return;
        }

        String id = readText(input, "Member ID: ");
        String name = readText(input, "Name: ");

        try {
            Member member = null;
            if (type == 1) {
                member = new Student(id, name);
            } else {
                member = new Staff(id, name);
            }

            if (library.registerMember(member)) {
                System.out.println("Registered: " + member);
            } else {
                System.out.println("A member with ID " + id + " already exists.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Could not register the member. ID and name cannot be empty.");
        }
    }

    private static void borrowOption(Scanner input, Library library) {
        String memberId = readText(input, "Member ID: ");
        String itemId = readText(input, "Item ID: ");
        System.out.println(library.borrowItem(memberId, itemId));
    }

    private static void returnOption(Scanner input, Library library) {
        String memberId = readText(input, "Member ID: ");
        String itemId = readText(input, "Item ID: ");
        int daysLate = readInt(input, "Days late (0 if on time): ");
        System.out.println(library.returnItem(memberId, itemId, daysLate));
    }

    private static void searchOption(Scanner input, Library library) {
        System.out.println("Search by: 1. ID  2. Title");
        int type = readInt(input, "Choose: ");

        if (type == 1) {
            String id = readText(input, "Item ID: ");
            LibraryItem item = library.findItemById(id);
            if (item == null) {
                System.out.println("No item with ID " + id + ".");
            } else {
                System.out.println(item);
            }
        } else if (type == 2) {
            String text = readText(input, "Part of the title: ");
            if (text.equals("")) {
                System.out.println("Please type something to search for.");
                return;
            }
            ArrayList<LibraryItem> results = library.searchByTitle(text);
            if (results.isEmpty()) {
                System.out.println("No items found.");
            } else {
                System.out.println(results.size() + " item(s) found:");
                for (LibraryItem item : results) {
                    System.out.println(item);
                }
            }
        } else {
            System.out.println("Please choose 1 or 2.");
        }
    }

    private static void displayAvailableOption(Library library) {
        ArrayList<LibraryItem> available = library.getAvailableItems();
        if (available.isEmpty()) {
            System.out.println("No items are available right now.");
        } else {
            System.out.println("Available items:");
            for (LibraryItem item : available) {
                System.out.println(item);
            }
        }
    }

    private static void displayMemberOption(Scanner input, Library library) {
        String memberId = readText(input, "Member ID: ");
        Member member = library.findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found: " + memberId);
            return;
        }

        System.out.println(member);

        ArrayList<LibraryItem> borrowed = member.getBorrowedItems();
        if (borrowed.isEmpty()) {
            System.out.println("Currently borrowing: nothing");
        } else {
            System.out.println("Currently borrowing:");
            for (LibraryItem item : borrowed) {
                System.out.println("  " + item);
            }
        }

        ArrayList<String> history = member.getHistory();
        if (!history.isEmpty()) {
            System.out.println("History:");
            for (String entry : history) {
                System.out.println("  " + entry);
            }
        }
    }

    // ---------- Input helpers ----------

    // Keeps asking until the user types a real whole number, so it never crashes
    private static int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("That is not a number. Try again.");
            }
        }
    }

    private static String readText(Scanner input, String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    // ---------- Sample data to test ----------

    private static void loadSampleData(Library library) {
        library.addItem(new Book("B1", "Clean Code", "Robert Martin"));
        library.addItem(new Book("B2", "Head First Java", "Kathy Sierra"));
        library.addItem(new DVD("D1", "Inception", 148));
        library.addItem(new DVD("D2", "The Matrix", 136));
        library.addItem(new Magazine("M1", "Tech Weekly", 42));

        library.registerMember(new Student("S1", "Thandi"));
        library.registerMember(new Student("S2", "Sipho"));
        library.registerMember(new Staff("T1", "Lerato"));
    }
}