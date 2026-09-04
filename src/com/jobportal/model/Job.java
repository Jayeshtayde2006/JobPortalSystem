package com.jobportal.model;

public class Job {

    private int id;
    private int employerId;
    private String jobTitle;
    private String companyName;
    private String location;
    private String salary;
    private String jobType;
    private String description;
    private String requiredSkills;

    public Job() {
    }

    public Job(int employerId, String jobTitle, String companyName,
               String location, String salary, String jobType,
               String description, String requiredSkills) {

        this.employerId = employerId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.salary = salary;
        this.jobType = jobType;
        this.description = description;
        this.requiredSkills = requiredSkills;
    }

    public Job(int id, int employerId, String jobTitle, String companyName,
               String location, String salary, String jobType,
               String description, String requiredSkills) {

        this.id = id;
        this.employerId = employerId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.salary = salary;
        this.jobType = jobType;
        this.description = description;
        this.requiredSkills = requiredSkills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
}