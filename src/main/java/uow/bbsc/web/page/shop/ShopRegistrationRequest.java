package uow.bbsc.web.page.shop;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uow.bbsc.web.data.customer.Customer;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ShopRegistrationRequest {
    private Customer customer;
    private String name;
    private String address;
    private String email;
    private String tel;
    private String icon;
    private LocalDate registered_date;
    public LocalDate getRegistered_date() {return LocalDate.now();}
}
