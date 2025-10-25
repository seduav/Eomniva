package lv.venta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Driver extends Person{

    @OneToMany(
            mappedBy = "driver",
            cascade = CascadeType.ALL
    )
    @ToString.Exclude
    private Collection<Parcel> parcels;

    @PreRemove
    private void preRemove() {
        for (Parcel parcel : parcels) {
            parcel.setDriver(null);
        }
    }

    @NotNull
    @Column(name = "Experience_in_years")
    @Min(0)
    private float experienceInYears;

    @NotNull
    @Column(name = "License_no")
    @Pattern(regexp = "[A]{1}[T]{1}\\d{5,7}")
    private String licenseNo;

    public Driver(Person person, float experienceInYears, String licenseNo){
        super(person);
        setExperienceInYears(experienceInYears);
        setLicenseNo(licenseNo);
    }
    
}