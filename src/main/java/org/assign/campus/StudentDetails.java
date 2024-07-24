package org.assign.campus;


import java.time.Year;

public class StudentDetails {
    public final String schoolName;
    public final String deptName;
    public final int yearOfStudy;
    public StudentDetails(String schoolName, String deptName, int yearOfStudy) {
        this.schoolName = schoolName;
        this.deptName = deptName;
        this.yearOfStudy = yearOfStudy;

    }
    public static StudentDetails extractStudentDetails(String regNumber)  {
        Year year = Year.now();
        String schoolCode = regNumber.substring(0,1);
        String deptCode = regNumber.substring(1,4);
        int regYear = Integer.parseInt(regNumber.substring(regNumber.length()-4));
        int currentYear = year.getValue();
        int yearOfStudy = currentYear - regYear;
        String schoolName = switch(schoolCode){
            case "C" -> "Computing";
            case "H" -> "HealthScience";
            case "E" -> "Engineering";
            case "S" -> "Science";
            case "G" -> "Geomatics";
            default  ->"Unknown";
        };
        String deptName = switch (deptCode) {
			case "026" -> "Computer Science";
			case "027" -> "IT";
			case "033" -> "Electrical Engineering";
			default -> "Unknown";
		};
		return new StudentDetails(schoolName, deptName, yearOfStudy);
    }
}
