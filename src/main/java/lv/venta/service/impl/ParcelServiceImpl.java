package lv.venta.service.impl;

import lv.venta.model.*;
import lv.venta.repo.*;
import lv.venta.service.IDriverCRUDService;
import lv.venta.service.IParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ParcelServiceImpl implements IParcelService {

    @Autowired
    private ICustomerAsPersonRepo customerAsPersonRepo;
    @Autowired
    private ICustomerAsCompanyRepo customerAsCompanyRepo;
    @Autowired
    private IDriverRepo driverRepo;
    @Autowired
    private IParcelRepo parcelRepo;
    @Autowired
    private IDriverCRUDService driverCRUDService;

    @Override
    public ArrayList<Parcel> selectAllParcels() {
        return (ArrayList<Parcel>) parcelRepo.findAll();
    }
    @Override
    public ArrayList<Parcel> selectAllParcelsByCustomerId(long id) throws Exception {
        if(id <= 0) throw new Exception("Id should be positive");
        System.out.println(customerAsPersonRepo.findById(id).orElse(null) == null);
        System.out.println(customerAsCompanyRepo.findById(id).orElse(null) == null);
        if(customerAsPersonRepo.findById(id).orElse(null) == null && customerAsCompanyRepo.findById(id).orElse(null) == null)  throw new Exception("Customer with id (" + id + ") doesn't exist");

        ArrayList<Parcel> result = parcelRepo.findByAbstractCustomerIdc(id);
        if (result.isEmpty()) throw new Exception("There are no parcels for this customer");
        return result;
    }

    @Override
    public ArrayList<Parcel> selectAllParcelsDeliveredByDriverId(long id) throws Exception {
        if(id <= 0) throw new Exception("Id should be positive");
        if(!driverRepo.existsById(id)) throw new Exception("Driver with id (" + id + ") doesn't exist");

        ArrayList<Parcel> result =  parcelRepo.findByDriverIdp(id);
        if(result.isEmpty()) throw new Exception("There are no parcels for this driver");

        return result;
    }

    @Override
    public ArrayList<Parcel> selectAllParcelsPriceLessThan(float price) throws Exception {
        if(price <= 0) throw new Exception("Price should be positive");

        ArrayList<Parcel> result = parcelRepo.findByPriceLessThan(price);

        if(result.isEmpty()) throw new Exception("there are no parcels with price less than " + price + "€");

        return result;
    }

    @Override
    public ArrayList<Parcel> selectAllParcelsDeliveredToCity(City city) throws Exception {
        if(city == null) throw new Exception("City is null");
        ArrayList<Parcel> result = (ArrayList<Parcel>) parcelRepo.findByAbstractCustomerAddressCity(city);
        if(result.isEmpty()) throw new Exception("There are no parcels for this city");
        return result;
    }

    @Override
    public Parcel insertNewParcelByCustomerCodeAndDriverId(Parcel parcel, String customerCode, long driverId) throws Exception {
        if(customerCode == null) throw new Exception("CustomerCode null");
        if(driverId <= 0) throw new Exception("Id should be positive");
        if(!driverRepo.existsById(driverId)) throw new Exception("Driver with id (" + driverId + ") doesn't exist");
        if(     customerAsPersonRepo.findByCustomerCode(customerCode).orElse(null) == null &&
                customerAsCompanyRepo.findByCustomerCode(customerCode).orElse(null) == null)
            throw new Exception("Customer with id (" + customerCode + ") doesn't exist");

        CustomerAsPerson personCustomer = (CustomerAsPerson) customerAsPersonRepo.findByCustomerCode(customerCode).orElse(null);
        CustomerAsCompany companyCustomer = (CustomerAsCompany) customerAsCompanyRepo.findByCustomerCode(customerCode).orElse(null);

        parcel.setOrderCreated(LocalDateTime.now());
        parcel.setPlannedDelivery(LocalDateTime.now().plusDays(parcel.getDliveryTime()));
        parcel.setDriver(driverCRUDService.selectDriverById(driverId));
        parcel.setPrice();
        if (personCustomer != null) {
            parcel.setAbstractCustomer(personCustomer);
        } else if (companyCustomer != null) {
            parcel.setAbstractCustomer(companyCustomer);
        } else {
            throw new Exception("Customer not found");
        }
        System.out.println(parcel);
        parcelRepo.save(parcel);
        return parcel;
    }

    @Override
    public Parcel changeParcelDriverByParcelIdAndDriverId(long parcelId, long driverId) throws Exception {
        System.out.println(parcelId + " " + driverId);
        Driver driver = driverCRUDService.selectDriverById(driverId);
        Parcel parcel = parcelRepo.findById(parcelId).orElse(null);
        if(parcel == null && driver == null) throw new Exception("Parcel or driver not found");
        parcel.setDriver(driver);
        return parcelRepo.save(parcel);
    }

    @Override
    public double calculateIncomeOfParcelsByCustomerId(long id) {
        double totalIncome = 0.0;
        ArrayList<Parcel> parcels = parcelRepo.findByAbstractCustomerIdc(id);
        for (Parcel parcel : parcels) {
            totalIncome += parcel.getPrice();
        }
        return totalIncome;
    }

    @Override
    public int calculateHowManyParcelsNeedToDeliverToday() {
        int count = 0;

        ArrayList<Parcel> allParcels = (ArrayList<Parcel>) parcelRepo.findAll();
        for (Parcel parcel : allParcels) {
            if (parcel.getPlannedDelivery().equals(LocalDateTime.now())) {
                count++;
            }
        }
        return count;
    }

}