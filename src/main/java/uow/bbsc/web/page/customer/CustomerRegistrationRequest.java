package uow.bbsc.web.page.customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CustomerRegistrationRequest {
    private String name;
    private String address;
    private String email;
    private LocalDate dob;
    private String tel;
    private String icon;
    private String pwd;
}
