package uow.bbsc.web.page.shop;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.favourites.Favourites;
import uow.bbsc.web.data.favourites.FavouritesRepository;
import uow.bbsc.web.data.item.ItemPage;
import uow.bbsc.web.data.payCart.PayCart;
import uow.bbsc.web.data.payCart.PayCartRepository;
import uow.bbsc.web.data.shop.Shop;
import uow.bbsc.web.data.shop.ShopPage;
import uow.bbsc.web.data.shop.ShopRepository;
import uow.bbsc.web.data.shop.ShopService;
import uow.bbsc.web.page.HTMLComponents;
import uow.bbsc.web.page.PageNaviButton;
import uow.bbsc.web.page.item.ItemPageService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class ShopPageService extends HTMLComponents{
    private final ShopService shopService;
    private final ShopRepository shopRepository;
    private final PayCartRepository payCartRepository;
    private final FavouritesRepository favouritesRepository;
    private final ItemPageService itemPageService;

    /** Return the shop page
     *
     * @param id
     * @return
     */
    public String getMyShop(Long id){
            String content = "";
            content += updateShopPage(id)+"";
            return content;
    }

    public String getAllShop() {
        String content = "";
        content += updateShopPage()+"";
        return content;
    }
    /** Return page for register shop
     *
     * @return
     */
    public String getShopRegisterPage() {

        return "<div class=\"container\">" +
                "<form class=\"form-signin \" id='form-register-shop' action='/registration/shop'>\n" +
                "<h2 class=\"form-signin-heading \">Shop Register</h2><br>\n" +
                "  <input class=\"form-control\" type=\"text\" id=\"name\" name=\"name\" value=\"\" placeholder=\"shop name\" required><br>\n" +
                "  <input class=\"form-control\" type=\"text\" id=\"address\" name=\"address\" value=\"\" placeholder=\"address\" required><br>\n" +
                "  <input class=\"form-control\" type=\"email\" id=\"email\" name=\"email\" value=\"\" placeholder=\"email\" required><br>\n" +
                "  <input class=\"form-control\" type=\"text\" id=\"tel\" name=\"tel\" value=\"\" placeholder=\"tel\" required><br>\n" +
                "<div class='form-check'>" +
                "<input type=\"checkbox\" class=\"form-check-input\" id=\"agree\" required>" +
                "<label class=\"form-check-label\" for=\"agree\" required>I agree your shop!</label>" +
                "</div><br/>" +
                "  <input class=\"btn btn-lg btn-primary btn-block\" type=\"submit\" value=\"Sign Up\">" +
                "</form>" +
                "</div>";
    }

    /** Get shop list page
     *
     * @param shopPage
     * @return
     */
    public String getShopPage(ShopPage shopPage){
        Page<Shop> page = shopService.getShops(shopPage);
        List<Shop> list = page.getContent();
        String content="<div class='container'><div class='row item-list'>";
        for(int i = 0;i < list.size();i++) {

            content+=getShopBlock(list.get(i));
        }
        content+="</div></div>" ;
        String url = "/shop/all_shops?sortBy="+shopPage.getSortBy()+"&sortDirection=";
        if(shopPage.getSortDirection() == Sort.Direction.ASC)url+="ASC";
        else url+="DESC";
        content+= "<div class='pageINF'>"+PageNaviButton.getPageINF(page,url)+"</div>";
        return  content;
    }

    /** Get the list of shops a user
     *
     * @param shopPage
     * @param id
     * @return
     */
    public String getUserShopPage(ShopPage shopPage,Long id){
        Optional<List<Shop>> shops = shopRepository.findShopById(id);
        if(!shops.isPresent())
            return "<h2>This user do not own a shop.</h2>";
        List<Shop> list = shops.get();
        String content="<div class='container'><div class='row item-list'>";
        for(int i = 0;i < list.size();i++) {
            content+=getShopBlock(list.get(i));
        }
        content+="</div></div>" ;
        return  content;
    }

    /** Return a shop's info block
     *
     * @param shop
     * @return
     */
    public String getShopBlock(Shop shop){
        String block = "<div class='col-xxl-1 col-xl-2 col-lg-3 col-md-4 col-ms-6 col-xs-12 img-thumbnail item '>" +
                " <div class='item-block'>"+
                "  <img src='";
        block+=shop.getIcon();
        LocalDate today = LocalDate.now();
        Long days=Duration.between(shop.getRegistered_date().atStartOfDay(),today.atStartOfDay()).toDays();
        String joined_days = ""+days+" day";
        if(days>1L)
            joined_days +="s";
        block+="'  class=\"img-responsive img-fluid icon img-icon\" onclick='window.location.href = \"/shop/"+shop.getSid()+"\";'>" +
                " </div>" +
                " <div class='item-user-title'>" +
                "  <div class='item-user-icon'>" +
                "  <img src=" +shop.getIcon()+" class='img-icon' onclick='window.location.href = \"/shop/"+shop.getSid()+"\";'>" +
                "  </div>" +
                "  <div class='item-user-title-name'>" +
                "   <div class='item-user-name' onclick='window.location.href = \"/shop/"+shop.getSid()+"\";'>" +shop.getName()+"</div>"+
                "   <div >joined " +joined_days+"</div>"+
                "  </div>" +
                " </div>" +
                "</div>" +
                "";
        return block;
    }

    /** Return the shop page
     *
     * @param id
     * @return
     */
    public String updateShopPage(Long id){
        String content = "<div class='container'>";
        content +="<nav style='padding: 1.5rem;' aria-label='breadcrumb'>" +
                " <ol class='breadcrumb'>" +
                "  <li class=\"breadcrumb-item active\"><a href='/shop/all_shops'>All Shop</a></li>" +
                "  <li class='breadcrumb-item active alert-heading ' aria-current='page'>Manage all shop</li>" +
                " </ol>" +
                "</nav>" ;
        List<Shop> shopList = shopRepository.findShopById2(id);
        if(shopList.size()==0)
            content += "<h2 id='h2'>You do not have any shop.</h2>";
        else
        for(int i = 0; i < shopList.size();i++){
            content +=getEditShopBlock(shopList.get(i),false);
        }
        content +="</div>";
        content +=
                "<div class='btn-floating'>" +
                        " <a class=\"float-btn\" onclick='addShop()'>\n" +
                        "  <i class=\"category-icon-float\">" +
                        "   <svg class='shop-icon-add' xmlns=\"http://www.w3.org/2000/svg\" width=\"60%\" height=\"100%\" fill=\"currentColor\" class=\"bi bi-bookmark-plus\" viewBox=\"0 0 16 16\">\n" +
                        "    <path d=\"M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM9 5.5V7h1.5a.5.5 0 0 1 0 1H9v1.5a.5.5 0 0 1-1 0V8H6.5a.5.5 0 0 1 0-1H8V5.5a.5.5 0 0 1 1 0z\"/>\n" +
                        "   </svg>" +
                        "  </i>\n" +
                        " </a>" +
                        "</div>";
        return content;
    }
    /** Return the admin shop page
     *
     * @return
     */
    public String updateShopPage(){
        String content = "<div class='container'>";
        content +="<nav style='padding: 1.5rem;' aria-label='breadcrumb'>" +
                " <ol class='breadcrumb'>" +
                "  <li class=\"breadcrumb-item active\"><a href='/shop/all_shops'>All Shop</a></li>" +
                "  <li class='breadcrumb-item active alert-heading ' aria-current='page'>Manage all shop</li>" +
                " </ol>" +
                "</nav>" ;
        List<Shop> shopList = shopRepository.findAll();
        if(shopList.size()==0)
            content += "<h2 id='h2'>You do not have any shop.</h2>";
        else
        for(int i = 0; i < shopList.size();i++){
            content +=getEditShopBlock(shopList.get(i),true);
        }
        content +="</div>";
        content +=
                "<div class='btn-floating'>" +
                        " <a class=\"float-btn\" onclick='addShop()'>\n" +
                        "  <i class=\"category-icon-float\">" +
                        "   <svg class='shop-icon-add' xmlns=\"http://www.w3.org/2000/svg\" width=\"60%\" height=\"100%\" fill=\"currentColor\" class=\"bi bi-bookmark-plus\" viewBox=\"0 0 16 16\">\n" +
                        "    <path d=\"M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM9 5.5V7h1.5a.5.5 0 0 1 0 1H9v1.5a.5.5 0 0 1-1 0V8H6.5a.5.5 0 0 1 0-1H8V5.5a.5.5 0 0 1 1 0z\"/>\n" +
                        "   </svg>" +
                        "  </i>\n" +
                        " </a>" +
                        "</div>";
        return content;
    }

    /** For editing a shop
     *
     * @param shop
     * @return
     */
    public String getEditShopBlock(Shop shop,boolean isAdmin){
        String block = "<div class=\"input-group alert alert-secondary shopBlock shopBlock-"+shop.getSid()+"\" role=\"alert\">" +
                "<div style='flex: 1 1 auto;width: 1%;padding-right: 0px;' class='col-xxl-10 col-xl-10 col-lg-10 col-md-12 col-ms-12 col-xs-12'>" +
                "<div class=\"row input-group-prepend \">"+
                "<div style='padding-right: 0px;' class=\"col-xxl-9 col-xl-9 col-lg-9 col-md-12 col-ms-12 col-xs-12\">"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Shop name</span>\n" +
                "<input type='text' class = 'shop-col form-control form-control-lg' id='shop-name-"+shop.getSid()+"' value='"+shop.getName()+"' placeholder='shop name' required>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Shop address</span>\n" +
                "<input type='text' class = 'shop-col form-control form-control-lg' id='shop-address-"+shop.getSid()+"' value='"+shop.getAddress()+"' placeholder='shop address' required>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">email</span>\n" +
                "<input type='text' class = 'shop-col form-control form-control-lg' id='shop-email-"+shop.getSid()+"' value='"+shop.getEmail()+"' placeholder='shop email' required>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">tel</span>\n" +
                "<input type='tel'  pattern=\"[0-9]{20}\" size='20' class = 'shop-col form-control form-control-lg' id='shop-tel-"+shop.getSid()+"' value='"+shop.getTel()+"' placeholder='shop tel' required>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">icon</span>\n" +
                "<input  style='display:none' type='text' class = 'form-control' id='shop-icon-"+shop.getSid()+"' value='"+shop.getIcon()+"'>" +
                "<form method=\"POST\" enctype=\"multipart/form-data\" id=\"UploadForm-"+shop.getSid()+"\" style=\"margin-bottom: 0px;\">\n" +
                "     <div class='sid' style='display:none' >"+shop.getSid()+"</div>" +
                "     <input class='shop-col shop-upload-icon shop-upload-icon-"+shop.getSid()+" form-control form-control-lg' id='iconUploadFile"+shop.getSid()+"' type=\"file\" name=\"uploadFiles\"  multiple=\"multiple\" accept=\".png\" />\n" +
                "</form>" +
                "</div>" +
                "</div>" +
                "<div style='padding-right: 0px;' class=\"col-xxl-3 col-xl-3 col-lg-3 col-md-12 col-ms-12 col-xs-12\">\n" +
                "<div class=\"card\" style=\"width: 100%;\">\n" +
                "    <p style=\"text-align: center;padding-top: 0.5rem;\">Icon preview</p>\n" +
//                "<hr/>" +
                "  <img src=\""+shop.getIcon()+"\" class=\"img-icon card-img-top img-"+shop.getSid()+"\" alt=\"...\">\n" +
                "</div>"+
                "</div>"+
                "</div>"+
                "";
        block +=         "</div>" +
                "<div style='margin-left:10px;display: grid;' class='col-xxl-2 col-xl-2 col-lg-2 col-md-2 col-ms-12 col-xs-12 input-group-prepend'>" +
                " <button style='width:100%;height: fit-content;margin-bottom:-1px;' type=\"button\" class=\"btn btn-outline-danger\" onclick='deleteShop(" + shop.getSid() + ")'>\n" +
                "  <span aria-hidden=\"true\">Delete</span>\n" +
                " </button>" ;
        if(isAdmin)
            block +=        " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-shop btn btn-outline-primary\" onclick=\"window.location.href ='./"+shop.getSid()+"/item';\">\n" ;
        else
            block +=        " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-shop btn btn-outline-primary\" onclick=\"window.location.href ='"+shop.getSid()+"/my_item';\">\n" ;
        block +=        "  <span aria-hidden=\"true\">Items</span>\n" +
                " </button>" +
                " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-shop btn btn-outline-info\" onclick='refreshShop(" + shop.getSid() + ")'>\n" +
                "  <span aria-hidden=\"true\">Refresh</span>\n" +
                " </button>" +
                " <button style='width:100%;height: fit-content;' type=\"submit\" class=\"btn-shop btn btn-outline-success\" onclick='updateShop(" + shop.getSid() + ")'>\n" +
                "  <span aria-hidden=\"true\">Save</span>\n" +
                " </button>" +
                "</div>" +
                "</div>";
        return block;
    }

    /** Show shop detail
     *
     * @param sid
     * @param id
     * @return
     */
    public String getShopView(Long sid,Long id) {
        Optional<Shop> shopOptional = shopRepository.findShopsBySid(sid);
        if(!shopOptional.isPresent())
            return "shop not found";
        Shop shop = shopOptional.get();
        String shopView = "<div class='container'>";

        String title = "<h1>Shop information</h1>";
        String body = "";

        body +=
                "        <div class=\"row\">\n" +
                        HTMLCard("",
                                "<div class=\"align-middle col-lg-8 col-md-12 col-ms-12 mb-4\">\n" +
                                     "    <h1>"+shop.getName()+"</h1>\n" +
                                     "    <h3>&nbsp;</h3>\n" +
                                     "    <p>Email :"+shop.getEmail()+"</p>\n" +
                                     "    <p>Tel :"+shop.getTel()+"</p>\n" +
                                     "    <p>Registered date: "+shop.getRegistered_date()+"</p>\n" +
                                     "  </div>\n" +
                                     "  <div class = \"align-middle col-md-4 col-ms-12 ratio-1-1\">\n" +
                                     "   <img class='img-icon ' src=\""+shop.getIcon()+"\" >\n" +
                                     " </div>\n",
                                "mb-4",
                                "col-12 row") +
                "        <div class='col-lg-7 col-md-12 col-ms-12'>\n" +
                        HTMLCard("<h3>Description:</h3>",
                                "          <p>"+shop.getDescription()+"<p>\n",
                                "mb-3","")+
                "        </div>\n";

        body += "        <div class='col-lg-5 col-md-12 col-ms-12'>\n" +
                getUserCardINF(shop.getCustomer(),"") +
                "        </div>\n"+
                "       </div>\n";
        shopView += HTMLCard(title,body,"mb-3","");
        title = "<h1>Selling item recently</h1>";
        body=   "      <div class=\"row\">\n" +
                "        <div class=\"col-12\">\n" ;
        ItemPage itemPage = new ItemPage();
        itemPage.setPageSize(16);
        itemPage.setSortBy("update_time");
        body += itemPageService.getShopItemPage(itemPage,id,shop.getSid());

        body+=  "        </div>\n" +
                "      </div>";
        shopView += HTMLCard(title,body,"mb-4","");
        shopView += "</div>";
        return shopView;
    }

    /** Return user info
     *
     * @param customer
     * @param username
     * @return
     */
    public String getUserCardINF(Customer customer, String username) {
        Long id =customer.getId();
        Optional<Shop> shopOptional = shopRepository.findShopsById(id);
        String rate = "no rating";
        double rating = 0;
        int qty = 0;
        if(shopOptional.isPresent()) {
            List<PayCart> payCarts = payCartRepository.findPayCartById2(id);
            for (int i = 0; i < payCarts.size(); i++) {
                rating+=payCarts.get(i).getQty()*payCarts.get(i).getRate();
                qty+=payCarts.get(i).getRate();
            }
            rating = rating/qty;
            rate = Math.round( rating*10)/10+" / 5";
        }
        List<Favourites> favouritesList= favouritesRepository.findAllFavouritesItemById(id);
        String body = "";
        String title =
                "  <img style='border-radius: 50%;align-self: center;width: 50%' src=\""+ customer.getIcon()+"\" class=\"img-icon card-img-top\" />" ;
        body +="<h2 style='font-size: 30px;line-height: 38px;text-align: left;font-weight: 400;'>"+customer.getName()+"</h2>";
        body +="<p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>Money : "+customer.getMoney()+"</h2>";
        body +="<p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>Rating : "+rate+"</h2>";
        body +="<p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>Comments : "+"(comments number)"+"</h2>";
        body +="<p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>Favourites : "+favouritesList.size()+"</h2>";
        body +="";
        body +="";

        body +="<p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>"+customer.getDescription()+"</h2>";
        if(username.equals(customer.getUsername()))
            title +="<button style='position:absolute;' type=\"button\" class=\"btn close\" onclick='window.location.href=\"/user/setting/profile\";'>\n" +
                    "   <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"32\" height=\"32\" fill=\"currentColor\" class=\"bi bi-gear\" viewBox=\"0 0 16 16\">\n" +
                    "       <path d=\"M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z\"/>\n" +
                    "       <path d=\"M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z\"/>\n" +
                    "   </svg>" +
                    "</button>";
        return HTMLCard(title,body,"","");
    }

}
