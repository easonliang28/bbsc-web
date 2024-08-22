package uow.bbsc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uow.bbsc.web.data.item.ItemPage;
import uow.bbsc.web.page.HTMLPacker;
import uow.bbsc.web.page.customer.CustomerPageService;
import uow.bbsc.web.page.favourites.FavouritesPageService;
import uow.bbsc.web.page.item.ItemPageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@RestController
public class PageController {
    private final HTMLPacker packer;
    private final CustomerPageService customerPageService;
    private final FavouritesPageService favouritesPageService;
    private final ItemPageService itemPageService;

    @RequestMapping
    public String index(){
        ItemPage itemPage= new ItemPage();
        itemPage.setSortBy("update_time");
        itemPage.setSortDirection(Sort.Direction.DESC);
        return packer.getHTML(itemPageService.getItemPage(itemPage,packer.getUsername()),"Home Page","");
    }

    @PostMapping("/getUsername")
    public String Username(){return packer.getUsername();}


    @RequestMapping("/register")//register for customer
    public String register(){return packer.getHTML(customerPageService.getRegister(),"Register");}

    @RequestMapping("/redirectPreviousPage")
    public void redirectPreviousPage(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(request.getHeader("referer"));
    }

    @RequestMapping("/favourites")
    public String favourites(@RequestParam(required = false,defaultValue="1") int pageNumber,
                             @RequestParam(required = false,defaultValue="DESC") Sort.Direction sortDirection,
                             @RequestParam(required = false,defaultValue="time") String sortBy){
        ItemPage itemPage = new ItemPage(pageNumber-1,48,sortDirection,sortBy);
        return packer.getHTML(favouritesPageService.getPage(packer.getUserId(),itemPage),"Favourites");}

    @RequestMapping("/chat")
    public String chat(){return packer.getHTML("Coming soon...","Chat Page");}

}
