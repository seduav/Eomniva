package lv.venta.service.impl;

import lv.venta.model.Address;
import lv.venta.model.CustomerAsCompany;
import lv.venta.model.CustomerAsPerson;
import lv.venta.repo.*;
import lv.venta.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CustomerServiceImpl implements ICustomerService{
        @Autowired
        private ICustomerAsPersonRepo customerAsPersonRepo;
        @Autowired
        private ICustomerAsCompanyRepo customerAsCompanyRepo;
        @Autowired
        private IPersonRepo personRepo;
        @Autowired
        private IAddressRepo addressRepo;
        @Override
        public ArrayList<String> retrieveAllCustomerCode() {
            ArrayList<String> result = new ArrayList<>();
            for (CustomerAsPerson person : customerAsPersonRepo.findAll()) {
                result.add(person.getCustomerCode());
            }
            for (CustomerAsCompany company : customerAsCompanyRepo.findAll()) {
                result.add(company.getCustomerCode());
            }
            return result;
        }
        @Override
        public ArrayList<Long> retrieveAllIdForCustomers() {
            ArrayList<Long> result = new ArrayList<>();
            for (CustomerAsPerson person : customerAsPersonRepo.findAll()) {
                result.add(person.getIdc());
            }
            for (CustomerAsCompany company : customerAsCompanyRepo.findAll()) {
                result.add(company.getIdc());
            }
            return result;
        }
        @Override
        public void insertNewCustomerAsPerson(CustomerAsPerson customer) throws Exception {
            if(customer == null) throw new Exception("Customer is null");
            personRepo.save(customer.getPerson());
            CustomerAsPerson customerFromDB = customerAsPersonRepo.findByPersonNameAndPersonSurnameAndPersonPersonCodeAndPhoneNo(customer.getPerson().getName(),customer.getPerson().getSurname(),customer.getPerson().getPersonCode(), customer.getPhoneNo());

            if(customerFromDB != null) {
                throw new Exception("Customer already exists");
            } else {
                CustomerAsPerson c = new CustomerAsPerson(customer.getPerson(), customer.getPhoneNo());
                System.out.println(c);
                customerAsPersonRepo.save(c);
            }
        }

        @Override
        public void insertNewCustomerAsCompany(CustomerAsCompany company) throws Exception {
            if(company == null) throw new Exception("Company is null");
            CustomerAsCompany customerFromDB = customerAsCompanyRepo.findByPhoneNoAndTitleAndCompanyRegNo(company.getPhoneNo(), company.getTitle(), company.getCompanyRegNo());
            if(customerFromDB != null) {
                throw new Exception("Customer already exists");
            } else {
                company.setCustomerCode();
                customerAsCompanyRepo.save(company);
            }
        }

        @Override
        public void addAddressToCustomerByCustomerId(Address address, long customerId) throws Exception{
            if(address == null || customerId < 0) throw new IllegalArgumentException("Address or customerId is invalid");
            CustomerAsPerson personCustomer = customerAsPersonRepo.findById(customerId).orElse(null);
            CustomerAsCompany companyCustomer = customerAsCompanyRepo.findById(customerId).orElse(null);
            Address addressFromDb = addressRepo.findByCityAndHouseNoAndStreetHouseTitle(address.getCity(), address.getHouseNo(), address.getStreetHouseTitle());
            if( addressFromDb == null)
                addressRepo.save(address);
            else
                address = addressFromDb;

            if (personCustomer != null) {
                personCustomer.setAddress(address);
                customerAsPersonRepo.save(personCustomer);
            } else if (companyCustomer != null) {
                companyCustomer.setAddress(address);
                customerAsCompanyRepo.save(companyCustomer);
            } else {
                throw new Exception("Customer not found");
            }
        }

}