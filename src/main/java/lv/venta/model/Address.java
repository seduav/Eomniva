package lv.venta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "ADDRESS_TABLE")
@Entity
public class Address {

    @Setter(value = AccessLevel.NONE)
    @Column(name = "Ida")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long ida;

    @NotNull
    @Column(name = "City")
    private City city;

    @NotNull
    @Column(name = "House_no")
    @Min(0)
    private int houseNo;

    @NotNull
    @Column(name = "Street_or_house_title")
    @Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀČŅ]{1}[a-zēūīļķģšāžčņ]+")
    private String streetHouseTitle;

    public Address(City city, String streetOrHouseTitle, int houseNo){
        setCity(city);
        setHouseNo(houseNo);
        setStreetHouseTitle(streetOrHouseTitle);
    }

}