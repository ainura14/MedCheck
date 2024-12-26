package dao.Impl;

import dao.HospitalDao;
import db.DataBase;
import model.Hospital;
import model.Patient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class HospitalDaoImpl implements HospitalDao {
    @Override
    public String addHospital(Hospital hospital) {
        DataBase.hospitals.add(hospital);
        System.out.println(hospital);
        return "Successfully added.";
    }

    @Override
    public Hospital findHospitalById(Long id) {
        List<Hospital> hospitals = getAllHospital();
        for (Hospital hospital : hospitals) {
            if (hospital.getId().equals(id)) {
                return hospital;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public List<Hospital> getAllHospital() {
        if (DataBase.hospitals.isEmpty()) throw new NullPointerException("");
        return DataBase.hospitals;
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        try {
            return findHospitalById(id).getPatients();
        }catch (NullPointerException e){
            System.out.println("The are no patient in hospital." + e.getMessage());
        }
        return null;
    }

    @Override
    public String deleteHospitalById(Long id) {
        Hospital hospitalById = findHospitalById(id);
        if (hospitalById != null) {
            DataBase.hospitals.remove(hospitalById);
        } else {
            return "We can't find this hospital " + id;
        }

        return "Successfully deleted.";
    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        List<Hospital> allHospital = getAllHospital();
        Map<String, Hospital> result = new HashMap<>();
        for (Hospital hospital : allHospital) {
            if (hospital.getAddress().equals(address)) {
                result.put("The hospital", hospital);
            }
        }
        if (result != null) {
            return result;
        }
        return null;
    }
}
