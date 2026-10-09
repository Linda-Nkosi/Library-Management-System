package com.mycompany.librarymanagementsystem;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Owns the two HashMaps and holds all the borrow/return rules.
 * IDs are case-sensitive: "B1" and "b1" are different keys.
 */
public class Library {

    private HashMap<String, LibraryItem> catalogue;   // item ID -> item
    private HashMap<String, Member> members;          // member ID -> member

    public Library() {
        catalogue = new HashMap<String, LibraryItem>();
        members = new HashMap<String, Member>();
    }

   

    public boolean addItem(LibraryItem item) {
        if (item == null || catalogue.containsKey(item.getId())) {
            return false;   // duplicate ID: do NOT overwrite the existing item
        }
        catalogue.put(item.getId(), item);
        return true;
    }

    public boolean registerMember(Member member) {
        if (member == null || members.containsKey(member.getMemberId())) {
            return false;
        }
        members.put(member.getMemberId(), member);
        return true;
    }

    public LibraryItem findItemById(String id) {
        return catalogue.get(id);   // null if there is no such ID
    }

    public Member findMemberById(String id) {
        return members.get(id);     // null if there is no such ID
    }

    // ---------- Borrow and return ----------

    public String borrowItem(String memberId, String itemId) {
        Member member = members.get(memberId);
        if (member == null) {
            return "Member not found: " + memberId;
        }

        LibraryItem item = catalogue.get(itemId);
        if (item == null) {
            return "Item not found: " + itemId;
        }

        if (!item.isAvailable()) {
            return "Sorry, \"" + item.getTitle() + "\" is already on loan.";
        }

        if (!member.canBorrow()) {
            return member.getName() + " has reached the limit of "
                    + member.getBorrowLimit() + " items.";
        }

        item.borrowItem();
        member.addBorrowedItem(item);
        return member.getName() + " borrowed \"" + item.getTitle()
                + "\". Due in " + item.getLoanPeriodDays() + " days.";
    }

    public String returnItem(String memberId, String itemId, int daysLate) {
        Member member = members.get(memberId);
        if (member == null) {
            return "Member not found: " + memberId;
        }

        LibraryItem item = catalogue.get(itemId);
        if (item == null) {
            return "Item not found: " + itemId;
        }

        if (daysLate < 0) {
            return "Days late cannot be negative.";
        }

        if (!member.removeBorrowedItem(item)) {
            return member.getName() + " does not have \"" + item.getTitle() + "\" on loan.";
        }

        item.returnItem();

        // Polymorphism: we do not care whether this is a Book, DVD or Magazine
        double fee = item.calculateLateFee(daysLate);
        if (fee == 0) {
            return "\"" + item.getTitle() + "\" returned on time. No late fee.";
        }
        return "\"" + item.getTitle() + "\" returned " + daysLate
                + " day(s) late. Late fee: R" + String.format("%.2f", fee);
    }

    // ---------- Searching and listing ----------

    public ArrayList<LibraryItem> searchByTitle(String text) {
        ArrayList<LibraryItem> results = new ArrayList<LibraryItem>();
        if (text == null) {
            return results;
        }
        String lookingFor = text.trim().toLowerCase();
        for (LibraryItem item : catalogue.values()) {
            if (item.getTitle().toLowerCase().contains(lookingFor)) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<LibraryItem> getAvailableItems() {
        ArrayList<LibraryItem> available = new ArrayList<LibraryItem>();
        for (LibraryItem item : catalogue.values()) {
            if (item.isAvailable()) {
                available.add(item);
            }
        }
        return available;
    }
}