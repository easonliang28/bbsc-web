package uow.bbsc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uow.bbsc.web.data.category.CategoryService;
import uow.bbsc.web.data.comment.CommentService;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.data.customer.CustomerService;
import uow.bbsc.web.data.favourites.Favourites;
import uow.bbsc.web.data.favourites.FavouritesRepository;
import uow.bbsc.web.data.item.ItemRepository;
import uow.bbsc.web.data.item.ItemService;
import uow.bbsc.web.data.payCart.PayCartService;
import uow.bbsc.web.page.HTMLPacker;
import uow.bbsc.web.page.category.CategoryRegistrationRequest;
import uow.bbsc.web.page.comment.CommentRegistrationRequest;
import uow.bbsc.web.page.comment.CommentRelyRequest;
import uow.bbsc.web.page.customer.CustomerRegistrationRequest;
import uow.bbsc.web.page.item.ItemRegistrationRequest;
import uow.bbsc.web.page.payCart.PayCartRegistrationRequest;
import uow.bbsc.web.page.shop.ShopRegistrationRequest;
import uow.bbsc.web.page.shop.ShopRegistrationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private final CustomerService customerService;
    private final ShopRegistrationService shopRegistrationService;
    private final CategoryService categoryService;
    private final ItemService itemService;
    private final PayCartService payCartService;
    private final FavouritesRepository favouritesRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final HTMLPacker packer;
    private final CommentService commentService;

    @PostMapping()
    public String register(@RequestBody CustomerRegistrationRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        try{
            return customerService.register(request);
        } catch (Exception e) {
            response.sendError(400,e.getMessage());
        }
        return "Customer registered(never run to this line)";
    }
    @PostMapping("/shop")
    public String registerShop(@RequestBody ShopRegistrationRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        try{
            return shopRegistrationService.register(request);
        } catch (Exception e) {
            response.sendError(400,e.getMessage());
        }
        return "shop registered(never run to this line";
    }
    @PostMapping("/category")
    public String registerCategory(@RequestBody CategoryRegistrationRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        try{
            return categoryService.register(request);
        } catch (Exception e) {
            response.sendError(400,e.getMessage());
        }
        return "category registered(never run to this line";
    }
    @PostMapping("/item")
    public String registerItem(@RequestBody ItemRegistrationRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        try{
            return itemService.register(request);
        } catch (Exception e) {
            response.sendError(400,e.getMessage());
        }
        return "item registered(never run to this line";
    }
    @PostMapping("/payCart")
    public String registerPayCart(@RequestBody PayCartRegistrationRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        try{
            return payCartService.register(request);
        } catch (Exception e) {
            response.sendError(400,e.getMessage());
        }
        return "item registered(never run to this line";
    }
    @PostMapping("/favourites/{iid}")
    public Long registerFavourites(@PathVariable("iid")Long iid) throws IOException {
        Favourites favourites = new Favourites(customerRepository.findCustomerById(packer.getUserId()),
                itemRepository.findItemByIid2(iid),
                LocalDateTime.now());
        favouritesRepository.save(favourites);
        return favourites.getFid();
    }
    @PostMapping("/comment")
    public Long registerComment(@RequestBody CommentRegistrationRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {

        return commentService.newComment(request);
    }
    @PostMapping("/replyComment/")
    public Long registerNewComment(@RequestBody CommentRelyRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {

//        favouritesRepository.save(favourites);
        return commentService.newRelyComment(request);
    }
}
