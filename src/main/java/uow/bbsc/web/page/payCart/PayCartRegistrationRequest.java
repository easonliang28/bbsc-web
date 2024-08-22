package uow.bbsc.web.page.payCart;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PayCartRegistrationRequest {
    private Long iid;
    private Long id;
    private int qty;
}
