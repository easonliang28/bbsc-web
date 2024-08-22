package uow.bbsc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uow.bbsc.web.data.payCart.PayCartPage;
import uow.bbsc.web.page.HTMLPacker;
import uow.bbsc.web.page.payCart.PayCartPageService;

@RestController
@RequestMapping("/user/")
@AllArgsConstructor
public class PayCartPageController {
    private final PayCartPageService payCartPageService;
    private final HTMLPacker packer;
    @RequestMapping("payCart")
    // Get cart page
    public String myCart(){
        return packer.getHTML(payCartPageService.getMyPayCart(packer.getUserId()),"Register",
                packer.getCssImpl("payCart")+
                packer.getCssImpl("item")+
                packer.getJsImpl("payCart"));
    }
    @RequestMapping("itemReview")
    // Get cart page
    public String itemReview(
            @RequestParam(required = false,defaultValue="") String tab,
            @RequestParam(required = false,defaultValue="1") int pageNumber,
            @RequestParam(required = false,defaultValue="DESC") Sort.Direction sortDirection,
            @RequestParam(required = false,defaultValue="pay_time") String sortBy){
        PayCartPage payCartPage= new PayCartPage(pageNumber-1,48,sortDirection,sortBy);
        return packer.getHTML(payCartPageService.getItemReview(payCartPage,packer.getUserId()),"Register",
                packer.getCssImpl("item")+
                packer.getJsImpl("item")+
                packer.getJsImpl("itemReview"));
    }
}
