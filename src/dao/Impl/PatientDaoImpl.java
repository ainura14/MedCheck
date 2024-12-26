package dao.Impl;

import dao.PatientDao;
import db.DataBase;
import model.Hospital;
import model.Patient;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class PatientDaoImpl implements PatientDao {
    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .map(hospital -> {
                    List<Patient> patientList = hospital.getPatients();
                    if(patientList == null){
                        patientList = new ArrayList<>();
                    }
                    patientList.addAll(patients);
                    hospital.setPatients(patientList);
                    return "Successfully added all patients.";
                })
                .orElse("We can't find hospital with this " + id + " ID");
    }

    @Override
    public Patient getPatientById(Long id) {
        return DataBase.hospitals.stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .filter(patient -> patient.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Map<Integer, Patient> getPatientByAge() {
        return DataBase.hospitals.stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .collect(Collectors.toMap(
                        Patient::getAge,
                        patient -> patient,
                        (existing, replacement) -> existing
                ));
    }

    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        return getAllPatients().stream()
                .sorted((ascOrDesc.equals("1"))
                        ? Comparator.comparingInt(Patient::getAge)
                        : Comparator.comparingInt(Patient::getAge).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public String add(Long hospitalId, Patient patient) {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst()
                .map(hospital -> {
                    List<Patient> patientList = hospital.getPatients();
                    if (patientList == null) {
                        patientList = new ArrayList<>();
                    }
                    patientList.add(patient);
                    hospital.setPatients(patientList);

                    System.out.println(patient);
                    return "Successfully added the patient to hospital with this " + hospitalId + " ID";
                })
                .orElse("We can't add the patient to hospital with ID " + hospitalId);
    }


    @Override
    public void removeById(Long id) {
        boolean removed = DataBase.hospitals.stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .filter(patient -> patient.getId().equals(id))
                .findFirst()
                .map(patient -> {
                    return DataBase.hospitals.stream()
                            .filter(hospital -> hospital.getPatients().contains(patient))
                            .findFirst();
                })
                .flatMap(hospital -> hospital.map(h -> {
                    h.getPatients().removeIf(patient -> patient.getId().equals(id));
                    System.out.println("Successfully removed.");
                    return true;
                }))
                .orElse(false);
        if (!removed) {
            System.out.println("We can't find the patient with this " + id);
        }
    }


    @Override
    public String updateById(Long id, Patient patient) {
        return getAllPatients().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(p -> {
                    p.setAge(patient.getAge());
                    p.setGender(patient.getGender());
                    p.setFirstName(patient.getFirstName());
                    p.setLastName(patient.getLastName());
                    System.out.println(p);
                    return "Successfully updated.";
                })
                .orElse("We can't find the patient with this " + id);
    }


    public List<Patient> getAllPatients() {
        return DataBase.hospitals.stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .collect(Collectors.toList());
    }

}
