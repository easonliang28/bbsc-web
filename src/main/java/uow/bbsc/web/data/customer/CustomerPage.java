package uow.bbsc.web.data.customer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
@Setter
@Getter
public class CustomerPage {
    private int pageNumber = 0;
    private int pageSize = 48;
    private Sort.Direction sortDirection= Sort.Direction.ASC;
    private String sortBy = "name";
}
