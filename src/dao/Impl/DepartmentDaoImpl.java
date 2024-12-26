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
        if(DataBase.hospitals.isEmpty()){
            throw new NullPointerException();
        }
        return DataBase.hospitals;
    }
    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        for (Hospital hospital : getAllHospitals()) { // catch
            if(hospital.getId().equals(id)){
                return new ArrayList<>(hospital.getDepartments());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Department findDepartmentByName(String name) {
        for (Hospital hospital :  getAllHospitals()) { // catch
            if(hospital.getDepartments() != null) {
                for (Department department : hospital.getDepartments()) {
                    if (department.getDepartmentName() != null && department.getDepartmentName().equals(name)) {
                        return department;
                    }
                }
            }
        }
        throw new DepartmentNotFoundException("Don't find the department"); // catch DepartmentNotFound
    }


    @Override
    public String add(Long hospitalId, Department department) {
        for (Hospital hospital :  getAllHospitals()){ //catch
            if(hospital.getId().equals(hospitalId)){
                List<Department> departments = hospital.getDepartments();
                if(departments == null){
                    departments = new ArrayList<>();
                }
                departments.add(department);
                hospital.setDepartments(departments);
                return "Successfully added new department to hospital with ID " + hospitalId;
            }
        }
        return "We can't find the hospital with ID " + hospitalId + " to add the new department.";
    }

    @Override
    public void removeById(Long id) {
        for (Hospital hospital : DataBase.hospitals) {
            if(hospital.getDepartments() != null) {
                for (Department department : hospital.getDepartments()) {
                    if (department.getId().equals(id)) {
                        hospital.getDepartments().remove(department);
                        System.out.println("Successfully deleted.");
                        return;
                    }
                }
            }
        }
        System.out.println("We can't find the department to deleted.");
    }

    @Override
    public String updateById(Long id, Department department) {
        for (Hospital hospital : DataBase.hospitals) {
            if(hospital.getDepartments() != null) {
                for (Department hospitalDepartment : hospital.getDepartments()) {
                    if (hospitalDepartment.getId().equals(id)) {
                        hospitalDepartment.setDepartmentName(department.getDepartmentName());
                        return "Successfully updated.";
                    }
                }
            }
        }
        return "We can't update.";
    }
}
