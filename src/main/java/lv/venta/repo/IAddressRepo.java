package lv.venta.repo;

import lv.venta.model.Address;
import lv.venta.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressRepo extends CrudRepository<Address, Long>{
    Address findByCityAndHouseNoAndStreetHouseTitle(City city, int houseNo, String streetHouseTitle);
}