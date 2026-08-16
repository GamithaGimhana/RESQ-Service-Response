package com.resq.response.model;

public class TeamMember {
    private String name;
    private String role;
    private String contactNumber;

    public TeamMember() {
    }

    public TeamMember(String name, String role, String contactNumber) {
        this.name = name;
        this.role = role;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
