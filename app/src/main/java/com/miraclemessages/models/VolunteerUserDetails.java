package com.miraclemessages.models;

/**
 * Volunteer user object details.
 * Created by shobhit on 2017-09-15.
 */

public class VolunteerUserDetails {
    private String name;
    private String email;
    private String phone;
    private String location;

    public VolunteerUserDetails() {
    }

    public VolunteerUserDetails(String name, String email, String phone, String location) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "VolunteerUserDetails{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
