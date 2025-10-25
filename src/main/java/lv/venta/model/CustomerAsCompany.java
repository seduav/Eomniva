package lv.venta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class CustomerAsCompany extends AbstractCustomer{

    @NotNull
    @Column(name = "Company_regNo")
    @Pattern(regexp = "LV\\d+")
    private String companyRegNo;

    @NotNull
    @Column(name = "Title")
    private String title;

    public CustomerAsCompany(Address address, String phoneNo, String title, String companyRegNo) {
        super(address, phoneNo);
        setTitle(title);
        setCompanyRegNo(companyRegNo);
        setCustomerCode();
    }

    @Override
    public void setCustomerCode() {
        super.customerCode  = getIdc() + "_company_" + companyRegNo;
    }
    
}