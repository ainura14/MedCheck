package dao.Impl;

import dao.HospitalDao;
import db.DataBase;
import model.Hospital;
import model.Patient;

import java.util.*;
import java.util.stream.Collectors;

public class HospitalDaoImpl implements HospitalDao {
    @Override
    public String addHospital(Hospital hospital) {
        boolean exists = DataBase.hospitals.stream()
                        .anyMatch(hospital1 -> hospital1.getId().equals(hospital.getId()));
        if(exists){
            return "Hospital with ID " + hospital.getId() + " already exists.";
        }
        DataBase.hospitals.add(hospital);
        System.out.println("Hospital added " + hospital);
        return "Successfully added.";
    }

    @Override
    public Hospital findHospitalById(Long id) {
        return getAllHospital().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Hospital not found with ID: " + id));
    }

    @Override
    public List<Hospital> getAllHospital() {
        return DataBase.hospitals;
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        return getAllHospital().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .map(Hospital::getPatients)
                .filter(patients -> patients != null && !patients.isEmpty())
                .findFirst()
                .orElse(new ArrayList<>());
    }

    @Override
    public String deleteHospitalById(Long id) {
        boolean removed = DataBase.hospitals.removeIf(hospital -> hospital.getId().equals(id));

        if (removed) {
            return "Successfully deleted.";
        } else {
            return "We can't find the hospital with ID " + id;
        }
    }


    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        return getAllHospital().stream()
                .filter(hospital -> hospital.getAddress().equals(address))
                .collect(Collectors.toMap(
                        hospital -> "The hospital",
                        hospital -> hospital
                ));
    }
}
