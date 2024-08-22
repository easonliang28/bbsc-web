package uow.bbsc.web.page.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uow.bbsc.web.page.item.ItemPageService;

@AllArgsConstructor
@Service
public class AdminPageService {
    private final ItemPageService itemPageService;
    public String getIndex(){
        return "";
    }

    public String getItemPage(Long sid) {
        return itemPageService.getMyShopItem(sid,false);
    }

}
