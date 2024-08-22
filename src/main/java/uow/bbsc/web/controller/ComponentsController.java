package uow.bbsc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uow.bbsc.web.data.category.CategoryRepository;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.item.ItemRepository;
import uow.bbsc.web.data.item.ItemService;
import uow.bbsc.web.data.shop.ShopRepository;
import uow.bbsc.web.page.PageNaviButton;
import uow.bbsc.web.page.category.CategoryPageService;
import uow.bbsc.web.page.item.ItemPageService;
import uow.bbsc.web.page.search.SearchPageRequest;
import uow.bbsc.web.page.shop.ShopPageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/components")
public class ComponentsController {
    private final CategoryPageService categoryPageService;
    private final CategoryRepository categoryRepository;
    private final ItemPageService itemPageService;
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final ShopPageService shopPageService;
    private final ShopRepository shopRepository;

    @PostMapping("/admin/categoryBlock/{categoryId}")
    public String categoryBlock(@PathVariable("categoryId") Long categoryId){
        return categoryPageService.getAdminCategoryBlock(categoryId);
    }

    @PostMapping("/shopBlock/{sid}")
    public String shopBlock(@PathVariable("sid") Long sid){
        return shopPageService.getEditShopBlock(shopRepository.findShopBySid2(sid),false);
    }

    @PostMapping("/naviCategoryBlock/{categoryId}")
    public String naviCategoryBlock(@PathVariable("categoryId") Long categoryId){
        if(categoryId==0)
            return "(no category block)";
        return categoryPageService.getNaviCategoryBlock(categoryRepository.findCategoryById2(categoryId));
    }

    @PostMapping("/searchCategoryBlock/{categoryId}")
    public String searchCategoryBlock(@PathVariable("categoryId") Long categoryId,@RequestBody SearchPageRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        Page<Item> page = itemService.getItemPage(categoryId,request);
        return itemPageService.getItemPage(page,request.getId());
    }
    @PostMapping("/searchCategoryPageINF/{categoryId}")
    public String searchCategoryPageINF(@PathVariable("categoryId") Long categoryId,@RequestBody SearchPageRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {

        Page<Item> page = itemService.getItemPage(categoryId,request);
        String url= "#";
        return PageNaviButton.getPageINF(page,url);
    }
    @PostMapping("/searchFilter/{categoryId}")
    public String searchFilter(@PathVariable("categoryId") Long categoryId) {

        return categoryPageService.getSearchFilter(categoryRepository.findCategoryById2(categoryId),"");
    }

    @PostMapping("/getSearchBarSuggestionList/{categoryId}")
    public ResponseEntity<String> getSearchBarSuggestion(@PathVariable("categoryId") Long categoryId, @RequestBody SearchPageRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        return new ResponseEntity<String>(itemService.getSearchBarSuggestion(categoryId,request), HttpStatus.OK);
    }
    @PostMapping("/itemTabSelect/{iid}/{categoryId}/{i}/{tab_name}")
    public String itemTabSelect(@PathVariable("iid") Long iid,@PathVariable("categoryId") Long categoryId,@PathVariable("i") int i,@PathVariable("tab_name") String tab_name) {
        return itemPageService.getTabOption(iid,categoryId,i,tab_name);
    }
    @PostMapping("/newItemTabSelect/{category_id}/{i}")
    public String newItemTabSelect(@PathVariable("category_id") Long category_id,@PathVariable("i") int i) {
        return itemPageService.getNewItemTabSelect(category_id,i);
    }
    @PostMapping("/itemTabBlock/{iid}")
    public String itemTabBlock(@PathVariable("iid") Long iid) {
        return itemPageService.getEditItemBlock(itemRepository.findItemByIid(iid).orElseThrow(()-> new IllegalStateException(
                "Item does not exists!" )));
    }
    @PostMapping("/newItemTabBlock")
    public String newItemTabBlock() {
        return itemPageService.getNewItemBlock();
    }

    @PostMapping("/itemBlock/{iid}")
    public String itemBlock(@PathVariable("iid") Long iid){
        return itemPageService.getEditItemBlock(itemRepository.findItemByIid2(iid));
    }
}
