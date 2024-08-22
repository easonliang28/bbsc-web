package uow.bbsc.web.page.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.data.customer.CustomerService;
import uow.bbsc.web.data.favourites.Favourites;
import uow.bbsc.web.data.favourites.FavouritesRepository;
import uow.bbsc.web.data.item.ItemPage;
import uow.bbsc.web.data.payCart.PayCart;
import uow.bbsc.web.data.payCart.PayCartRepository;
import uow.bbsc.web.data.shop.Shop;
import uow.bbsc.web.data.shop.ShopPage;
import uow.bbsc.web.data.shop.ShopRepository;
import uow.bbsc.web.page.item.ItemPageService;
import uow.bbsc.web.page.shop.ShopPageService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerPageService {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final ShopPageService shopPageService;
    private final ItemPageService itemPageService;
    private final PayCartRepository payCartRepository;
    private final ShopRepository shopRepository;
    private final FavouritesRepository favouritesRepository;


    // Get register page
    public String getRegister(){
        String content;
        content = "<div class='container body-content'><div class='center-block form-group'><br>" +
                "<form class=\"form-signin form-group\" id='form-register' >\n" +
                "<h2 class=\"form-signin-heading \">Register</h2><br>\n" +
                "  <input class=\"form-control\" type=\"text\" id=\"name\" name=\"name\" value=\"\" placeholder=\"username\" required><br>\n" +
                "  <input class=\"form-control\" type=\"text\" id=\"address\" name=\"address\" value=\"\" placeholder=\"address\" required><br>\n" +
                "  <input class=\"form-control\" type=\"text\" id=\"email\" name=\"email\" value=\"\" placeholder=\"email\" required><br>\n" +
                "  <input class=\"form-control\" type=\"text\" id=\"tel\" name=\"tel\" value=\"\" placeholder=\"tel\" required><br>\n" +
                "  <input class=\"form-control\" type=\"text\" id=\"DOB\" name=\"DOB\" value=\"\" placeholder=\"2000-12-31(YYYY-MM-DD)\" pattern=\"[0-9]{4}-[0-9]{2}-[0-9]{2}\" required><br>\n" +
                "  <input class=\"form-control\" type=\"text\" id=\"pwd\" name=\"pwd\" value=\"\" placeholder=\"password\" required><br>\n" +
                "  <input class=\"btn btn-lg btn-primary btn-block\" type=\"submit\" value=\"Submit\">" +
                "</form>" +
                "</div></div>";
        return content;
    }

    // Get register dropdown menu
    public String getNaviRegister(){
        return
                "        <div class=\"dropdown  nav-item\">\n" +
                        "          <a href=\"#\" data-toggle=\"dropdown\" class=\"dropdown-toggle btn btn-lg btn-block btn-primary sign-up-btn\" " +
                        "id=\"navbarRegisterDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">Sign up</a>\n" +

                        "<div class='dropdown-menu dropdown-menu-right' aria-labelledby=\"navbarRegisterDropdown\"><div class='action-form'>" +
                        "<form class=\"form-signin \" id='form-register' action='registration'>\n" +
                        "<h2 class=\"form-signin-heading \">Register</h2><br>\n" +
                        "  <input class=\"form-control\" type=\"text\" id=\"name\" name=\"name\" value=\"\" placeholder=\"username\" required><br>\n" +
                        "  <input class=\"form-control\" type=\"text\" id=\"address\" name=\"address\" value=\"\" placeholder=\"address\" required><br>\n" +
                        "  <input class=\"form-control\" type=\"email\" id=\"email\" name=\"email\" value=\"\" placeholder=\"email\" required><br>\n" +
                        "  <input class=\"form-control\" type=\"text\" id=\"tel\" name=\"tel\" value=\"\" placeholder=\"tel\" required><br>\n" +
                        "  <input class=\"form-control\" type=\"text\" id=\"DOB\" name=\"DOB\" value=\"\" placeholder=\"2000-12-31(YYYY-MM-DD)\" pattern=\"[0-9]{4}-[0-9]{2}-[0-9]{2}\" required><br>\n" +
                        "  <input class=\"form-control\" type=\"text\" id=\"pwd\" name=\"pwd\" value=\"\" placeholder=\"password\" required><br>\n" +
                        "<div class='form-check'>" +
                        "<input type=\"checkbox\" class=\"form-check-input\" id=\"agree\" required>" +
                        "<label class=\"form-check-label\" for=\"agree\" required>I agree you!</label>" +
                        "</div><br/>" +
                        "  <input class=\"btn btn-lg btn-primary btn-block\" type=\"submit\" value=\"Sign Up\">" +
                        "</form>" +
                        "</div>" +
                        "</div>"+
                        "        </div>\n" ;
    }

    // Get login dropdown menu
    public String getNaviLogin(){
        return
                "          <a href=\"#\" data-toggle=\"dropdown\" class=\"btn-log nav-link dropdown-toggle mr-4\" " +
                        "id=\"navbarLoginDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">Login</a>\n" +
                        "          <div class=\"dropdown-menu action-form \" aria-labelledby=\"navbarLoginDropdown\">\n" +
                        "            <form class=\"form-signin\" action=\"/loginAction\" method=\"post\">\n" +
                        "              <!--<div class=\"or-seperator\"><b>or</b></div> -->\n" +
                        "              <div class=\"form-group\">\n" +
                        "                <input type=\"text\" class=\"form-control\" placeholder=\"Email\" id=\"username\" name=\"username\" required autofocus>\n" +
                        "              </div>\n" +
                        "              <div class=\"form-group\">\n" +
                        "                <input type=\"password\" id=\"password\" name=\"password\" class=\"form-control\" placeholder=\"Password\" required>\n" +
                        "              </div>\n" +
                        "              <input type=\"submit\" class=\"btn btn-primary btn-block\" value=\"Login\">\n" +
                        "            </form>\n" +
                        "          </div>\n" +
                        "        </div>\n" ;
    }

    // Let users edit their profile
    public String getUserProfileEdit(String username){
        Customer customer = customerRepository.findCustomerByEmail2(username);
        String content = "<div class='container-lg'>";
        content +="<nav style='padding: 1.5rem;' aria-label='breadcrumb'>" +
                " <ol class='breadcrumb'>" +
                "  <li  class=\"breadcrumb-item active\"><a href='/user'>User</a></li>" +
                "  <li  class=\"breadcrumb-item active\"><a href='/user/setting'>Setting</a></li>" +
                "  <li class='breadcrumb-item active alert-heading ' aria-current='page'>Profile</li>" +
                " </ol>" +
                "</nav>" ;
        content +="<div class=\"card\" style=\"width: 100%;padding : 1rem;\">\n" +
                "  <img src=\""+ customer.getIcon()+"\" class=\"img-icon card-img-top\" alt=\"...\">\n" +
                "  <div class=\"card-body\">\n" +
                getUserCardEditINF(customer) +
                "  </div>\n" +
                "</div>";
        content +="</div>";
        return content;
    }

    // The inner content of user profile editing interface
    private String getUserCardEditINF(Customer customer) {
        Long id =customer.getId();
        String card = "";
        card +=         "<div style=\"float: right;\" class=\"mb-3\">\n" +
                        "<input type='text' class = 'd-none form-control' id='user-icon' value='"+customer.getIcon()+"'>" +
                        "<form method=\"POST\" enctype=\"multipart/form-data\" id=\"UploadForm\" style=\"margin-bottom: 0px;\">\n" +
                        "   <div type='text' class='d-none form-control' id='user-id'>"+id+"</div>" +
                        "    <label for='iconUploadFile' class='btn btn-primary'>Upload photo" +
                        "     <input  class='d-none user-col user-upload-icon form-control form-control-lg' id='iconUploadFile' type=\"file\" name=\"uploadFiles\"  multiple=\"multiple\" accept=\".png\"/>\n" +
                        "    </label>" +
                        "</form>" +
                        "</div>" ;
        card +="<div class=\"input-group mb-3\">\n" +
                        "  <span class=\"input-group-text\">Username</span>\n" +
                        "<input type='text' class = 'user-col form-control form-control-lg' id='user-name' value='"+customer.getName()+"' placeholder='Username'>" +
                        "</div>";
        card +="<div class=\"input-group mb-3\">\n" +
                        "  <span class=\"input-group-text\">Address</span>\n" +
                        "<input type='text' class = 'user-col form-control form-control-lg' id='user-address' value='"+customer.getAddress()+"' placeholder='Address'>" +
                        "</div>";
        card +="<div class=\"input-group mb-3\">\n" +
                        "  <span class=\"input-group-text\">Email</span>\n" +
                        "<input type='email' class = 'user-col form-control form-control-lg' id='user-email' value='"+customer.getEmail()+"' placeholder='Email'>" +
                        "</div>";
        card +="<div class=\"input-group mb-3\">\n" +
                        "  <span class=\"input-group-text\">Date of birth</span>\n" +
                        "<input type='text' class = 'user-col form-control form-control-lg' id='user-dob' value='"+customer.getDob().toString()+"' placeholder='date of birth' pattern=\"[0-9]{4}-[0-9]{2}-[0-9]{2}\">" +
                        "</div>";
        card +="<div class=\"input-group mb-3\">\n" +
                        "  <span class=\"input-group-text\">Username</span>\n" +
                        "<input type='tel' class = 'user-col form-control form-control-lg' id='user-tel' value='"+customer.getTel()+"' placeholder='tel'>" +
                        "</div>";
        card +="<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Description</span>\n" +
                "<input type='tel' class = 'user-col form-control form-control-lg' id='user-description' value='"+customer.getDescription()+"' placeholder='description'>" +
                "</div>";
        card += " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-user btn btn-outline-primary mb-2\" onclick='refreshUser(" + id + ")'>\n" +
                        "  <span aria-hidden=\"true\">Refresh</span>\n" +
                        " </button>";
        card += " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-user btn btn-success\" onclick='saveUser(" + id + ")'>\n" +
                        "  <span aria-hidden=\"true\">Save</span>\n" +
                        " </button>";
        card += "";

        return card;
    }

    // Get user profile
    public String getUserProfileView(String username,Long id) {
        Customer customer = customerRepository.findCustomerById(id);
        return getProfile(username, customer);
    }

    // Get user profile
    public String getUserProfile(String username) {
        Customer customer = customerRepository.findCustomerByEmail2(username);
        return getProfile(username, customer);
    }

    // Get the view for user profile
    private String getProfile(String username, Customer customer) {
        String content = "<div class='container-lg'>";

        content +="<img class='img-fluid mb-3' src='../profile_banner.jpeg'/>";
        content +="<div class='row'>";
        content +="<div class='col-xxl-3 col-xl-3 col-lg-4 col-md-12 col-ms-12 col-xs-12'>";
        content +=getUserCardINF(customer,username) ;
        content +="</div>";
        content +="<div class='col-xxl-9 col-xl-9 col-lg-8 col-md-12 col-ms-12 col-xs-12'>";
        content +=getUserINF(customer);
        content +="</div>";
        content +="</div>";
        content +="</div>";
        return content;
    }

    // Get user info
    private String getUserINF(Customer customer) {
        Long id =customer.getId();
        String card = "";
        card +="";
        card +="<div class='col-12'>";
        card +=getCard(
                "   <h2 style='font-size: 30px;line-height: 38px;text-align: left;font-weight: 400;'>Description</h2>",
                "   <p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>"+customer.getDescription()+"</h2>"
        );


        card +="</div>";

        card +="<div class='col-12'>";
        ShopPage shopPage = new ShopPage();
        shopPage.setPageSize(1000);
        card +=getCard("<h3>Shop</h3>","   <div class='user-shop row'>"+shopPageService.getUserShopPage(shopPage,id)+"</div>");
        card +=getCard("<h3>Recently item in cart</h3>","   <div class='user-item row'>"+itemPageService.getUserItemPage(new ItemPage(),id)+"</div>");
        card +="</div>";

        return card;
    }

    // Get user info for the user card
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

        if(username.equals(customer.getUsername()))
        title +="<button style='position:absolute;' type=\"button\" class=\"btn close\" onclick='window.location.href=\"/user/setting/profile\";'>\n" +
                "   <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"32\" height=\"32\" fill=\"currentColor\" class=\"bi bi-gear\" viewBox=\"0 0 16 16\">\n" +
                "       <path d=\"M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z\"/>\n" +
                "       <path d=\"M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z\"/>\n" +
                "   </svg>" +
                "</button>";
        return getCard(title,body);
    }

    // return card with set content
    public String getCard(String title, String body){
        return "<div class=\"card mb-3\" style=\"width: 100%;padding : 1rem;\">\n" +title+
                "  <div class=\"card-body\">\n"+body+
                "</div>"+
                "</div>";

    }

}
