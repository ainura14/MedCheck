package dao;

import model.Patient;

import java.util.List;
import java.util.Map;

public interface PatientDao extends GenericDao<Patient>{
    String addPatientsToHospital(Long id, List<Patient> patients);
    Patient getPatientById(Long id);
    Map<Integer, Patient> getPatientByAge();
    List<Patient> sortPatientsByAge(String ascOrDesc);
}
