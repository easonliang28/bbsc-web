/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uow.bbsc.web.page;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.data.customer.UserRole;
import uow.bbsc.web.data.shop.ShopRepository;
import uow.bbsc.web.page.category.CategoryPageService;
import uow.bbsc.web.page.customer.CustomerPageService;

import java.util.Optional;


@AllArgsConstructor
@Service
public class HTMLPacker {
    private final HeadImpl headImpl;
    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;
    private final CustomerPageService customerPageService;
    private final CategoryPageService categoryPageService;


    /** get login user email from session
     *
     * @return email
     */
    public final String getUsername(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    /** get the user id from email
     *
     * @return user id
     */
    public final Long getUserId(){
        if(isGuest()) return 0L;
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByEmail(getUsername());
        if(optionalCustomer.isPresent())
            return optionalCustomer.get().getId();
        else return 0L;
    }

    public boolean isGuest() {
        return getUsername().equals("anonymousUser");
    }

    public double getMoney(){
        if(!isGuest())
            return getDouble(customerRepository.findCustomerByEmail2(getUsername()).getMoney());
        return 0;
    }

    /** crafting the header block
     *
     * @param title
     * @param javascript
     * @return header
     */
    public String getHeader(String title, String javascript){
        return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "" +
                headImpl.getJquery() +
                headImpl.getSweetalert2() +
                headImpl.getBootstrap() +
                headImpl.getNavi() +
                headImpl.getItem() +
                headImpl.getAnime() +
                headImpl.getAutoComplete()+
                "<title>BBSC - "+title+"</title>\n" +
                javascript +
                "</head>" +
                "<body style='padding-top: 0px'>" +
                "<iframe src=\"/background-anime/gwd_preview_background-anime/index.html\" scrolling=\"no\">\n" +
                "</iframe>" +
                "<div class='wrapper'>";

    }

    /** crafting the footer block
     *
     * @return footer
     */
    public String getFooter(){
         return"</div><footer class=\"footer \">\n" +
                 "<div class=\"footer-basic\">\n" +
                 "        <footer>\n" +
                 "            <div class=\"social\">" +
                 "<a href=\"#\"><i class=\"icon ion-social-instagram\"></i></a>" +
                 "<a href=\"#\"><i class=\"icon ion-social-snapchat\"></i></a>" +
                 "<a href=\"#\"><i class=\"icon ion-social-twitter\"></i></a>" +
                 "<a href=\"#\"><i class=\"icon ion-social-facebook\"></i></a>" +
                 "          </div>\n" +
                 "            <ul class=\"list-inline\">\n" +
                 "                <li class=\"list-inline-item\"><a href=\"#\">Home</a></li>\n" +
                 "                <li class=\"list-inline-item\"><a href=\"#\">Services</a></li>\n" +
                 "                <li class=\"list-inline-item\"><a href=\"#\">About</a></li>\n" +
                 "                <li class=\"list-inline-item\"><a href=\"#\">Terms</a></li>\n" +
                 "                <li class=\"list-inline-item\"><a href=\"#\">Privacy Policy</a></li>\n" +
                 "            </ul>\n" +
                 "            <p class=\"copyright\">Bell Bell Shopping Car © 2021</p>\n" +
                 "        </footer>\n" +
                 "    </div>"+
                 "</footer>" +
                 headImpl.getJquery() +
                "</body></html>";
    }

    /** crafting the nav block
     *
     * @return nav-bar
     */
    public String getNavi(){
        String Navi = "\n" +
                "<nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\n" +
                "  <div class=\"container-fluid\">\n" +
                "    <button style=\"margin-left: 5px;\" class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarTogglerDemo03\" aria-controls=\"navbarTogglerDemo03\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
                "      <span class=\"navbar-toggler-icon\"></span>\n" +
                "    </button>\n" +
                "    <a class=\"navbar-brand\" href=\"/\"><span class=\"h3 mx-1\">Bell Bell Shopping Car</span></a>\n" +
                "    <div class=\"collapse navbar-collapse\" id=\"navbarTogglerDemo03\">\n" +
                "      <ul class=\"navbar-nav me-auto mb-2 mb-lg-0\">\n" +
                "        <li class=\"dropdown nav-item  has-megamenu\">\n" +

                //category navigation
                "          <a href=\"#\" data-toggle=\"dropdown\" class=\"btn-log nav-link dropdown-toggle mr-4 \"" +
                "id=\"navbarCategoryDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">Items Category</a>\n" +
                           categoryPageService.getNaviCategory()+

                "        </li>\n" +
                //shop
                "        <li class=\"nav-item\">\n" +
                "          <a class=\"nav-link\" href=\"/shop/all_shops\" tabindex=\"-1\" aria-disabled=\"true\">Shops</a>\n" +
                "        </li>\n" +

                "      </ul>\n" +
                "      <div class=\"navbar-nav ml-auto action-buttons\">\n" +
                "      <form class=\"nav-item d-flex navi-search-form\" action='/item/search?categoryId=0'>\n" +
                "        <input class=\"form-control me-2 navi-search-bar\" name='keyword' type=\"search\" placeholder=\"Search\" aria-label=\"Search\">\n" +
                "        <input style='display:none;' name='categoryId' type=\"text\" value='0'>\n" +
                "        <button class=\"btn btn-outline-success navi-search-btn\" type=\"submit\">Search</button>\n" +
                "      </form>\n" ;
        if(isGuest()) {
            Navi +=
                    "        <div class=\"nav-item dropdown\">\n" +
                            customerPageService.getNaviLogin()+
                            customerPageService.getNaviRegister();
            Navi +="      </div>\n";
        }
        else{
            Customer customer = customerRepository.findCustomerByEmail2(getUsername());
            Long id = customer.getId();
            Navi += "<a style='min-width:50px' href='/user/profile' class='nav-link'><img style='margin-top:-5px' class='img-fluid' src='"+customer.getIcon()+"'/></a>"+
                    "<div class=\"nav-item dropdown \">\n" +
                    "          <a href='#' data-toggle=\"dropdown\" class=\"dropdown-menu-text nav-link dropdown-toggle\"" +
                    " \"id=\"navbarLoggedDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">"+customer.getName()+"</a>\n" +
                    "          <div class=\"dropdown-menu dropdown-menu-block nav-dropdown-menu\"  aria-labelledby=\"navbarLoginDropdown\">" +
                    "           <div uid='"+id+"' class=''>\n" +
                    "            <a class=\"nav-link dropdown-item disabled money\" money=\'"+getMoney()+"\' >Your money: $"+getMoney()+"</a>\n" +
                    "          </div>" +
                    "           <div class=''>\n" +
                    "            <a class=\"nav-link dropdown-item\" href=\"#\" onclick='setting("+id+")'>Setting</a>\n" +
                    "          </div>" +
                    "           <div class=''>\n" +
                    "            <a class=\"nav-link\" href=\"/user/profile\" tabindex=\"-1\" aria-disabled=\"true\">Profile</a>\n" +
                    "          </div>\n" ;
            if(customer.getUserRole() == UserRole.ADMIN){
                Navi +="          <div class=\"\">\n" +
                        "            <a class=\"nav-link\" href=\"#\" onclick='adminSetting()' tabindex=\"-1\" aria-disabled=\"true\">admin management</a>\n" +
                        "          </div>\n" ;
            }
//            if(shopRepository.findShopById(id).isPresent()){
                Navi +="          <div class=\"\">\n" +
                        "            <a class=\"nav-link\" href=\"/shop/myShop\" tabindex=\"-1\" aria-disabled=\"true\">My Shop</a>\n" +
                        "          </div>\n" ;
//            }
//                Navi +="          <div class=\" \">\n" +
//                        "            <a class=\"nav-link\" href=\"/shop/shop_register\" tabindex=\"-1\" aria-disabled=\"true\">Register Shop</a>\n" +
//                        "          </div>\n" ;


            Navi +=        "          <div class=\"\">\n" +
                    "            <a class=\"nav-link\" style='cursor:pointer' onclick='logout()' tabindex=\"-1\" aria-disabled=\"true\">Logout</a>\n" +
                    "           </div>\n" +
                    "          </div>" +
            "        </div>\n"+
                    "<div class='nav-item user-menu-icon'>" +
                    " <div class='nav-link mr4' onclick='location.href=\"/favourites\"'>" +
                    "  <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"navi-favourite-icon bi bi-heart\" viewBox=\"0 0 16 16\">\n" +
                    "   <path d=\"m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z\"/>\n" +
                    "  </svg>" +
                    "  <a class=\"navi-icon-text\" style='cursor:pointer' onclick='location.href=\"/favourites\"' tabindex=\"-1\" aria-disabled=\"true\">Favourites</a>\n" +
                    " </div>" +
                    "</div>"+
                    "<div class='nav-item user-menu-icon'>" +
                    " <div class='nav-link mr4' onclick='location.href=\"/chat\"'>" +
                    "  <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-chat\" viewBox=\"0 0 16 16\">\n" +
                    "   <path d=\"M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z\"/>\n" +
                    "  </svg>"+
                    "  <a class=\"navi-icon-text\" style='cursor:pointer' onclick='location.href=\"/chat\"' tabindex=\"-1\" aria-disabled=\"true\">Chat</a>\n" +
                    " </div>" +
                    "</div>" +
                    "<div class='nav-item user-menu-icon'>" +
                    " <div class='nav-link mr4' onclick='location.href=\"/user/payCart\"'>" +
                    "  <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-chat\" viewBox=\"0 0 16 16\">\n" +
                    "   <path d=\"M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z\"/>" +
                    "  </svg>"+
                    "  <a class=\"navi-icon-text\" style='cursor:pointer' onclick='location.href=\"/chat\"' tabindex=\"-1\" aria-disabled=\"true\">Chat</a>\n" +
                    " </div>" +
                    "</div>" ;
        }
        Navi +=

                "    </div>\n" +
                "  </div>\n" +
                "</nav>" ;

        Navi += "";

        return Navi;
    }

    /** Final packing with implement
     *
     * @param content
     * @param title
     * @param implement
     * @return
     */
    public String getHTML(String content, String title,String implement){
        return getHeader(title,implement)+getNavi()+content+getFooter();
    }

    /** Final packing
     *
     * @param content
     * @param title
     * @return
     */
    public String getHTML(String content, String title) {
        return getHeader(title,"")+getNavi()+content+getFooter();
    }

    /** css implement
     *
     * @param cssName
     * @return
     */
    public String getCssImpl(String cssName){
        return "<link rel=\"stylesheet\" href=\"/css/"+cssName+".css\">\n";
    }
    /** js implement
     *
     * @param JsName
     * @return
     */
    public String getJsImpl(String JsName){
        return "<script type=\"text/javascript\" src='/js/"+JsName+".js'></script>\n";
    }

    private double getDouble(double d){
        return  Math.round(d * 100) / 100;
    }
}
