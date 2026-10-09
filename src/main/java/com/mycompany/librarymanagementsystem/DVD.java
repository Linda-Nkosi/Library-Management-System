/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagementsystem;

/**
 *
 * @author docto
 */
public class DVD extends LibraryItem {
    
     private static final double FEE_PER_DAY = 5.0;   // R5 per day late
    private static final int LOAN_DAYS = 7;

    private int durationMinutes;
    
    public DVD(String id,String title,int durationMinutes){
        super(id,title);
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0");
        }
        this.durationMinutes=durationMinutes;
    }
    public int getDurationMinutes(){
        return this.durationMinutes;
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
        return super.toString() + " | DVD, " + durationMinutes + " min";
    }
}
