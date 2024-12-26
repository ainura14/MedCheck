package service.Impl;

import dao.Impl.HospitalDaoImpl;
import model.Hospital;
import model.Patient;
import service.HospitalService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class HospitalServiceImpl implements HospitalService {
    private final HospitalDaoImpl hospitalDao = new HospitalDaoImpl();
    @Override
    public String addHospital(Hospital hospital) {
        return hospitalDao.addHospital(hospital);
    }

    @Override
    public Hospital findHospitalById(Long id){
        Hospital hospital = null;
        try {
            hospital =  hospitalDao.findHospitalById(id);
        } catch (NullPointerException | NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        return hospital;
    }

    @Override
    public List<Hospital> getAllHospital() {
        return hospitalDao.getAllHospital();
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        return hospitalDao.getAllPatientFromHospital(id);
    }

    @Override
    public String deleteHospitalById(Long id) {
        return hospitalDao.deleteHospitalById(id);
    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        return hospitalDao.getAllHospitalByAddress(address);
    }
}
