package dao.Impl;

import db.DataBase;
import model.Department;
import model.Doctor;
import model.Hospital;
import service.DoctorService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorDaoImpl implements DoctorService {
    @Override
    public Doctor findDoctorById(Long id) {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getDoctors() != null)
                .flatMap(hospital -> hospital.getDoctors().stream())
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getDepartments() != null)
                .flatMap(hospital -> hospital.getDepartments().stream())
                .filter(department -> department.getId().equals(departmentId))
                .findFirst()
                .map(department -> {
                    List<Doctor> doctorsToAssign = DataBase.hospitals.stream()
                            .filter(hospital -> hospital.getDoctors() != null)
                            .flatMap(hospital -> hospital.getDoctors().stream())
                            .filter(doctor -> doctorsId.contains(doctor.getId()))
                            .collect(Collectors.toList());
                    department.setDoctors(doctorsToAssign);
                    return "Successfully assigned.";
                })
                .orElse("Don't assigned to department.");
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .map(hospital -> hospital.getDoctors())
                .filter(doctors -> doctors != null)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getDepartments() != null)
                .flatMap(hospital -> hospital.getDepartments().stream())
                .filter(department -> department.getId().equals(id))
                .map(department -> department.getDoctors())
                .filter(doctors -> doctors != null)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String add(Long hospitalId, Doctor doctor) {
        boolean found = DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .peek(hospital -> {
                    if(hospital.getDoctors() == null){
                        hospital.setDoctors((new ArrayList<>()));
                    }
                    hospital.getDoctors().add(doctor);
                    System.out.println(doctor);
                })
                .findFirst()
                .isPresent();
        return found ? "Successfully added." : "Hospital with ID " + hospitalId + " not found.";
    }

    @Override
    public void removeById(Long id) {
        boolean isRemoved = DataBase.hospitals.stream()
                .filter(hospital -> hospital.getDoctors() != null)
                .flatMap(hospital -> hospital.getDoctors().stream())
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst()
                .map(doctor -> {
                    DataBase.hospitals.forEach(hospital -> {
                        if(hospital.getDoctors() != null){
                            hospital.getDoctors().remove(doctor);
                        }
                    });
                    return true;
                })
                .orElse(false);
        if (isRemoved) {
            System.out.println("Successfully removed.");
        } else {
            System.out.println("Don't removed the doctor.");
        }
    }

    @Override
    public String updateById(Long id, Doctor doctor) {
        boolean isUpdated = DataBase.hospitals.stream()
                .filter(hospital -> hospital.getDoctors() != null)
                .flatMap(hospital -> hospital.getDoctors().stream())
                .filter(doctor1 -> doctor1.getId().equals(id))
                .peek(doctor1 -> {
                    doctor1.setFirstName(doctor.getFirstName());
                    doctor1.setLastName(doctor.getLastName());
                    doctor1.setGender(doctor.getGender());
                    doctor1.setExperienceYear(doctor.getExperienceYear());
                })
                .findFirst()
                .isPresent();
        return isUpdated ? "Successfully updated." : "Can't update the doctor info.";
    }

}
