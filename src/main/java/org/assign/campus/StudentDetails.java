package org.assign.campus;


import java.time.Year;

public class StudentDetails {
    public String schoolName;
    public String deptName;
    public int yearOfStudy;
    public StudentDetails(String schoolName, String deptName, int yearOfStudy) {
        this.schoolName = schoolName;
        this.deptName = deptName;
        this.yearOfStudy = yearOfStudy;

    }
    public static StudentDetails extractStudentDetails(String regNumber )  {
        Year year = Year.now();
        String schoolCode = regNumber.substring(0,1);
        String deptCode = regNumber.substring(1,4);
        int regYear = Integer.parseInt(regNumber.substring(regNumber.length()-4));
        int currentYear = year.getValue();
        int yearOfStudy = currentYear - regYear;
        String schoolName;
        switch(schoolCode){
            case "C":
                schoolName = "Computing";
            case "H":
                schoolName = "HealthScience";
            case "E":
                schoolName = "Engineering";
            case "S":
                schoolName = "Science";
            case "G":
                schoolName = "Geomatics";
            default:
                schoolName = "Unknown";
        }
        String deptName;
        switch(deptCode){
            case "026": deptName = "Computer Science";
            case "027": deptName = "IT";
            case "033": deptName = "Electrical Engineering";
            default: deptName = "Unknown";
        }
        return new StudentDetails(schoolName, deptName, yearOfStudy);
    }


    
}
