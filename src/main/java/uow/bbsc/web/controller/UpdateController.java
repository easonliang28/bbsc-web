package uow.bbsc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uow.bbsc.web.data.category.CategoryService;
import uow.bbsc.web.data.customer.CustomerService;
import uow.bbsc.web.data.item.ItemService;
import uow.bbsc.web.data.payCart.PayCartService;
import uow.bbsc.web.data.shop.ShopService;
import uow.bbsc.web.page.HTMLPacker;
import uow.bbsc.web.page.category.CategoryUpdateRequest;
import uow.bbsc.web.page.customer.CustomerAddMoneyRequest;
import uow.bbsc.web.page.customer.CustomerResetPasswordRequest;
import uow.bbsc.web.page.customer.CustomerUpdateRequest;
import uow.bbsc.web.page.item.ItemUpdateRequest;
import uow.bbsc.web.page.payCart.PayCartBuyRequest;
import uow.bbsc.web.page.payCart.PayCartRatingRequest;
import uow.bbsc.web.page.payCart.PayCartUpdateRequest;
import uow.bbsc.web.page.shop.ShopUpdateRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/update")
@AllArgsConstructor
public class UpdateController {

    private final CategoryService categoryService;
    private final ShopService shopService;
    private final ItemService itemService;
    private final CustomerService customerService;
    private final PayCartService payCartService;
    private final HTMLPacker packer;
    @PutMapping("/category")
    public String updateCategory(@RequestBody CategoryUpdateRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        try{
            return categoryService.update(request);
        } catch (Exception e) {
            response.sendError(400,e.getMessage());
        }
        return "category registered(never run to this line";
    }
    @PutMapping("/shop")
    public String updateShop(@RequestBody ShopUpdateRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
            return shopService.update(request);
    }
    @PutMapping("/item")
    public String updateItem(@RequestBody ItemUpdateRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
            return itemService.update(request);
    }
    @PutMapping("/user")
    public String updateItem(@RequestBody CustomerUpdateRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
            return customerService.update(request);
    }
    @PutMapping("/pay_cart")
    public String updatePayCart(@RequestBody PayCartUpdateRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
            return payCartService.update(request);
    }
    @PutMapping("/buy")
    public String updatePayCart(@RequestBody PayCartBuyRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
            return payCartService.buy(request);
    }
    @PutMapping("/addMoney")
    public double updateMoney(@RequestBody CustomerAddMoneyRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
            return customerService.addMoney(packer.getUserId(),request);
    }
    @PutMapping("/rate")
    public double updateRating(@RequestBody PayCartRatingRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
            return payCartService.rate(request);
    }
    @PutMapping("/password")
    public String updatePassword(@RequestBody CustomerResetPasswordRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
            return customerService.resetPassword(request);
    }
}
