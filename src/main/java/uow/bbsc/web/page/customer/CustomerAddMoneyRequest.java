package uow.bbsc.web.page.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerAddMoneyRequest {
    double money;
    CustomerAddMoneyRequest(){}
}
