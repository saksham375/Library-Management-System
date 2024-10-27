// Member.java
package com.library.model;

import java.util.Date;

public class Member {
    private int memberId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date membershipDate;
    private String status;

    public Member(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.membershipDate = new Date();
        this.status = "ACTIVE";
    }
    // Add getters and setters similar to Book class
}