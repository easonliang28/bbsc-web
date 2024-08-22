package uow.bbsc.web.page.item;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ItemRegistrationRequest {

    private Long sid;
    private String name;
    private LocalDateTime update_time;
    private String description;
    private double price;
    private String icon;
    private int stock;
    private Long category_id;
    private List<String> tabs;

    public LocalDateTime getUpdate_time() {return LocalDateTime.now();}
}
