package com.miraclemessages.models;

import java.util.Date;

/**
 * Created by James Wu on 5/18/2017.
 */

public class Case {
    private Integer age; //Should be digit
    private Boolean ageApproximate;
    private String caseStatus;
    private String currentCity;
    private String currentState; //Full state name
    private String currentCountry; //Country code
    private Integer detectiveCount;
    private String firstName; //Sender first name
    private String homeCity;
    private String homeCountry; //Country code
    private String homeState; //Full state name
    private String lastName; //Sender last name


    private String messageStatus;
    private String nextStep;
    private String pubVideo; //YouTube Video URL

    private String youtubeCover;

    private Date submitted;
    private String createdBy;
    private String privVideo; //S3 Video URL
    //need public String source here
    private String photo; //URL to firebase storage location
    private String middleName; //Sender middle name
    private int yearsHomeless;
    private boolean detectives;

    //Need constructor

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date submitted) {
        this.submitted = submitted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public String getPubVideo() {
        return pubVideo;
    }

    public void setPubVideo(String pubVideo) {
        this.pubVideo = pubVideo;
    }

    public String getYoutubeCover() {
        return youtubeCover;
    }

    public void setYoutubeCover(String youtubeCover) {
        this.youtubeCover = youtubeCover;
    }

    public String getPrivVideo() {
        return privVideo;
    }

    public void setPrivVideo(String privVideo) {
        this.privVideo = privVideo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(String currentCountry) {
        this.currentCountry = currentCountry;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getHomeState() {
        return homeState;
    }

    public void setHomeState(String homeState) {
        this.homeState = homeState;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAgeApproximate() {
        return ageApproximate;
    }

    public void setAgeApproximate(boolean ageApproximate) {
        this.ageApproximate = ageApproximate;
    }

    public int getYearsHomeless() {
        return yearsHomeless;
    }

    public void setYearsHomeless(int yearsHomeless) {
        this.yearsHomeless = yearsHomeless;
    }

    public boolean isDetectives() {
        return detectives;
    }

    public void setDetectives(boolean detectives) {
        this.detectives = detectives;
    }
}
