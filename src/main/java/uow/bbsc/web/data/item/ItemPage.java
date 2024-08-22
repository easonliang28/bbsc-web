package uow.bbsc.web.data.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Setter
@Getter
@AllArgsConstructor
public class ItemPage {
    private int pageNumber = 0;
    private int pageSize = 48;
    private Sort.Direction sortDirection= Sort.Direction.ASC;
    private String sortBy = "name";
    public ItemPage(){

    }
}
