package lv.venta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "PARCEL_TABLE")
@Entity
public class Parcel {
    
    @Setter(value = AccessLevel.NONE)
    @Column(name = "Idpa")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long idpa;

    @NotNull
    @Column(name = "Is_fragile")
    boolean isFragile;
    public  boolean getIsFragile(){
        return isFragile;
    }

    @NotNull
    @Column(name = "Order_created")
    private LocalDateTime orderCreated;

    @NotNull
    @Column(name = "Planned_delivery")
    private LocalDateTime plannedDelivery;

    private int dliveryTime = 2;

    @Setter(value = AccessLevel.NONE)
    @Column(name = "Price")
    @Min(0)
    private float price;
    public void setPrice() {
        this.price = (float) ((size.getValue() * 1.99) + (isFragile ? 2.99 : 0));
    }

    @Column(name = "Size")
    private Size size = Size.notKnown;

    @ManyToOne
    @JoinColumn(name="Idc")
    private AbstractCustomer abstractCustomer;

     @ManyToOne
     @JoinColumn(name="Idp")
     private Driver driver;

    public Parcel(boolean isFragile, Size size, AbstractCustomer customer, Driver driver){
        setFragile(isFragile);
        setOrderCreated(LocalDateTime.now());
        setPlannedDelivery(LocalDateTime.now().plusDays(2));
        setSize(size);
        setAbstractCustomer(customer);
        setDriver(driver);
        setPrice();
    }

}