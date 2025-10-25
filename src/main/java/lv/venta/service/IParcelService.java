package lv.venta.service;

import lv.venta.model.City;
import lv.venta.model.Parcel;
import java.util.ArrayList;

public interface IParcelService {
    public abstract ArrayList<Parcel> selectAllParcels();
    public abstract ArrayList<Parcel> selectAllParcelsByCustomerId(long id) throws Exception;
    public abstract ArrayList<Parcel> selectAllParcelsDeliveredByDriverId(long id) throws Exception;
    public abstract ArrayList<Parcel> selectAllParcelsPriceLessThan(float price) throws Exception;
    public ArrayList<Parcel> selectAllParcelsDeliveredToCity(City city) throws Exception;
    public Parcel insertNewParcelByCustomerCodeAndDriverId(Parcel parcel, String customerCode, long driverId) throws Exception;
    public abstract Parcel changeParcelDriverByParcelIdAndDriverId(long parcelId, long driverId) throws Exception;
    public abstract double calculateIncomeOfParcelsByCustomerId(long id);
    public abstract int calculateHowManyParcelsNeedToDeliverToday();
}