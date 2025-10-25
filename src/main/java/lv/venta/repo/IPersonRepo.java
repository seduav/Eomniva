package lv.venta.repo;

import lv.venta.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepo extends CrudRepository<Person, Long> {
}