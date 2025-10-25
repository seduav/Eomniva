package lv.venta.service;

import lv.venta.model.Driver;
import java.util.ArrayList;

public interface IDriverCRUDService {
    public abstract ArrayList<Driver> selectAllDrivers();
    public abstract Driver selectDriverById(long id) throws Exception;
    public abstract Driver deleteDriverById(long id) throws Exception;
    public abstract Driver insertNewDriver(Driver driver) throws Exception;
    public abstract Driver updateDriverById(long id, Driver driver) throws Exception;
}