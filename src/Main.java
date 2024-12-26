import db.GeneratedID;
import enums.Gender;
import model.Department;
import model.Doctor;
import model.Hospital;
import model.Patient;
import service.DepartmentService;
import service.DoctorService;
import service.HospitalService;
import service.Impl.DepartmentServiceImpl;
import service.Impl.DoctorServiceImpl;
import service.Impl.HospitalServiceImpl;
import service.Impl.PatientServiceImpl;
import service.PatientService;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static HospitalService hospitalService = new HospitalServiceImpl();
    static PatientService patientService = new PatientServiceImpl();
    static DepartmentService departmentService = new DepartmentServiceImpl();
    static DoctorService doctorService = new DoctorServiceImpl();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean logOut = true;
        while (logOut) {
            System.out.println("""
                    1. hospital
                    2. patient 
                    3. department
                    4. doctor
                    """);
            switch (scanner.nextLine()) {
                case "1" -> {
                    workWithHospital();
                }
                case "2" -> {
                    workWithPatient();
                }
                case "3" ->{
                    workWithDepartment();
                }
                case "4" ->{
                    workWithDoctor();
                }
                default -> {
                    System.out.println("Can't find this option.");
                }
            }
        }
    }

    public static void workWithHospital() {
        boolean exit = true;
        while (exit) {
            System.out.println("""
                    -------- Hospital --------
                    1. add hospital
                    2. find by ID
                    3. get all hospital
                    4. get All Patient From Hospital
                    5. delete Hospital By Id
                    6. get All Hospital By Address
                    7. log out
                    """);
            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.println(hospitalService.addHospital(new Hospital(GeneratedID.setHospitalID(), "Respublikanskiy", "Moskovskiy")));
                    hospitalService.addHospital(new Hospital(GeneratedID.setHospitalID(), "KudretGoz", "C.Aitmatova"));
                }
                case "2" -> {
                    System.out.println(hospitalService.findHospitalById(2L));
                }
                case "3" -> {
                    System.out.println(hospitalService.getAllHospital());
                }
                case "4" -> {
                    System.out.println(hospitalService.getAllPatientFromHospital(2L));
                }
                case "5" -> {
                    System.out.println(hospitalService.deleteHospitalById(2L));
                }
                case "6" -> {
                    System.out.println("Enter the address: ");
                    System.out.println(hospitalService.getAllHospitalByAddress(scanner.nextLine()));
                }
                case "7" -> {
                    exit = false;
                }
            }
        }
    }

    public static void workWithPatient() {
        boolean exit = true;
        while (exit) {
            System.out.println("""
                    1. add to hospital by hospitalID
                    2. add Patients To Hospital
                    3. get patient by ID
                    4. get patient by age
                    5. sort patient by age
                    6. update by ID
                    7. remove by ID
                    8. exit
                    """);
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                throw new RuntimeException("Please, write the number");
            }
            switch (choice) {
                case 1 -> {
                    System.out.println(patientService.add(1L, new Patient(GeneratedID.setPatientID(), "Akmaral", "Muktarova", 21, Gender.FEMALE)));
                    patientService.add(2L, new Patient(GeneratedID.setPatientID(), "Uluk", "Saparbekov", 20, Gender.MALE));
                }
                case 2 -> {
                    List<Patient> patientList = new ArrayList<>(Arrays.asList(new Patient(GeneratedID.setPatientID(), "Kameliya", "Arnalieva", 19, Gender.FEMALE),
                            new Patient(GeneratedID.setPatientID(), "Temir", "Temirbelov", 18, Gender.MALE)));
                    System.out.println(patientService.addPatientsToHospital(2L, patientList));
                }
                case 3 -> {
                    System.out.println(patientService.getPatientById(2L));
                }
                case 4 -> {
                    System.out.println(patientService.getPatientByAge());
                }
                case 5 -> {
                    boolean l = true;
                    while (l) {
                        System.out.println("""
                                1. sort ascending
                                2. sort descending
                                3. log out
                                """);

                        int ch = scanner.nextInt();
                        scanner.nextLine();
                        switch (ch) {
                            case 1 -> {
                                System.out.println(patientService.sortPatientsByAge("1"));
                            }
                            case 2 -> {
                                System.out.println(patientService.sortPatientsByAge("2"));
                            }
                            case 3 -> {
                                l = false;
                            }
                            default -> {
                                System.out.println("Can't find this option!");
                            }
                        }
                    }

                }
                case 6 -> {
                    System.out.println(patientService.updateById(2L, new Patient("Syrga", "Temirbekova", 29, Gender.FEMALE)));
                }
                case 7 -> {
                    patientService.removeById(1L);
                }
                case 8 -> {
                    exit = false;
                }
                default -> {
                    System.out.println("Can't find this option!");
                }
            }
        }
    }

    public static void workWithDepartment() {
        boolean exit = true;
        while (exit) {
            System.out.println("""
                    1. add by hospital ID
                    2. get All Department By Hospital 
                    3. find Department By Name
                    4. update by ID
                    5. remove by ID
                    6. log out
                    """);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println(departmentService.add(2L, new Department(GeneratedID.setDepartmentID(),"Glaznoye")));
                }
                case 2 -> {
                    System.out.println(departmentService.getAllDepartmentByHospital(2L));
                }
                case 3 -> {
                    System.out.println(departmentService.findDepartmentByName("Glaznoye"));
                }
                case 4 -> {
                    System.out.println(departmentService.updateById(1L, new Department(GeneratedID.setDepartmentID(), "Evropotolog")));
                }
                case 5 ->{
                    departmentService.removeById(1L);
                }
                case 6 ->{
                    exit = false;
                }
                default -> {
                    System.out.println("We can't find this option!");
                }
            }
        }
    }
    public static void workWithDoctor(){
        boolean exit = true;
        while (exit){
            System.out.println("""
                    1. add
                    2. assign Doctor To Department
                    3. get All Doctors By Hospital Id
                    4. get All Doctors By Department Id
                    5. update By Id
                    6. remove By Id
                    7. log out
                    """);
            int choice = scanner.nextInt();
            switch (choice){
                case 1 ->{
                    System.out.println(doctorService.add(2L, new Doctor(GeneratedID.setDoctorID(), "Altyn", "Altymysheva", Gender.FEMALE, 14)));
                }
                case 2 ->{
                    List<Long> ids = new ArrayList<>();
                    ids.add(1L);
                    ids.add(2L);
                    System.out.println(doctorService.assignDoctorToDepartment(1L, ids));
                }
                case 3 ->{
                    System.out.println(doctorService.getAllDoctorsByHospitalId(1L));
                }
                case 4 ->{
                    System.out.println(doctorService.getAllDoctorsByDepartmentId(1L));
                }
                case 5 ->{
                    System.out.println(doctorService.updateById(1L, new Doctor("Jarkyn", "Amantaeva", Gender.FEMALE, 2)));
                }
                case 6 ->{
                    doctorService.removeById(1L);
                }
                case 7 ->{
                    exit = false;
                }
            }
        }
    }
}