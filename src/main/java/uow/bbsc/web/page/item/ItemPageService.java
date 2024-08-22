package uow.bbsc.web.page.item;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.category.Category;
import uow.bbsc.web.data.category.CategoryRepository;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.data.favourites.Favourites;
import uow.bbsc.web.data.favourites.FavouritesRepository;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.item.ItemPage;
import uow.bbsc.web.data.item.ItemRepository;
import uow.bbsc.web.data.item.ItemService;
import uow.bbsc.web.data.shop.Shop;
import uow.bbsc.web.data.shop.ShopRepository;
import uow.bbsc.web.page.HTMLComponents;
import uow.bbsc.web.page.PageNaviButton;
import uow.bbsc.web.page.comment.CommentPageService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemPageService extends HTMLComponents {
    private final ItemService itemService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;
    private final FavouritesRepository favouritesRepository;
    private final CommentPageService commentPageService;

    // Get item page (using email)
    public String getItemPage(ItemPage itemPage,String email){
        Customer customer = customerRepository.findCustomerByEmail2(email);
        Long id = 0L;
        if(customer != null)
            id=customer.getId();
        Page<Item> page = itemService.getItems(itemPage);
        List<Item> list = page.getContent();
        String content="<div class='container '><div class='row item-list'>";
        for(int i = 0;i < list.size();i++) {
            content+=getItemBlock(list.get(i),id);
        }
        content+="</div></div>";
        String url = "/item/all_items?sortBy="+itemPage.getSortBy()+"&sortDirection=";
        if(itemPage.getSortDirection() == Sort.Direction.ASC)url+="ASC";
        else url+="DESC";
        content+= "<div class='pageINF'>"+PageNaviButton.getPageINF(page,url)+"</div>";
        return  content;
    }

    // Get item page (using id)
    public String getItemPage(Page<Item> page,Long id){
        List<Item> list = page.getContent();
        if(list.size()==0)return "Any Result";
        String content="<br/><div class='container'><div class='row search-result'>";
        for(int i = 0;i < list.size();i++) {
            content+=getItemBlock(list.get(i),id);
        }
        content+="</div></div>";
        return  content;
    }

    // Get item page (not logged in)
    public String getItemPage(Page<Item> page){
        List<Item> list = page.getContent();
        if(list.size()==0)return "Any Result";
        String content="<br/><div class='container'><div class='row search-result'>";
        for(int i = 0;i < list.size();i++) {
            content+=getItemBlock(list.get(i),0L);
        }
        content+="</div></div>";
        return  content;
    }

    // Get user's brought items
    public String getUserItemPage(ItemPage itemPage, Long id) {
        Page<Item> page = itemService.getUserItems(id,itemPage);
        return getSmallItemPage(page,"<h2>This user do not buy anything.</h2>",id);
    }

    // Get shop's products
    public String getShopItemPage(ItemPage itemPage, Long id,Long sid) {
        Page<Item> page = itemService.getShopItems(sid,itemPage);
        return getSmallItemPage(page,"<h2>This shop do not sell anything.</h2>",id);
    }

    // Return a small item page
    private String getSmallItemPage(Page<Item> page,String zeroMessage, Long id) {
        List<Item> list = page.getContent();
        if(list.size()==0)return zeroMessage;
        String content="<div class='container '><div class='row item-list'>";

        for(int i = 0;i < list.size();i++) {
            content+=getItemBlock(list.get(i),id);
        }
        content+="</div></div>";
        return  content;
    }

    // Return an item block
    public String getItemBlock(Item item,Long id){
        String block = "<div class='col-xxl-2 col-xl-3 col-lg-3 col-md-4 col-ms-6 col-xs-12 img-thumbnail item'>" +

                "<div class='item-user-title'>" ;
        boolean isShopNull = item.getShop()==null;
        if(isShopNull) {
            block +="  <div class='item-user-title-name'> Shop is deleted </div>" ;
        }else {
            block += "  <div class='item-user-icon'>" +
                    "  <img src=" + item.getShop().getIcon() + " class='img-icon' onclick='window.location.href=\"/shop/" + item.getShop().getSid() + "\";'>" +
                    "   </div>" +
                    "  <div class='item-user-title-name'>" +
                    "   <div class='item-user-name' onclick='window.location.href=\"/shop/" + item.getShop().getSid() + "\";'>" +
                    item.getShop().getName() +
                    "   </div>" +
                    "  <div >";
            block+=timeBeforeText(item.getUpdate_time())+"" ;
            block+="  </div>" +
                    "</div>";
        }
        //
        block+= " </div>"+
                " <div class='item-block'>"+
                "  <div class='item-block-img'>" +
                "<img src='";
        if(!(item.getIcon()==null)){
            block+=item.getIcon();
        }else {
            block+="../itemNull.jpg";
        }
        String itemHref="";
        itemHref="onclick='window.location.href=\"/item/"+item.getIid()+"\";'";
        block+="'  class=\"img-responsive img-fluid img-icon mb-3\" "+itemHref+">" ;
        if(id!=0L)
            block+="<button class=\"item-block-img-btn align-bottom btn btn-warning\" onclick='addToCart("+item.getIid()+","+id+")'>Add to Cart</button>" ;

        block+= "</img>  </div>" +
                "  <div onclick='window.location.href=\"/item/"+item.getIid()+"\";'>" +
                "  <p style='float:right'>$"+item.getPrice()+"</p>"+
                "  <p class='item-title item-name'>"+item.getName()+"</p>" +
                "  <p class='item-name'>"+item.getCategory().getName();
        if(!item.getTab().isEmpty())
            block+=" - "+item.getTab().get(0);
        block+="</p>" ;



        block+= "  </div>" +
                " </div>";
        if(id>0) {
            boolean isFavourites = false;
            String fid="";
            List<Favourites> favouritesList=favouritesRepository.findFavouritesItem(id,item.getIid());
            if(favouritesList.size()>0) {
                isFavourites = true;
                fid = favouritesList.get(0).getFid()+"";
            }
            block += "<div iid='" + item.getIid() + "' fid='"+fid+"' id='favourites-" + item.getIid() + "' class='favourite-icon' value='"+isFavourites+"'>" +
                    "<svg onclick='favourite(" + item.getIid() + ")' xmlns=\"http://www.w3.org/2000/svg\" width=\"28\" height=\"28\" fill=\"currentColor\" class=\"bi bi-heart\" viewBox=\"0 0 16 16\">\n" +
                    "  <path  d=\"m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z\"/>\n" +
                    "\n" +
//                "    <rect class=\"btn\" x=\"0\" y=\"0\" width=\"10\" height=\"10\" onclick=\"alert('click!')\" />" +
                    "</svg>" +
                    "</div>";
        }
        block+="</div>" ;
        return block;
    }


    // Get the user's shop item
    public String getMyShopItem(Long sid,boolean isAdmin) {
        Shop shop = shopRepository.findShopBySid(sid);
        String content = "<div class='container'>";
        if(isAdmin){
            content += "<nav style='padding: 1.5rem;' aria-label='breadcrumb'>" +
                    " <ol class='breadcrumb'>" +
                    "  <li class=\"breadcrumb-item active\"><a href='/admin/update/shop'>Manage all Shop</a></li>" +
                    "  <li class='breadcrumb-item active alert-heading ' aria-current='page'>Manage shop item</li>" +
                    " </ol>" +
                    "</nav>" +
                    "<div style='display:none;' id='sid'>" + sid + "</div>";
        }else {
            content += "<nav style='padding: 1.5rem;' aria-label='breadcrumb'>" +
                    " <ol class='breadcrumb'>" +
                    "  <li class=\"breadcrumb-item active\"><a href='/shop/all_shop'>All Shop</a></li>" +
                    "  <li class=\"breadcrumb-item active\"><a href='/shop/myShop'>My shop - " + shop.getName() + "</a></li>" +
                    "  <li class='breadcrumb-item active alert-heading ' aria-current='page'>Manage item</li>" +
                    " </ol>" +
                    "</nav>" +
                    "<div style='display:none;' id='sid'>" + sid + "</div>";
        }
        List<Item> itemList = itemRepository.findItemsBySid(sid);
        if(itemList.size()==0)
            content+="<h2>Your shop do not have any item.</h2>";
        else
        for(int i = 0; i < itemList.size();i++){
            content +=getEditItemBlock(itemList.get(i));
        }
        content +="</div>";
        content +=
                "<div class='btn-floating'>" +
                        " <a class=\"float-btn\" onclick='addItem()'>\n" +
                        "  <i class=\"category-icon-float\">" +
                        "   <svg class='item-icon-add' xmlns=\"http://www.w3.org/2000/svg\" width=\"60%\" height=\"100%\" fill=\"currentColor\" class=\"bi bi-bookmark-plus\" viewBox=\"0 0 16 16\">\n" +
                        "    <path d=\"M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z\"/>\n" +
                        "    <path d=\"M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z\"/>" +
                        "   </svg>" +
                        "  </i>\n" +
                        " </a>" +
                        "</div>";
        return content;
    }

    // Allow users to edit their item sold on their shops
    public String getEditItemBlock(Item item) {
        Long iid= item.getIid();
        String block = "<div class=\" row input-group alert alert-secondary itemBlock itemBlock-"+iid+"\" role=\"alert\">" +
                "<div style='flex: 1 1 auto;width: 1%' class='col-xxl-10 col-xl-10 col-lg-10 col-md-12 col-ms-12 col-xs-12'>" +
                "<div class=\"row input-group-prepend \">"+
                "<div class=\"col-xxl-9 col-xl-9 col-lg-9 col-md-12 col-ms-12 col-xs-12\">"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Item name</span>\n" +
                "<input type='text' class = 'item-col form-control form-control-lg' id='item-name-"+iid+"' value='"+item.getName()+"' placeholder='item name'>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Description</span>\n" +
                "<input type='text' class = 'item-col form-control form-control-lg' id='item-description-"+iid+"' value='"+item.getDescription()+"' placeholder='description'>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Price</span>\n" +
                "<input type='number' step=\"0.01\" class = 'item-col form-control form-control-lg' id='item-price-"+iid+"' value='"+item.getPrice()+"' placeholder='price'>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Stock</span>\n" +
                "<input type='number' class = 'item-col form-control form-control-lg' id='item-stock-"+iid+"' value='"+item.getStock()+"' placeholder='stock'>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Icon</span>\n" +
                "<input  style='display:none' type='text' class = 'form-control' id='item-icon-"+iid+"' value='"+item.getIcon()+"'>" +
                "<form method=\"POST\" enctype=\"multipart/form-data\" id=\"UploadForm-"+iid+"\" style=\"margin-bottom: 0px;\">\n" +
                "     <div class='sid' style='display:none' >"+iid+"</div>" +
                "     <input class='item-col item-upload-icon item-upload-icon-"+iid+" form-control form-control-lg' id='iconUploadFile"+iid+"' type=\"file\" name=\"uploadFiles\"  multiple=\"multiple\" accept=\".png\"/>\n" +
                "</form>" +
                "</div>" +
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">category</span>\n" +
                "<select class = 'item-col item-category-select' id='item-category-"+iid+"' name='category_id' placeholder='item category' selected='"+item.getCategory().getName()+"'>" +
                getCategoryOption(item.getCategory().getName())+
                "</select>" +
                "</div>"+
                "</div>" +
                "<div class=\"col-xxl-3 col-xl-3 col-lg-3 col-md-12 col-ms-12 col-xs-12\">\n" +
                "  <div class=\"card\" style=\"width: 100%;\">\n" +
                "    <p style=\"text-align: center;padding-top: 0.5rem;\">Icon preview</p>\n" +
//                "<hr/>" +
                "  <img src=\""+item.getIcon()+"\" class=\"img-icon card-img-top img-"+iid+"\" alt=\"...\">\n" +
                "  </div>"+
                "</div>"+
                "<hr class=\"hr-text\" data-content=\"Item Tab\">" +
                "<div class=\"item-tab item-tab-"+iid+" col-12\">\n" +
                getTabBlock(iid)+
                "</div>"+
                "</div>"+
                "";
        block +=         "</div>" +
                "<div style='margin-left:10px;display: grid;' class='col-xxl-2 col-xl-2 col-lg-2 col-md-2 col-ms-12 col-xs-12 input-group-prepend'>" +
                " <button style='width:100%;height: fit-content;margin-bottom:-1px;' type=\"button\" class=\"btn btn-outline-danger\" onclick='deleteItem(" + iid + ")'>\n" +
                "  <span aria-hidden=\"true\">Delete</span>\n" +
                " </button>" +
                " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-item btn btn-outline-primary\" onclick='addItemTab(" + iid + ")'>\n" +
                "  <span aria-hidden=\"true\">Add tab</span>\n" +
                " </button>" +
                " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-item btn btn-outline-primary\" onclick='clearItemTab(" + iid + ")'>\n" +
                "  <span aria-hidden=\"true\">Clear tab</span>\n" +
                " </button>" +
                " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-item btn btn-outline-info\" onclick='refreshItem(" + iid + ")'>\n" +
                "  <span aria-hidden=\"true\">Refresh</span>\n" +
                " </button>" +
                " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-item btn btn-outline-success\" onclick='updateItem(" + iid + ")'>\n" +
                "  <span aria-hidden=\"true\">Save</span>\n" +
                " </button>" +
                "</div>" +
                "</div>" +
                "";
        return block;
    }

    // Return options for categories
    public String getCategoryOption(String selected) {

        String option = "";
        List<Category> categoryList = categoryRepository.findAll();
        for (int i = 0; i < categoryList.size();i++){
            option+="<option value=\""+categoryList.get(i).getCategory_id()+"\" ";
            if(selected.equals(categoryList.get(i).getName()))
                option+="selected=\"selected\"";
            option+=">"+categoryList.get(i).getName()+"</option>";
        }

        return option;
    }

    // Return tab block
    public String getTabBlock(Long iid){
        Item item = itemRepository.findItemByIid2(iid);
        Category category= categoryRepository.findCategoryById2(item.getCategory().getCategory_id());
        List<String> categoryTabList = category.getTab();
        List<String> tabList = item.getTab();
        String block = "";

        for (int i = 0; i < tabList.size();i++){
            block += "<div class=\" alert alert-secondary alert-dismissible fade show blockTab\" role=\"alert\">" +
                    "<div class='btn tabBlockIn tabBlock-"+item.getIid()+"' edit_type='click'>" ;
            if(tabList.size()==0) return "null";
            block+="<select class='item-tab-select-"+item.getIid()+" item-tab-select-"+item.getIid()+"-"+i+"' " +
                    "id='item-tab-select-"+item.getIid()+"-"+i+"' name='item-tab-select-"+i+"' placeholder='item tab' >";
            for (int j = 0; j < categoryTabList.size();j++){
                block+="<option value=\""+categoryTabList.get(j)+"\" ";
                if(tabList.get(i).equals(categoryTabList.get(j)))
                    block+="selected='selected'";
                block+=">"+categoryTabList.get(j)+"</option>";
            }
            block+="</select>";
            block +=         "</div>" ;
            block +="<button type=\"button\" class=\"btn close\" data-bs-dismiss=\"alert\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "  <span aria-hidden=\"true\">×</span>\n" +
                    "</button>" ;
            block +=    "</div>";
        }
        return block;
    }

    /** Get tab options for new item tab with category
     *
     * @param iid
     * @param categoryId
     * @param tab_name selected tab
     * @return
     */
    public String getTabOption(Long iid,Long categoryId,int i,String tab_name) {
        Category category = categoryRepository.findCategoryById2(categoryId);
        Item item = itemRepository.findItemByIid2(iid);

        String option = "";
        List<String> tabList = category.getTab();
        if(tabList.size()==0) return "null";
        option+="<select class='item-tab-select-"+item.getIid()+" item-tab-select-"+item.getIid()+"-"+i+"' " +
                "id='item-tab-select-"+item.getIid()+"-"+i+"' name='item-tab-select-"+i+"' placeholder='item tab' >";
        for (int j = 0; j < tabList.size();j++){
            option+="<option value=\""+tabList.get(j)+"\" ";
            if(tab_name.equals(tabList.get(j)))
                option+="selected='selected'";
            option+=">"+tabList.get(j)+"</option>";
        }
        option+="/<select>";

        return option;
    }

    // Get new item tab select menu
    public String getNewItemTabSelect(Long category_id,int i) {

        Category category = categoryRepository.findCategoryById2(category_id);

        String option = "";
        List<String> tabList = category.getTab();
        if(tabList.size()==0) return "null";
        option+="<select class='item-tab-select-new item-tab-select-new-"+i+"' " +
                "id='item-tab-select-new-"+i+"' name='item-tab-select-"+i+"' placeholder='item tab' >";
        for (int j = 0; j < tabList.size();j++){
            option+="<option value=\""+tabList.get(j)+"\" ";
            option+=">"+tabList.get(j)+"</option>";
        }
        option+="</select>";

        return option;
    }

    // View for new item block
    public String getNewItemBlock() {
        String block = "<div class=\" row input-group alert alert-secondary itemBlock-new\" role=\"alert\">" +
                "<div style='flex: 1 1 auto;width: 1%' class='col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-ms-12 col-xs-12'>" +
                "<div class=\"row input-group-prepend \">"+
                "<div class=\"col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-ms-12 col-xs-12\">"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Item name</span>\n" +
                "<input type='text' class = 'item-col form-control form-control-lg' id='item-name-new' value='' placeholder='item name'>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Description</span>\n" +
                "<input type='text' class = 'item-col form-control form-control-lg' id='item-description-new' value='' placeholder='description'>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Price</span>\n" +
                "<input type='number' step=\"0.01\" class = 'item-col form-control form-control-lg' id='item-price-new' value='' placeholder='price'>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Stock</span>\n" +
                "<input type='number' class = 'item-col form-control form-control-lg' id='item-stock-new' value='' placeholder='stock'>" +
                "</div>"+
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">Icon</span>\n" +
                "<input  style='display:none' type='text' class = 'form-control' id='item-icon-new' value=''>" +
                "<form method=\"POST\" enctype=\"multipart/form-data\" id=\"UploadForm-new\" style=\"margin-bottom: 0px;\">\n" +
                "     <div class='sid' style='display:none' >new</div>" +
                "     <input class='item-col item-upload-icon item-upload-icon-new form-control form-control-lg' id='iconUploadFileNew' type=\"file\" name=\"uploadFiles\"  multiple=\"multiple\" accept=\".png\"/>\n" +
                "</form>" +
                "</div>" +
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\">category</span>\n" +
                "<select class = 'item-col' id='item-category-new' name='category_id' placeholder='item category' selected=''>" +
                getCategoryOption("")+
                "</select>" +
                "</div>"+
                "</div>" +
                "<hr class=\"hr-text\" data-content=\"Item Tab\">" +
                "<div class=\"item-tab item-tab-new col-12\">\n" +
                "</div>"+
                "</div>"+
                "";
        block +=         "</div>" +
                "<div style='margin-left:10px;' class='col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-ms-12 col-xs-12 input-group-prepend'>" +
                " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-item btn btn-outline-primary\" onclick='addNewItemTab()'>\n" +
                "  <span aria-hidden=\"true\">Add tab</span>\n" +
                " </button>" +
                " <button style='width:100%;height: fit-content;' type=\"button\" class=\"btn-item btn btn-outline-primary\" onclick='clearNewItemTab()'>\n" +
                "  <span aria-hidden=\"true\">Clear tab</span>\n" +
                " </button>" +
                "</div>" +
                "</div>" +
                "";
        return block;
    }

    // Show item info
    public String getItemView(Long iid) {
        Optional<Item> itemOptional = itemRepository.findItemByIid(iid);
        if(!itemOptional.isPresent())
            return "item not found";
        Item item = itemOptional.get();
        String itemView = "<div class='container'>";
        String title = "<h1>Item information</h1>";
        String body = "";
        body +=
                "        <div class=\"row\">\n" +
                        HTMLCard("",
                                "<div class=\"align-middle col-lg-8 col-md-12 col-ms-12 mb-4\">\n" +
                                        "    <h1>"+item.getName()+"</h1>\n" +
                                        "    <h3>&nbsp;</h3>\n" +
                                        "    <p>Stock :"+item.getStock()+"</p>\n" +
                                        "    <p>Price :"+item.getPrice()+"</p>\n" +
                                        "  </div>\n" +
                                        "  <div class = \"align-middle col-md-3 col-ms-12 ratio-1-1\">\n" +
                                        "   <img class='img-icon ' src=\""+item.getIcon()+"\" >\n" +
                                        " </div>\n",
                                "mb-4",
                                "col-12 row") +
                        "        <div class='col-lg-7 col-md-12 col-ms-12'>\n" +
                        HTMLCard("<h3>Description:</h3>",
                                "          <p>"+item.getDescription()+"<p>\n",
                                "mb-3","")+
                        "        </div>\n";

        body += "        <div class='col-lg-5 col-md-12 col-ms-12'>\n" +
                getUserCardINF(item.getShop().getCustomer(),"") +
                "        </div>\n"+
                "       </div>\n";
        itemView += HTMLCard(title,body,"mb-3","");
        itemView += commentPageService.getComment(iid);
        itemView += "</div>";
        return itemView;
    }

    // Shows user info
    public String getUserCardINF(Customer customer, String username) {
        Long id =customer.getId();
        String body = "";
        String title ="<h1>Seller</h1>";
        body +="  <a href='/user/"+id+"'><img style='border-radius: 50%;align-self: center;width: 50%' src=\""+ customer.getIcon()+"\" class=\"img-icon card-img-top\" /></a>" ;
        body +="<h2 style='font-size: 30px;line-height: 38px;text-align: left;font-weight: 400;'>"+customer.getName()+"</h2>";
        body +="<p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>Money : "+customer.getMoney()+"</h2>";
        body +="<p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>Rating : "+"(rating)"+"</h2>";
        body +="<p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>Comments : "+"(comments number)"+"</h2>";
        body +="<p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>Favourites : "+"(Favourites number)"+"</h2>";
        body +="";
        body +="";

        body +="<p style='font-size: 16px;line-height: 24px;text-align: left;font-weight: 400;'>"+customer.getDescription()+"</h2>";
        if(username.equals(customer.getUsername()))
            title +="<button style='position:absolute;' type=\"button\" class=\"btn close\" onclick='window.location.href=\"/user/setting\";'>\n" +
                    "   <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"32\" height=\"32\" fill=\"currentColor\" class=\"bi bi-gear\" viewBox=\"0 0 16 16\">\n" +
                    "       <path d=\"M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z\"/>\n" +
                    "       <path d=\"M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z\"/>\n" +
                    "   </svg>" +
                    "</button>";
        return HTMLCard(title,body,"","");
    }
}
