package dao.Impl;

import db.DataBase;
import exceptions.DepartmentNotFoundException;
import model.Department;
import model.Hospital;
import service.DepartmentService;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentService {

    public List<Hospital> getAllHospitals(){
        return DataBase.hospitals.stream()
                .findFirst()
                .map(hospital -> DataBase.hospitals)
                .orElseThrow(NullPointerException::new);
    }
    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .map(hospital -> new ArrayList<>(hospital.getDepartments()))
                .orElse(new ArrayList<>());
    }

    @Override
    public Department findDepartmentByName(String name) {
        return getAllHospitals().stream()
                .flatMap(hospital -> hospital.getDepartments().stream())
                .filter(department -> department.getDepartmentName() != null && department.getDepartmentName().equals(name))
                .findFirst()
                .orElseThrow(() -> new DepartmentNotFoundException("Don't find the department"));
    }


    @Override
    public String add(Long hospitalId, Department department) {
        return getAllHospitals().stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst()
                .map(hospital -> {
                    List<Department> departments = hospital.getDepartments();
                    if(departments == null){
                        departments = new ArrayList<>();
                    }
                    departments.add(department);
                    hospital.setDepartments(departments);
                    return "Successfully added new department to hospital with ID " + hospitalId;
                })
                .orElse("We can't find the hospital with ID " + hospitalId + " to add the new department.");
    }

    @Override
    public void removeById(Long id) {
        boolean removed = getAllHospitals().stream()
                .filter(hospital -> hospital.getDepartments() != null)
                .flatMap(hospital -> hospital.getDepartments().stream())
                .filter(department -> department.getId().equals(id))
                .findFirst()
                .map(department -> {
                    getAllHospitals().forEach(hospital -> {
                        if(hospital.getDepartments() != null){
                            hospital.getDepartments().remove(department);
                        }
                    });
                    return true;
                })
                .orElse(false);
        if(removed){
            System.out.println("Successfully deleted.");
        }else{
            System.out.println("We can't find the department to delete.");
        }
    }

    @Override
    public String updateById(Long id, Department department) {
        return getAllHospitals().stream()
                .filter(hospital -> hospital.getDepartments() != null)
                .flatMap(hospital -> hospital.getDepartments().stream())
                .filter(department1 -> department1.getId().equals(id))
                .findFirst()
                .map(department1 -> {
                    department1.setDepartmentName(department.getDepartmentName());
                    return "Successfully updated.";
                })
                .orElse("We can't update.");
    }
}
