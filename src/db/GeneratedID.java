package db;

public class GeneratedID {
    private static Long departmentID = 0L;
    private static Long doctorID = 0L;
    private static Long hospitalID = 0L;
    private static Long patientID = 0L;

    public static Long setDepartmentID(){
        return ++departmentID;
    }

    public static Long setDoctorID(){
        return ++doctorID;
    }

    public static Long setHospitalID(){
        return ++hospitalID;
    }

    public static Long setPatientID(){
        return ++patientID;
    }

}
