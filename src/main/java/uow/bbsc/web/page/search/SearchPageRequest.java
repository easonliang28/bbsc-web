package uow.bbsc.web.page.search;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SearchPageRequest {
    private Long id;
    private List<String> tabs;
    private int pageNumber;
    private Sort.Direction sortDirection;
    private String sortBy;
    private int max;
    private int min;
    private String keyword;
}
