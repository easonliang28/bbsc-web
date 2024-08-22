package uow.bbsc.web.page.category;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.category.Category;
import uow.bbsc.web.data.category.CategoryRepository;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.item.ItemPage;
import uow.bbsc.web.data.item.ItemService;
import uow.bbsc.web.page.PageNaviButton;
import uow.bbsc.web.page.item.ItemPageService;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryPageService {
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final ItemService itemService;
    private final ItemPageService itemPageService;

    // For updating the category
    public String updateCategoryPage(){
        String content = "<div class='container'>";
        content +="<nav style='padding: 1.5rem;' aria-label='breadcrumb'>" +
                    " <ol class='breadcrumb'>" +
                    "  <li class='breadcrumb-item active alert-heading ' aria-current='page'>Manage all Category</li>" +
                    " </ol>" +
                    "</nav>" ;
        List<Category> categoryList = categoryRepository.findAll();
        for(int i = 0; i < categoryList.size();i++){
            content +=getCategoryBlock(categoryList.get(i));
        }
        content +="</div>";
        content +=
                "<div class='btn-floating'>" +
                " <a class=\"float-btn\" onclick='addCategory()'>\n" +
                "  <i class=\"category-icon-float\">" +
                "   <svg class='category-icon-add' xmlns=\"http://www.w3.org/2000/svg\" width=\"60%\" height=\"60%\" fill=\"currentColor\" class=\"bi bi-bookmark-plus\" viewBox=\"0 0 16 16\">\n" +
                "    <path d=\"M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z\"/>\n" +
                "    <path d=\"M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z\"/>\n" +
                "   </svg>" +
                "  </i>\n" +
                " </a>" +
                "</div>";
        return content;
    }

    // Return a category detail that admin can modified
    public String getAdminCategoryBlock(Long categoryId){
        return getCategoryBlock(categoryRepository.findCategoryById2(categoryId));
    }
    public String getCategoryBlock(Category category){
        String block = "<div class=\"input-group alert alert-secondary categoryBlock categoryBlock-"+category.getCategory_id()+"\" role=\"alert\">" +
                "<div style='display: flow-root !important;flex: 1 1 auto;width: 1%' class='input-group-prepend '>" +
                "<div class=\"input-group mb-3\">\n" +
                "  <span class=\"input-group-text\" id=\"basic-addon1\">Category name</span>\n" +
                "<input type='text' class = 'form-control form-control-lg' id='category-name-"+category.getCategory_id()+"' value='"+category.getName()+"' placeholder='category name'>" +
                "</div>" +
                "<hr class=\"hr-text\" data-content=\"Tabs\">" +
                "<div class='tab-"+category.getCategory_id()+"'>" ;
        List<String> tabs = category.getTab();
        for (int i = 0;i < tabs.size();i++){
            block +=getTabBlock(tabs.get(i), category.getCategory_id());}
        block +=         "</div></div>" +
                "<div style='margin-left:10px;display: grid;' class='input-group-prepend'>" +
                " <button style='width:100px;height: fit-content;margin-bottom:-1px;' type=\"button\" class=\"btn btn-outline-danger\" onclick='deleteCategory(" + category.getCategory_id() + ")'>\n" +
                "  <span aria-hidden=\"true\">Delete</span>\n" +
                " </button>" +
                " <button  type=\"button\" class=\"btn-category btn btn-outline-primary\" onclick='addTab(" + category.getCategory_id() + ")'>\n" +
                "  <span aria-hidden=\"true\">add tab</span>\n" +
                " </button>" +
                " <button type=\"button\" class=\"btn-category btn btn-outline-info\" onclick='clearTabs(" + category.getCategory_id() + ")'>\n" +
                "  <span aria-hidden=\"true\">clear tabs</span>\n" +
                " </button>" +
                " <button type=\"button\" class=\"btn-category btn btn-outline-info\" onclick='refreshCategory(" + category.getCategory_id() + ")'>\n" +
                "  <span aria-hidden=\"true\">refresh</span>\n" +
                " </button>" +
                " <button type=\"button\" class=\"btn-category btn btn-outline-success\" onclick='updateCategory(" + category.getCategory_id() + ")'>\n" +
                "  <span aria-hidden=\"true\">save</span>\n" +
                " </button>" +
                "</div>" +
                "</div>";
        return block;
    }

    /** Return a block of a main category
     *
     * @param name
     * @param id
     * @return
     */
    public String getTabBlock(String name,Long id){

        String s = "<div class=\" alert alert-secondary alert-dismissible fade show blockTab\" role=\"alert\">" +
                "<div class='btn tabBlockIn tabBlock-"+id+"' edit_type='click'>" +
                name +
                "</div>" ;
            s +="<button type=\"button\" class=\"btn close\" data-bs-dismiss=\"alert\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                "  <span aria-hidden=\"true\">×</span>\n" +
                "</button>" ;
        s +=    "</div>";
        return s;
    }

    /** Get top bar navigation
     *
     * @return Category menu inside drop drown navigation
     */
    public String getNaviCategory(){
        String content = "          <div class=\"dropdown-menu megamenu navbarCategoryDropdownBlock\" role=\"menu\" aria-labelledby=\"navbarCategoryDropdown\">\n" +
                "<div class=\"row g-3\" id='navi-category'>";
        List<Category> categoryList=categoryRepository.findAllOrderByCountTab();
        for (int i = 0; i <categoryList.size(); i++)
            content+=getNaviCategoryBlock(categoryList.get(i));
        content+="</div>" +
                "        </div>\n";
        return content;
    }

    // Get component of top bar navigation
    public String getNaviCategoryBlock(Category category) {
        String content=  "<div class=\"col-lg-3 col-6 navi-category-block\">" ;
        content+=        " <div class=\"col-megamenu\">" ;
        content+=        "  <a href='/item/search?categoryId="+category.getCategory_id()+"'><h6 class=\"title\">"+category.getName()+"</h6></a>" ;
        content+=        "  <ul class=\"list-unstyled\">" ;
        List<String> tabList = category.getTab();
        for (int i = 0; i < tabList.size(); i++){

            content+=        "<li><a href=\"/item/search?categoryId="+category.getCategory_id()+"&tab="+tabList.get(i)+"\">"+tabList.get(i)+"</a></li>" ;
        }
        if(tabList.size() !=0)
            content+=        "<li><a href=\"#\"></a></li>" ;
        content+=        "  </ul>" ;


        content+=        " </div>" +
                         "</div>";
        return content;
    }
    // search page
    public String getNaviLinkSearchPage(Long categoryId, String tab,ItemPage itemPage,String email) {
        Customer customer = customerRepository.findCustomerByEmail2(email);
        Long id = 0L;
        if(customer != null)
            id=customer.getId();
        Page<Item> page;
        if(categoryId==0)
            page = itemService.getItems(itemPage);
        else if(tab.length()==0)
            page = itemService.getItemsByCategoryId(itemPage,categoryId);
        else
            page = itemService.getItemsByCategoryAndTab(itemPage,categoryId,tab);

        if(page.getTotalPages()==0)return "No Result";
        String content="";
        content+="<div class=\"container-lg\">\n" +
                "<input  style='display:none' type='text' class = 'form-control' id='id' value='"+id+"'>" +
                getSearchBar() +
                "  <div class=\"row item-list\">\n" +
                "    <div class=\"col-md-3 col-xs-12\">\n" +
                getOrderByBlock()+
                "        <div class='searchFilter'>";
        if(categoryId!=0){
            content+=getSearchFilter(categoryRepository.findCategoryById2(categoryId),tab);
        }
        else{
            content+=getByCategoryBlock();
        }
        content+="<script type=\"text/javascript\" src='/js/searchFilter.js'></script>";
        content+="        </div>\n" +
                "    </div>\n" +
                "    <div class=\"col-md-9 row col-ms-12 col-xs-12 item-row\">\n" ;
        content+=itemPageService.getItemPage(page,id);


            content+=        "    </div>\n" +
                    "  </div>\n" +
                    "</div>";
        String url = "/item/search?"+"categoryId="+categoryId+"&tab="+tab+"&sortBy="+itemPage.getSortBy()+"&sortDirection=";
        if(itemPage.getSortDirection() == Sort.Direction.ASC)url+="ASC";
        else url+="DESC";
        content+= "<div class='pageINF'>"+PageNaviButton.getPageINF(page,url)+"</div>";
        return content;
    }

    // Return the search filter
    public String getSearchFilter(Category category,String tab){
        String content = "";
        content+="<div class=\"\">" +
                "<h2 class=\"grid-title\">" +
                "<i class=\"fa fa-filter\">" +
                "</i> Filters</h2>" +
                "<hr>" ;
        content+=getByCategoryBlock(category);
        List<String> list = category.getTab();
        if(list.size()>0){
            content+="<h4>By tab:</h4>" ;
            for (int i =0; i< list.size();i++) {
                content += "<div class=\"checkbox\"> " +
                        "<label><input type=\"checkbox\" class=\"search-checkbox\" value=\""+ list.get(i) + "\"";
                if(tab==list.get(i))
                        content+=" checked";
                content +=">"+ list.get(i) + "</label>" +
                        "</div>";
            }
        }

        content += getSlider();
        content +="</div>" ;
        return content;
    }

    // Slider for the search options
    private String getSlider() {
        String content="<h4>By price:</h4>" +
                "<div id='id66' class=\"range\">\n" +
                "  <div id='id66b' class='range__between'></div>\n" +
                "    <button id='id661' class=\"range__button_1\"></button>\n" +
                "    <button id='id662' class=\"range__button_2\"></button>\n" +
                "  <div class='slider-input'>" +
                "    $<input id='id66i1' class='range_inpt1' type='number' min='0' max='30000'>\n" +
                "    - $<input id='id66i2' class='range_inpt2' type='number' min='0' max='30000'>\n" +
                "  </div>" +
                "</div>" +
                "" ;
        return content;
    }

    // Return the search bar
    public String getSearchBar(){
        String content = "" ;
        content +="<div class='search-bar col-12'>";
        content +="      \n" +
                "       <div class=\"autoComplete_wrapper\">" +
                "        <input id='autoComplete' class=\"input-search-bar form-control my-0 py-1\" type=\"text\" placeholder=\"Search Keyword\" aria-label=\"Search\" value=''>\n" +
                "       </div>" +
                "           <script src=\"https://cdn.jsdelivr.net/npm/@tarekraafat/autocomplete.js@9.1.1/dist/js/autoComplete.js\"></script>\n" +
                "      \n";
        content +="</div>";
        return content;
    }

    // Sorting option menu
    public String getOrderByBlock(){
        String content = "";
        content += "";
        content += "<h4>Order by:</h4>" +
                "  <div class=\"btn-group\" role=\"group\">\n" +
                "    <button id=\"btnGroupDropOrderBy\" type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                "      Item Name\n" +
                "    </button>\n" +
                "    <ul class=\"dropdown-menu dropdown-menu-orderBy\" aria-labelledby=\"btnGroupDropOrderBy\">\n" ;
        content +=        "      <li><a class=\"dropdown-item Order-by-a\" href=\"#\">Item Name</a></li>\n" ;
        content +=        "      <li><a class=\"dropdown-item Order-by-a\" href=\"#\">Price</a></li>\n";
        content +=        "      <li><a class=\"dropdown-item Order-by-a\" href=\"#\">Stock</a></li>\n";
        content +=        "      <li><a class=\"dropdown-item Order-by-a\" href=\"#\">Last Update</a></li>\n";
        content +=        "    </ul>\n" +
                "  </div>";
        content += "<div class=\"btn-group order-by-btn-group\" role=\"group\" aria-label=\"Basic radio toggle button group\">\n" +
                "  <input type=\"radio\" class=\"btn-check\" name=\"btn-order-by\" id=\"btn-order-by1\" autocomplete=\"off\" checked>\n" +
                "  <label class=\"btn btn-primary btn-order-by1\" for=\"btn-order-by1\">↑</label>\n" +
                "\n" +
                "  <input type=\"radio\" class=\"btn-check\" name=\"btn-order-by\" id=\"btn-order-by2\" autocomplete=\"off\">\n" +
                "  <label class=\"btn btn-outline-secondary btn-order-by2\" for=\"btn-order-by2\">↓</label>\n" +
                "\n" +
                "</div>";
        content += "";
        return content;
    }

    // Return categories for search filter
    public String getByCategoryBlock(Category category){

        String content="";
        content += "";
        content += "<h4>By Category:</h4>" +
                "  <div class=\"btn-group\" role=\"group\">\n" +
                "    <button id=\"btnGroupDropByCategory\" type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                category.getName() +
                "    </button>\n" +
                "    <ul class=\"dropdown-menu dropdown-menu-orderBy\" aria-labelledby=\"btnGroupDropByCategory\">\n" ;
        List<Category> categoryList = categoryRepository.findAll();
        content += "      <li><a class=\"dropdown-item by-category-a\" href=\"/item/search?categoryId=0\">All</a></li>\n";
        for (int i =0 ;i <categoryList.size();i++) {
            content += "      <li><a class=\"dropdown-item by-category-a\" href=\"/item/search?categoryId="+categoryList.get(i).getCategory_id()+"\">" +
                    categoryList.get(i).getName() + "</a></li>\n";
        }
        content +=        "    </ul>\n" +
                "  </div>";
        content += "";
        return content;
    }

    // Get category block for searching
    public String getByCategoryBlock(){

        String content="";
        content += "";
        content += "<h4>By Category:</h4>" +
                "  <div class=\"btn-group\" role=\"group\">\n" +
                "    <button id=\"btnGroupDropByCategory\" type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                "all" +
                "    </button>\n" +
                "    <ul class=\"dropdown-menu dropdown-menu-orderBy\" aria-labelledby=\"btnGroupDropByCategory\">\n" ;
        List<Category> categoryList = categoryRepository.findAll();
        content += "      <li><a class=\"dropdown-item by-category-a\" href=\"/item/search?categoryId=0\">All</a></li>\n";
        for (int i =0 ;i <categoryList.size();i++) {
            content += "      <li><a class=\"dropdown-item by-category-a\" href=\"/item/search?categoryId="+categoryList.get(i).getCategory_id()+"\">" +
                    categoryList.get(i).getName() + "</a></li>\n";
        }
        content +=        "    </ul>\n" +
                "  </div>";
        content += getSlider();
        return content;
    }
}
