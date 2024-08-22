package uow.bbsc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import uow.bbsc.web.data.item.ItemPage;
import uow.bbsc.web.page.HTMLComponents;
import uow.bbsc.web.page.HTMLPacker;
import uow.bbsc.web.page.category.CategoryPageService;
import uow.bbsc.web.page.item.ItemPageService;

@AllArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemPageController extends HTMLComponents {
    private final HTMLPacker packer;
    private final ItemPageService itemPageService;
    private final CategoryPageService categoryPageService;

    @GetMapping("/all_items")
    public String allItems(@RequestParam(required = false,defaultValue="1") int pageNumber,
                           @RequestParam(required = false,defaultValue="ASC") Sort.Direction sortDirection,
                           @RequestParam(required = false,defaultValue="name") String sortBy
    ){
        ItemPage itemPage = new ItemPage(pageNumber-1,48,sortDirection,sortBy);
        return packer.getHTML(
                itemPageService.getItemPage(itemPage,packer.getUsername()),
                "All items");
    }

    @GetMapping("/search")
    public String naviLink(@RequestParam Long categoryId,
                           @RequestParam(required = false,defaultValue="") String tab,
                           @RequestParam(required = false,defaultValue="1") int pageNumber,
                           @RequestParam(required = false,defaultValue="ASC") Sort.Direction sortDirection,
                           @RequestParam(required = false,defaultValue="name") String sortBy){
        ItemPage itemPage = new ItemPage(pageNumber-1,48,sortDirection,sortBy);
        return packer.getHTML(
                categoryPageService.getNaviLinkSearchPage(categoryId,tab,itemPage,packer.getUsername()),
                "search",
                "<link rel=\"stylesheet\" href=\"/css/searchFilter.css\">" +
                        "");
    }
    @RequestMapping("/{iid}")
    public String shopView(@PathVariable("iid")Long iid){return packer.getHTML(itemPageService.getItemView(iid),"My Shop",
            "<link rel=\"stylesheet\" href=\"/css/shopView.css\">" +
                    packer.getJsImpl("comment"));}

}
