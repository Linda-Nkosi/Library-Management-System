/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagementsystem;

/**
 *
 * @author docto
 */
public class Magazine extends LibraryItem{
    
    private static final double FEE_PER_DAY = 1.0;   // R1 per day late
    private static final int LOAN_DAYS = 3;
    
    private int issueNumber;
    
    public Magazine(String id,String title,int issueNumber){
        super(id,title);
         if (issueNumber <= 0) {
            throw new IllegalArgumentException("Issue number must be greater than 0");
        }
        this.issueNumber = issueNumber;
    }
     public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public double calculateLateFee(int daysLate) {
        if (daysLate < 0) {
            return 0;
        }
        return daysLate * FEE_PER_DAY;
    }

    @Override
    public int getLoanPeriodDays() {
        return LOAN_DAYS;
    }

    @Override
    public String toString() {
        return super.toString() + " | Magazine, issue " + issueNumber;
    }
}
