package dao.Impl;

import dao.PatientDao;
import db.DataBase;
import model.Hospital;
import model.Patient;

import java.util.*;

public class PatientDaoImpl implements PatientDao {
    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        for (Hospital hospital : DataBase.hospitals) {
            if (hospital.getId().equals(id)) {
                List<Patient> existingPatients = hospital.getPatients();
                if(existingPatients == null){
                    existingPatients = new ArrayList<>();
                }
                System.out.println(patients);
                existingPatients.addAll(patients);
                hospital.setPatients(existingPatients);
                return "Successfully added all patients.";
            }
        }
        return "We can't find hospital with this " + id + " ID";
    }

    @Override
    public Patient getPatientById(Long id) {
        for (Hospital hospital : DataBase.hospitals) {
            List<Patient> patientsList;
            patientsList = hospital.getPatients();
            for (Patient patient : patientsList) {
                if (patient.getId().equals(id)) {
                    return patient;
                }
            }
        }
        return null;
    }

    @Override
    public Map<Integer, Patient> getPatientByAge() {
        Map<Integer, Patient> integerPatientMap = new HashMap<>();
        for (Hospital hospital : DataBase.hospitals) {
            List<Patient> patientsList = new ArrayList<>();
            patientsList = hospital.getPatients();
            for (Patient patient : patientsList) {
                integerPatientMap.put(patient.getAge(), patient);
            }
        }
        return integerPatientMap;
    }

    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        List<Patient> patientList = new ArrayList<>(getAllPatients());

        if (ascOrDesc.equals("1")) {
            patientList.sort(new Comparator<Patient>() {
                @Override
                public int compare(Patient o1, Patient o2) {
                    return o1.getAge() - o2.getAge();
                }
            });
        } else if (ascOrDesc.equals("2")) {
            patientList.sort(new Comparator<Patient>() {
                @Override
                public int compare(Patient o1, Patient o2) {
                    return o2.getAge() - o1.getAge();
                }
            });
        }

        return patientList;
    }

    @Override
    public String add(Long hospitalId, Patient patient) {
        for (Hospital hospital : DataBase.hospitals) {
            if (hospital.getId().equals(hospitalId)) {
                List<Patient> patients = hospital.getPatients();
                if(patients == null){
                    patients = new ArrayList<>();
                }
                patients.add(patient);
                System.out.println(patient);
                hospital.setPatients(patients);
                return "Successfully added the patient to hospital with this " + hospitalId + " ID";
            }
        }
        return "We can't added the patient to hospital";
    }

    @Override
    public void removeById(Long id) {
        for (Hospital hospital : DataBase.hospitals) {
            for (Patient patient : hospital.getPatients()) {
                if (patient.getId().equals(id)) {
                    hospital.getPatients().remove(patient);
                    System.out.println("Successfully removed.");
                    return;
                }
            }
            System.out.println("We can't find the patient with this " + id);
        }
    }

    @Override
    public String updateById(Long id, Patient patient) {
        for (Patient patients : getAllPatients()) {
            if (patients.getId().equals(id)) {
                patients.setAge(patient.getAge());
                patients.setGender(patient.getGender());
                patients.setFirstName(patient.getFirstName());
                patients.setLastName(patient.getLastName());
                System.out.println(patients);
                return "Successfully updated.";
            }
        }
        return "We can't find the patient with this " + id;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patientList = new ArrayList<>();
        for (Hospital hospital : DataBase.hospitals) {
            patientList.addAll(hospital.getPatients());
        }
        return patientList;
    }
}
