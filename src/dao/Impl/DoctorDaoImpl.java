package dao.Impl;

import db.DataBase;
import model.Department;
import model.Doctor;
import model.Hospital;
import service.DoctorService;

import java.util.ArrayList;
import java.util.List;

public class DoctorDaoImpl implements DoctorService {
    @Override
    public Doctor findDoctorById(Long id) {
        for (Hospital hospital : DataBase.hospitals) {
            if(hospital.getDoctors() != null){
                for (Doctor doctor : hospital.getDoctors()) {
                    if(doctor.getId().equals(id)){
                        return doctor;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        List<Doctor> doctors = new ArrayList<>();
        for (Hospital hospital : DataBase.hospitals) {
            if(hospital.getDepartments() != null) {
                for (Department department : hospital.getDepartments()) {
                    if(department.getId().equals(departmentId)){
                        if(hospital.getDoctors() != null){
                            for (Doctor doctor : hospital.getDoctors()) {
                                for (Long l : doctorsId) {
                                    if(doctor.getId().equals(l)){
                                        doctors.add(doctor);
                                        System.out.println(doctor);
                                    }
                                }
                            }
                        }
                        department.setDoctors(doctors);
                    }
                }
                return "Successfully assigned.";
            }
        }
        return "Don't assigned to department.";
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        List<Doctor> doctors = new ArrayList<>();
        for (Hospital hospital : DataBase.hospitals) {
            if(hospital.getId().equals(id)){
                if(hospital.getDoctors() != null) {
                    doctors.addAll(hospital.getDoctors());
                    return doctors;
                }
            }
        }
        return null;
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        for (Hospital hospital : DataBase.hospitals) {
            if(hospital.getDepartments() != null){
                for (Department department : hospital.getDepartments()) {
                    if(department.getId().equals(id)){
                        if(department.getDoctors() != null){
                            return new ArrayList<>(department.getDoctors());
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String add(Long hospitalId, Doctor doctor) {
        for (Hospital hospital : DataBase.hospitals) {
            if(hospital.getId().equals(hospitalId)){
                List<Doctor> doctors = new ArrayList<>();
                doctors.add(doctor);
                System.out.println(doctor);
                hospital.setDoctors(doctors);
            }
        }
        return "Successfully added.";
    }

    @Override
    public void removeById(Long id) {
        boolean isCheck = false;
        for (Hospital hospital : DataBase.hospitals) {
            if(hospital.getDoctors() != null){
                for (Doctor doctor : hospital.getDoctors()) {
                    if(doctor.getId().equals(id)){
                        hospital.getDoctors().remove(doctor);
                        System.out.println("Successfully removed.");
                        isCheck = true;
                        return;
                    }
                }
            }
        }
        if(!isCheck){
            System.out.println("Don't removed the doctor.");
        }
    }

    @Override
    public String updateById(Long id, Doctor doctor) {
        for (Hospital hospital : DataBase.hospitals) {
            if(hospital.getDoctors() != null){
                for (Doctor d : hospital.getDoctors()) {
                    if(d.getId().equals(id)){
                        d.setExperienceYear(doctor.getExperienceYear());
                        d.setFirstName(doctor.getFirstName());
                        d.setGender(doctor.getGender());
                        d.setLastName(doctor.getLastName());
                        System.out.println(d);
                        return "Successfully updated.";
                    }
                }
            }
        }
        return "Can't updated the doctor info";
    }

}
