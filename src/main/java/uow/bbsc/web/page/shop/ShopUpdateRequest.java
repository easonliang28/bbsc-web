package uow.bbsc.web.page.shop;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ShopUpdateRequest {
    private Long sid;
    private String name;
    private String address;
    private String email;
    private String tel;
    private String icon;
    private String description;
}
