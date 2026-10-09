/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagementsystem;

/**
 *
 * @author docto
 */
public class Book extends LibraryItem{
    
    private static final double FEE_PER_DAY = 2.0;   // R2 per day late
    private static final int LOAN_DAYS = 14;

    
    private String author;
    public Book(String id,String title,String author){
        super(id,title);
        if (author == null || author.trim().equals("")) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        this.author = author.trim();
    }
    public String getAuthor(){
        return this.author;
    }
    
    @Override
   public double calculateLateFee(int daysLate){
         if (daysLate < 0) {
            return 0;
        }
        return daysLate * FEE_PER_DAY;
   }
    @Override
    public int getLoanPeriodDays() {
        return LOAN_DAYS;
    };

    @Override
    public String toString() {
        return super.toString() + " | Book by " + author;
    }
}
