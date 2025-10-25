package lv.venta.repo;

import lv.venta.model.City;
import lv.venta.model.Parcel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface IParcelRepo extends CrudRepository<Parcel, Long>{
    ArrayList<Parcel> findByAbstractCustomerIdc(long id);
    ArrayList<Parcel> findByPriceLessThan(double price);
    ArrayList<Parcel> findByDriverIdp(long id);
    ArrayList<Parcel> findByAbstractCustomerAddressCity(City city);
}