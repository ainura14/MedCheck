package service.Impl;

import dao.Impl.DepartmentDaoImpl;
import exceptions.DepartmentNotFoundException;
import model.Department;
import service.DepartmentService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDaoImpl departmentDao = new DepartmentDaoImpl();
    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        return departmentDao.getAllDepartmentByHospital(id);
    }

    @Override
    public Department findDepartmentByName(String name) {
        Department department = null;
        try {
            department = departmentDao.findDepartmentByName(name);
        } catch (NullPointerException e) {
            System.out.println("The hospital is empty.");
        }catch (DepartmentNotFoundException e){
            System.out.println("Don't find the department");
        }
        return department;
    }

    @Override
    public String add(Long hospitalId, Department department) {
        try{
            departmentDao.add(hospitalId, department);
        }catch (NullPointerException e){
            System.out.println("Can't find the hospital.");
        }
        return "Successfully added.";
    }

    @Override
    public void removeById(Long id) {
        departmentDao.removeById(id);
    }

    @Override
    public String updateById(Long id, Department department) {
        return departmentDao.updateById(id, department);
    }
}
