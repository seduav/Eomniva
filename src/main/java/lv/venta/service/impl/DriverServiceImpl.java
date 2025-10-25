package lv.venta.service.impl;

import lv.venta.model.Driver;
import lv.venta.repo.*;
import lv.venta.service.IDriverCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class DriverServiceImpl implements IDriverCRUDService {

    @Autowired
    private IDriverRepo driverRepo;

    @Override
    public ArrayList<Driver> selectAllDrivers() {
        return (ArrayList<Driver>) driverRepo.findAll();
    }

    @Override
    public Driver selectDriverById(long id) throws Exception {
        if(id < 0) throw new Exception("Id should be positive");
        if(driverRepo.existsById(id))
        {
            return driverRepo.findById(id).get();
        }
        throw new Exception("Driver with " + id + " is not found");
    }

    @Override
    public Driver deleteDriverById(long id) throws Exception {
        Driver deleteProduct = selectDriverById(id);
        driverRepo.delete(deleteProduct);
        return deleteProduct;
    }

    @Override
    public Driver insertNewDriver(Driver driver) throws Exception {
        if(driver == null) throw new Exception("Company is null");
        Driver driverFromDB = driverRepo.findByNameAndSurnameAndPersonCodeAndExperienceInYearsAndLicenseNo(driver.getName(), driver.getSurname(), driver.getPersonCode(), driver.getExperienceInYears(), driver.getLicenseNo());
        if(driverFromDB != null) {
            throw new Exception("Customer already exists");
        } else {
            return driverRepo.save(driver);
        }
    }

    @Override
    public Driver updateDriverById(long id, Driver driver) throws Exception {
        Driver updateDriver = selectDriverById(id);
        updateDriver.setName(driver.getName());
        updateDriver.setSurname(driver.getSurname());
        updateDriver.setPersonCode(driver.getPersonCode());
        updateDriver.setExperienceInYears(driver.getExperienceInYears());
        updateDriver.setLicenseNo(driver.getLicenseNo());
        return driverRepo.save(updateDriver);
    }

}