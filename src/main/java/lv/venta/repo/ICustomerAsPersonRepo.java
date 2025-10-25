package lv.venta.repo;

import lv.venta.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ICustomerAsPersonRepo extends CrudRepository<CustomerAsPerson, Long>{
    Optional<Object> findByCustomerCode(String customerCode);
    CustomerAsPerson findByPersonNameAndPersonSurnameAndPersonPersonCodeAndPhoneNo(String name, String surname, String personCode, String phoneNo);
}