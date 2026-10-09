package com.mycompany.librarymanagementsystem;

public class Staff extends Member {

    public Staff(String memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getBorrowLimit() {
        return 6;
    }
}