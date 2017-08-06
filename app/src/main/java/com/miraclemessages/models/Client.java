package com.miraclemessages.models;

/**
 * Created by shobhit on 2017-08-06.
 */

//Class to store data to be sent to Firebase
public class Client {

    public String client_contact_info;
    public String client_current_city;
    public String client_dob;
    public String client_hometown;
    public String client_name;
    public String client_years_homeless;

    public String created_at;

    public String recipient_dob;
    public String recipient_last_location;
    public String recipient_last_seen;
    public String recipient_name;
    public String recipient_other_info;
    public String recipient_relationship;

    public String volunteer_email;
    public String volunteer_location;
    public String volunteer_name;
    public String volunteer_phone;
    public String uploadedURL;

    public Client(String client_contact_info,
                  String client_current_city,
                  String client_dob,
                  String client_hometown,
                  String client_name,
                  String client_years_homeless,
                  String created_at,
                  String recipient_dob,
                  String recipient_last_location,
                  String recipient_last_seen,
                  String recipient_name,
                  String recipient_other_info,
                  String recipient_relationship,
                  String volunteer_email,
                  String volunteer_location,
                  String volunteer_name,
                  String volunteer_phone,
                  String uploadedURL) {
        this.client_contact_info = client_contact_info;
        this.client_current_city = client_current_city;
        this.client_dob = client_dob;
        this.client_hometown = client_hometown;
        this.client_name = client_name;
        this.client_years_homeless = client_years_homeless;
        this.created_at = created_at;
        this.recipient_dob = recipient_dob;
        this.recipient_last_location = recipient_last_location;
        this.recipient_last_seen = recipient_last_seen;
        this.recipient_name = recipient_name;
        this.recipient_other_info = recipient_other_info;
        this.recipient_relationship = recipient_relationship;
        this.volunteer_email = volunteer_email;
        this.volunteer_location = volunteer_location;
        this.volunteer_name = volunteer_name;
        this.volunteer_phone = volunteer_phone;
        this.uploadedURL = uploadedURL;
    }
}
