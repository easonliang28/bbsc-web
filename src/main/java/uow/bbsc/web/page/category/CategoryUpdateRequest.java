package uow.bbsc.web.page.category;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CategoryUpdateRequest {

    private Long category_id;
    private String name;
    private List<String> tab;
}
