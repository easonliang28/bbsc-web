package uow.bbsc.web.page.payCart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayCartBuyRequest {
    ArrayList<Long> pidList;
    PayCartBuyRequest(){}
}
