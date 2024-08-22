package uow.bbsc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.page.HTMLPacker;
import uow.bbsc.web.page.admin.AdminPageService;
import uow.bbsc.web.page.category.CategoryPageService;
import uow.bbsc.web.page.customer.CustomerPageService;
import uow.bbsc.web.page.shop.ShopPageService;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminPageController {
    private final HTMLPacker packer;
    private final CategoryPageService categoryPageService;
    private final AdminPageService adminPageService;
    private final ShopPageService shopPageService;
    private final CustomerPageService customerPageService;
    private final CustomerRepository customerRepository;

    @RequestMapping("/update/category")
    public String adminCategoryUpdate(){
        return packer.getHTML(categoryPageService.updateCategoryPage(),
                "Admin - manage category",
                "<script src='/js/updateCategory.js'></script>" +
                        "<link rel=\"stylesheet\" href=\"/css/categoryEdit.css\">");
    }
    @RequestMapping("/update/shop")
    public String adminShopUpdate(){
        return packer.getHTML(shopPageService.getAllShop(),
                "Admin - manage shop",
                "<link rel=\"stylesheet\" href=\"/css/shopEdit.css\">" +
                        "<script src='/js/updateShop.js'></script>");
    }
    @RequestMapping("/update/{sid}/item")
    public String adminItemUpdate(@PathVariable("sid") Long sid){
        return packer.getHTML(adminPageService.getItemPage(sid),
                "Admin - manage item","" +
                        "<link rel=\"stylesheet\" href=\"/css/itemEdit.css\">" +
                        "<script src='/js/updateItem.js'></script>");
    }
    @RequestMapping("/update/user/{id}")
    public String adminUserUpdate(@PathVariable("id") Long id){
        return packer.getHTML(customerPageService.getUserProfileEdit(customerRepository.findCustomerById(id).getEmail()),
                "Admin - manage user",
                "<link rel=\"stylesheet\" href=\"/css/userEdit.css\">" +
                        "<script src='/js/updateUser.js'></script>");
    }
}
