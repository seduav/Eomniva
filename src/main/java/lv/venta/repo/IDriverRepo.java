package lv.venta.repo;

import lv.venta.model.Driver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDriverRepo extends CrudRepository<Driver, Long> {
    Driver findByNameAndSurnameAndPersonCodeAndExperienceInYearsAndLicenseNo(String name, String surname, String personCode, float experienceInYears, String licenseNo);
}