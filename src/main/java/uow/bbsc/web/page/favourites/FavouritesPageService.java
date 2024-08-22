package uow.bbsc.web.page.favourites;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.favourites.FavouritesRepository;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.item.ItemPage;
import uow.bbsc.web.page.HTMLComponents;
import uow.bbsc.web.page.PageNaviButton;
import uow.bbsc.web.page.item.ItemPageService;

import java.util.List;

@AllArgsConstructor
@Service
public class FavouritesPageService extends HTMLComponents {

    private final ItemPageService itemPageService;
    private final FavouritesRepository FavouritesRepository;

    public String getPage(Long userId, ItemPage itemPage) {
        Pageable pageable = getPageRequest(itemPage);
        Page<Item> page = FavouritesRepository.findAllItem(pageable,userId);
        List<Item> list = page.getContent();
        String content="<div class='container '>" +
                "<h1>Favourites</h1>" +
                "<div class='row item-list'>";
        if(list.size()==0)
            content+="<h3>You do not have any favourite item.</h3>";
        for(int i = 0;i < list.size();i++) {
            content+=itemPageService.getItemBlock(list.get(i),userId);
        }
        content+="</div></div>";
        String url = "/item/all_items?sortBy="+itemPage.getSortBy()+"&sortDirection=";
        if(itemPage.getSortDirection() == Sort.Direction.ASC)url+="ASC";
        else url+="DESC";
        content+= "<div class='pageINF'>"+ PageNaviButton.getPageINF(page,url)+"</div>";
        return  content;

    }


    private PageRequest getPageRequest(ItemPage itemPage) {
        return PageRequest.of(
                itemPage.getPageNumber(),
                itemPage.getPageSize(),
                Sort.by(itemPage.getSortDirection(), itemPage.getSortBy()));
    }
}
