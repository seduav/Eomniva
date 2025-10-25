package lv.venta.service;

import lv.venta.model.Address;
import lv.venta.model.CustomerAsCompany;
import lv.venta.model.CustomerAsPerson;
import java.util.ArrayList;

public interface ICustomerService {
    public void insertNewCustomerAsPerson(CustomerAsPerson customerAsPerson) throws Exception;
    public void insertNewCustomerAsCompany(CustomerAsCompany company) throws Exception;
    public void addAddressToCustomerByCustomerId(Address address, long customerId) throws Exception;
    public ArrayList<String> retrieveAllCustomerCode();
    public ArrayList<Long> retrieveAllIdForCustomers();
}