/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagementsystem;

/**
 *
 * @author docto
 */
public class Student extends Member{
   
    public Student(String memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getBorrowLimit() {
        return 3;
    }
}

