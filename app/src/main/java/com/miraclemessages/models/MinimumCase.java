package com.miraclemessages.models;

/**
 * Created by James Wu on 5/18/2017.
 */

public class MinimumCase {
    public String submitted;
    public String createdBy;
    public String caseStatus;
    public String messageStatus;
    public String nextStep;
    public String pubVideo; //YouTube Video URL
    public String youtubeCover;
    public String privVideo; //S3 Video URL
    //need public String source here
    public String photo; //URL to firebase storage location
    public String firstName; //Sender first name
    public String middleName; //Sender middle name
    public String lastName; //Sender last name
    public String currentCity;
    public String currentState; //Full state name
    public String currentCountry; //Country code
    public String homeCity;
    public String homeState; //Full state name
    public String homeCountry; //Country code
    public int age; //Should be digit
    public boolean ageApproximate;
    public int yearsHomeless;
    public boolean detectives;

    //Need constructor

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
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
