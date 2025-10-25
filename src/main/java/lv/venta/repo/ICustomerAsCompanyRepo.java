package lv.venta.repo;

import lv.venta.model.City;
import lv.venta.model.CustomerAsCompany;
import lv.venta.model.Parcel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface ICustomerAsCompanyRepo extends CrudRepository<CustomerAsCompany, Long> {
    CustomerAsCompany findByPhoneNoAndTitleAndCompanyRegNo(String phoneNo, String title, String companyRegNo);
    Optional<Object> findByCustomerCode(String customerCode);
    ArrayList<Parcel> findByAddressCity(City city);
}