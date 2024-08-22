package uow.bbsc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import uow.bbsc.web.data.shop.ShopPage;
import uow.bbsc.web.page.HTMLPacker;
import uow.bbsc.web.page.item.ItemPageService;
import uow.bbsc.web.page.shop.ShopPageService;

@AllArgsConstructor
@RestController
@RequestMapping("/shop")
public class ShopPageController {
    private final HTMLPacker packer;
    private final ItemPageService itemPageService;
    private final ShopPageService shopPageService;
    @RequestMapping("/all_shops")
    public String allShops(@RequestParam(required = false,defaultValue="1") int pageNumber ,
                           @RequestParam(required = false,defaultValue="ASC") Sort.Direction sortDirection,
                           @RequestParam(required = false,defaultValue="name") String sortBy
    ){
        ShopPage shopPage = new ShopPage(pageNumber-1,48,sortDirection,sortBy);
        return packer.getHTML(
                shopPageService.getShopPage(shopPage),
                "All Shops",
                "<script src='/js/shopListPage.js'></script>");}

    @RequestMapping("/myShop")
    public String my_shop(){return packer.getHTML(shopPageService.getMyShop(packer.getUserId()),"My Shop",
            "<link rel=\"stylesheet\" href=\"/css/shopEdit.css\">" +
                    "<script src='/js/updateShop.js'></script>");}
    @RequestMapping("/{sid}/my_item")
    public String myShopItem(@PathVariable("sid")Long sid){return packer.getHTML(itemPageService.getMyShopItem(sid,false),"My Shop",
            "<link rel=\"stylesheet\" href=\"/css/itemEdit.css\">" +
                    "<script src='/js/updateItem.js'></script>");}

    @RequestMapping("/shop_register")
    public String shopRegister(){
        return packer.getHTML(shopPageService.getShopRegisterPage(),
                "Shop Register",
                "<script src='/js/shopRegister.js'></script>");
    }

    @RequestMapping("/{sid}")
    public String shopView(@PathVariable("sid")Long sid){return packer.getHTML(shopPageService.getShopView(sid,packer.getUserId()),"My Shop",
            "<link rel=\"stylesheet\" href=\"/css/shopView.css\">" +
                    "");}
}
