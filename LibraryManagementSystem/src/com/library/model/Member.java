// Member.java
package com.library.model; // Package declaration for the model class representing a library member

import java.util.Date; // Importing Date class for handling membership dates

public class Member {
    // Instance variables to hold member properties
    private int memberId; // Unique identifier for the member
    private String firstName; // First name of the member
    private String lastName; // Last name of the member
    private String email; // Email address of the member
    private String phone; // Phone number of the member
    private Date membershipDate; // Date when the member joined the library
    private String status; // Status of the member (e.g., ACTIVE, INACTIVE)

    // Constructor to initialize a new Member object with first name, last name, and email
    public Member(String firstName, String lastName, String email) {
        this.firstName = firstName; // Set the first name
        this.lastName = lastName; // Set the last name
        this.email = email; // Set the email
        this.membershipDate = new Date(); // Set the membership date to the current date
        this.status = "ACTIVE"; // Set the initial status to ACTIVE
    }

    // Add getters and setters similar to Book class (not shown here, but would be similar in structure)
}
