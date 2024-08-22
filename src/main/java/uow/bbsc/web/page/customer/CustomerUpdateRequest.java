package uow.bbsc.web.page.customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Period;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CustomerUpdateRequest {
    private Long id;
    private String name;
    private String address;
    private String email;
    private LocalDate dob;
    private String tel;
    private String icon;
    private String description;
    public int getAge() {
        return Period.between(this.dob,LocalDate.now()).getYears();
    }
}
