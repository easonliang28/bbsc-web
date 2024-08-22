package uow.bbsc.web.data.payCart;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class PayCartPage {
    private int pageNumber = 0;
    private int pageSize = 48;
    private Sort.Direction sortDirection= Sort.Direction.ASC;
    private String sortBy = "sid";

    public PayCartPage(int pageNumber, int pageSize, Sort.Direction sortDirection, String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
    }
}
