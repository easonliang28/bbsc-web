package uow.bbsc.web.page.payCart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayCartRatingRequest {
    private Long pid;
    private int rate;
}
