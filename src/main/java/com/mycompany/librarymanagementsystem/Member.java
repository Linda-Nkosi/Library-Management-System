/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagementsystem;
import java.util.ArrayList;
/**
 *
 * @author docto
 */
public abstract class Member {
    private String memberID;
    private String name;
    private ArrayList<LibraryItem>borrowedItems;
    private ArrayList<String>history;
    
    public Member(String memberID,String name){
         if (memberID == null || memberID.trim().equals("")) {
            throw new IllegalArgumentException("Member ID cannot be empty");
        }
        if (name == null || name.trim().equals("")) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.memberID = memberID.trim();
        this.name = name.trim();
        this.borrowedItems = new ArrayList<LibraryItem>();
        this.history = new ArrayList<String>();
    }
     public String getMemberId() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    // Each child decides its own limit (polymorphism)
    public abstract int getBorrowLimit();

    public boolean canBorrow() {
        return borrowedItems.size() < getBorrowLimit();
    }

    public void addBorrowedItem(LibraryItem item) {
        borrowedItems.add(item);
        history.add("Borrowed: " + item.getTitle());
    }

    public boolean removeBorrowedItem(LibraryItem item) {
        boolean removed = borrowedItems.remove(item);
        if (removed) {
            history.add("Returned: " + item.getTitle());
        }
        return removed;
    }

    // Return COPIES so outside code cannot edit the real lists
    public ArrayList<LibraryItem> getBorrowedItems() {
        return new ArrayList<LibraryItem>(borrowedItems);
    }

    public ArrayList<String> getHistory() {
        return new ArrayList<String>(history);
    }

    @Override
    public String toString() {
        return memberID + " | " + name + " | " + getClass().getSimpleName()
                + " (limit " + getBorrowLimit() + ")";
    }
}
