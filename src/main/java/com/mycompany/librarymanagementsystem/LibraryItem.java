/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagementsystem;

/**
 *
 * @author docto
 */
public abstract class LibraryItem {
    private String id;
    private String title;
    private boolean available;
    
    public LibraryItem(String id,String title){
        
         if (id == null || id.trim().equals("")) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (title == null || title.trim().equals("")) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
            this.id=id;
            this.title=title;
            
}
    public String getId(){
        return this.id;
    }
    public String getTitle(){
        return this.title;
    }
    public boolean isAvailable(){
        return this.available;
    }
    public boolean borrowItem(){
         if (available) {
            available = false;
            return true;
        }
        return false;   // already on loan, nothing changes
    }
         
    public boolean returnItem(){
        if (!available) {
            available = true;
            return true;
        }
        return false;   // was never borrowed, nothing changes
    }
           
    public abstract double calculateLateFee(int daysLate);
        
    public abstract int getLoanPeriodDays();

    @Override
    public String toString(){
        String status;
        if (available) {
            status = "Available";
        } else {
            status = "On loan";
        }
        return id + " | " + title + " | " + status;
    }
}
