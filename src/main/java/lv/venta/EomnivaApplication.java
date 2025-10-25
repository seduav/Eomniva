package lv.venta;

import lv.venta.model.*;
import lv.venta.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EomnivaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EomnivaApplication.class, args);
    }
    @Bean
    public CommandLineRunner testDatabaseLayer(
            IAddressRepo addressRepo,
            ICustomerAsCompanyRepo customerAsCompanyRepo,
            ICustomerAsPersonRepo customerAsPersonRepo,
            IDriverRepo driverRepo,
            IPersonRepo personRepo,
            IParcelRepo parcelRepo) {
        return new CommandLineRunner() {


            @Override
            public void run(String... args) throws Exception {

                Person pers = new Person("Name1","123456-12345", "Surname1");
                personRepo.save(pers);
                Person pers2 = new Person("Name2", "234567-23456", "Surname2");
                personRepo.save(pers2);

               Driver d1 = new Driver(pers, 1, "AT12345");
               System.out.println(d1);
               driverRepo.save(d1);
                Driver d2 = new Driver(pers2, 2, "AT23456");
                System.out.println(d2);
                driverRepo.save(d2);

               Address addr1 = new Address(City.Ventspils, "Brivibas", 1);
                addressRepo.save(addr1);
                Address addr2 = new Address(City.Riga, "Talsu", 2);
                addressRepo.save(addr2);

               CustomerAsPerson cust1 = new CustomerAsPerson(pers2, addr1, "25367481");
               System.out.println(cust1);
               customerAsPersonRepo.save(cust1);
                CustomerAsPerson cust2 = new CustomerAsPerson(pers, addr1, "23684627");
                System.out.println(cust2);
                customerAsPersonRepo.save(cust2);

                CustomerAsCompany cust3 = new CustomerAsCompany(addr2, "25645879", "SIA Labais", "LV1234567");
                customerAsCompanyRepo.save(cust3);
                System.out.println(cust3);
                CustomerAsCompany cust4 = new CustomerAsCompany(addr2, "23245368", "SIA Kruta", "LV2345678");
                customerAsCompanyRepo.save(cust4);
                System.out.println(cust4);

                Parcel pckg1 = new Parcel(true, Size.M, cust1, d2);
                System.out.println(pckg1);
                parcelRepo.save(pckg1);
                System.out.println(d2);
                Parcel pckg2 = new Parcel(false, Size.XL, cust3, d2);
                System.out.println(pckg2);
                parcelRepo.save(pckg2);
                System.out.println(d2);
            }
        };
    }

}