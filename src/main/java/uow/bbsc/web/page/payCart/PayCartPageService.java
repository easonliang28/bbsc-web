package uow.bbsc.web.page.payCart;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.payCart.PayCart;
import uow.bbsc.web.data.payCart.PayCartPage;
import uow.bbsc.web.data.payCart.PayCartRepository;
import uow.bbsc.web.page.HTMLComponents;
import uow.bbsc.web.page.PageNaviButton;
import uow.bbsc.web.page.item.ItemPageService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PayCartPageService extends HTMLComponents {
    private final ItemPageService itemPageService;
    private final CustomerRepository customerRepository;
    private final PayCartRepository payCartRepository;

    /** Get shopping cart
     *
     * @param id
     * @return the page
     */
    public String getMyPayCart(Long id){
        String content = "";
        // the main block on pay cart page
        content+= HTMLCard(HTMLHeading(1,getCartHeading(id),""),
                HTMLRow("mainRow", HTMLRowCol(getMainRowCol(id))),//content
                "mb-3","");
        content = HTMLDiv("container",content);
        return content;
    }

    /** Get heading for the cart
     *
     * @param id
     * @return heading of the card
     */
    private String getCartHeading(Long id) {
        Customer customer = customerRepository.findCustomerById(id);
        String content = "YOUR CART ";

        content += HTMLElement("span","total","badge bg-secondary",
                "TOTAL <span style=\"color: #20c997;\">"+ payCartRepository.findNotPayPayCartsById(id).size() +"</span> ITEM");
        content +=HTMLElement("btn","","btn btn-secondary",getRedirectJs("/user/itemReview"),
                "style='float: right;'","SHOPPING RECORD =>");
        return content;
    }

    /** Get the main row column
     *
     * @param id
     * @return
     */
    public List<String> getMainRowCol(Long id){
        List<String> rowCol = new ArrayList<String>();
        rowCol.add(payCartLeftCol(id));
        rowCol.add(payCartRightCol(id));
        return rowCol;
    }

    /** Get the left column
     *
     * @param id
     * @return
     */
    public String payCartLeftCol(Long id){
        String content="";
        content += getCartBlocks(id);
        return HTMLDiv("col-lg-8 col-md-12",content);
    }

    /** Cart right side item col
     *              left      right
     *         |***|***|***|  |###|
     *         |***|***|***|  |###|
     *         |***|***|***|  |###|
     *
     * @param id
     * @return
     */
    public String payCartRightCol(Long id){
        String content="";
        content+= HTMLElement("button","","btn btn-primary right-col-btn col-12 mb-3",
                "checkOut()","","CHECKOUT");
        content+=HTMLDiv("col-12 right-item-col right-item-col-price mh-80","");
        content+=HTMLDiv("col-12 right-item-col right-item-col-result mh-15","<h1 style='float: left;margin-top: -2.1rem;'><b>+</b></h1><hr/>"+
                HTMLHeading(2,"Price: "+HTMLElement("h1","price-result","","$0"),""));
        return HTMLDiv("col-lg-4 col-md-12",HTMLRow("right-row mh-100",content));
    }

    /** Cart left side item cols
     *              left      right
     *         |###|###|###|  |***|
     *         |###|###|###|  |***|
     *         |###|###|###|  |***|
     * @param id user id
     * @return a card of the block(No title)
     */
    public String getCartBlocks(Long id){
        List<PayCart> itemList = payCartRepository.findNotPayPayCartsById(id);
        String content="";
        if(itemList.size()==0)
            return HTMLCard(HTMLHeading(1,"You do not any item in cart.",""),
                    HTMLElement("a","","btn btn-primary","","href='/'","Back to shopping!"),"col-12","");
        String headSelectBox =HTMLDiv("select-all-block",HTMLElement("input","select-all","","","type='checkbox' ","" +
                HTMLElement("label","","select-all-text ",""," for='select-all'","Select-all")));
        content += HTMLHeading(3,headSelectBox,"col-12");
        for (int i = 0;i < itemList.size(); i++){
            content += getCartBlock(itemList.get(i),id);
        }
        return content;
    }

    /** left col side information
     *              left      right
     *         |###|###|***|  |***|
     *         |###|###|***|  |***|
     *         |###|###|***|  |***|
     * @param payCart
     * @return two row
     */
    private String getCartBlock(PayCart payCart,Long id) {
        Item item = payCart.getItem();
        String itemImg = HTMLDiv("","col-lg-3 pay-cart-item-img col-md-12","",HTMLHeading(6,item.getName(),"")+
                HTMLImgIcon(item.getIcon(),"",
                        "",getRedirectJs("/item/"+item.getIid()),HTMLElement(
                                "button","deleteCartItem-"+payCart.getPid().toString(),"btn btn-warning deleteCartItem"
                                ,"","pid='"+payCart.getPid()+"'","Delete")));
        String itemContent = HTMLDiv("","col-lg-6 col-md-12","",getCartItemINF(item,payCart.getPid()));

        String itemControl = HTMLDiv("","col-lg-3 col-md-12 cart-item-control","",getCartItemControl(payCart));
        String headSelectBox=HTMLDiv("select-item-block",HTMLElement("input","select-item"+payCart.getPid(),"select-item","","type='checkbox'  pid=\'"+payCart.getPid()+"\'","" +
                HTMLElement("label","","select-all-text ",""," for='select-item"+payCart.getPid()+"'","Select-item")));
        return HTMLCard(HTMLHeading(5,item.getShop().getName()+headSelectBox,"") ,
                HTMLRow("",itemImg + itemContent + itemControl) ,
                "payCartItem payCartItem-"+payCart.getPid(),"");
    }

    /** left col center side inputs
     *              left      right
     *         |***|###|***|  |***|
     *         |***|###|***|  |***|
     *         |***|###|***|  |***|
     * @param item
     * @param pid
     * @return a row
     */
    private String getCartItemINF(Item item,Long pid){
        String tab= "";
        List<String> tabList = item.getTab();
        for (int i =0; i < tabList.size(); i++){
            tab += getTabBlock(tabList.get(i),pid,"cartBlockTab col mb-2",false);
        }
        String content = HTMLElement("p","","",item.getCategory().getName());
        content += HTMLRow("",tab);
        return content;
    }

    /** left col right side inputs
     *              left      right
     *         |***|***|###|  |***|
     *         |***|***|###|  |***|
     *         |***|***|###|  |***|
     *
     * @param payCart
     * @return a row
     */
    private String getCartItemControl(PayCart payCart) {
        String content = "";
        content += "qty:";
        content += HTMLElement("input","cart-qty-"+payCart.getPid(),"control-form cart-qty col-lg-12 col-md-7 col-ms-6 col-xs-6","",
                " type=\'number\' max='1000' min='0' value='"+payCart.getQty()+"' pid='"+payCart.getPid()+"' price='"+payCart.getItem().getPrice()+"' placeholder=\"qty\"","");
        List<String> payTypeList = new ArrayList<>();
        payTypeList.add("cash");
        payTypeList.add("money");
        content+= HTMLSelect("payType-"+payCart.getPid(),"payType","pid='"+payCart.getPid()+"'",payCart.getPay_type(),payTypeList);
        content +=HTMLHeading(5,"$"+(payCart.getQty()*(payCart.getItem().getPrice()))," cart-price align-text-bottom");
        return content;
    }

    /**
     *
     * @param userId
     * @return
     */
    public String getItemReview(PayCartPage payCartPage,Long userId) {
        String content = "";
        // the main block on pay cart page
        content+= HTMLCard(HTMLHeading(1,getReviewHeading(userId),""),
                HTMLRow("mainRow", HTMLRowCol(getItemReviewRowCol(payCartPage,userId))),//content
                "mb-3","");
        content = HTMLDiv("container",content);
        return content;
    }

    private String getReviewHeading(Long userId) {
        return "Payment Record";
    }

    private List<String> getItemReviewRowCol(PayCartPage payCartPage,Long userId) {
        List<String> rowCol = new ArrayList<String>();
        rowCol.add(getReviewItemPage(payCartRepository.findPayCartById(getPageRequest(payCartPage),userId),userId,payCartPage));
        return rowCol;
    }

    public String getReviewItemPage(Page<PayCart> page, Long id,PayCartPage payCartPage){
        List<PayCart> list = page.getContent();
        if(list.size()==0)return "You buy any item.";
        String content="<div class='col-12'><div class='row search-result'>";
        for(int i = 0;i < list.size();i++) {
            content+=getReviewItemBlock(list.get(i));
        }
        content+="</div></div>";
        String url = "/user/itemReview?sortBy="+payCartPage.getSortBy()+"&sortDirection=";
        if(payCartPage.getSortDirection() == Sort.Direction.ASC)url+="ASC";
        else url+="DESC";
        content+= "<div class='pageINF'>"+ PageNaviButton.getPageINF(page,url)+"</div>";
        return  content;
    }

    private PageRequest getPageRequest(PayCartPage payCartPage) {
        return PageRequest.of(
                payCartPage.getPageNumber(),
                payCartPage.getPageSize(),
                Sort.by(payCartPage.getSortDirection(), payCartPage.getSortBy()));
    }

    // Return an payCart block
    public String getReviewItemBlock(PayCart payCart){
        String block = "<div class='col-xxl-2 col-xl-3 col-lg-3 col-md-4 col-ms-6 col-xs-12 img-thumbnail payCart'>" +

                "<div class='payCart-user-title'>" ;
        boolean isShopNull = payCart.getShop()==null;
        boolean isItemNull = payCart.getItem()==null;
        if(isShopNull) {
            block +="  <div class='payCart-user-title-name'> Shop is deleted </div>" ;
        }else {
            block += "  <div class='payCart-user-icon'>" +
                    "  <img src=" + payCart.getShop().getIcon() + " class='img-icon' onclick='window.location.href=\"/shop/" + payCart.getShop().getSid() + "\";'>" +
                    "   </div>" +
                    "  <div class='payCart-user-title-name'>" +
                    "   <div class='payCart-user-name' onclick='window.location.href=\"/shop/" + payCart.getShop().getSid() + "\";'>" +
                    payCart.getShop().getName() +
                    "   </div>" +
                    "  <div >";
            if(!isItemNull)
                block+=timeBeforeText(payCart.getItem().getUpdate_time())+"" ;
            block+="  </div>" +
                    "</div>";
        }
                //
        block+= " </div>"+
                 " <div class='payCart-block'>"+
                "  <div class='payCart-block-img'>" +
                "<img src='";
        if(isItemNull){
            block+="../shopDeleted.jpg";
        }else if(!(payCart.getItem().getIcon()==null)){
            block+=payCart.getItem().getIcon();
        }else {
            block+="../itemNull.jpg";
        }
        String itemHref="";
        if(!isItemNull)
            itemHref="onclick='window.location.href=\"/payCart/"+payCart.getItem().getIid()+"\";'";
        block+="'  class=\"img-responsive img-fluid img-icon mb-3\" "+itemHref+">" ;

        block+="<button class=\"payCart-block-img-btn align-bottom btn btn-warning disabled\" >Qty: "+(payCart.getQty())+"</button>" ;
        if(!isItemNull && !isShopNull) {
            String rate = "Rate it!";
            if(payCart.getRate()!=0) rate ="Your rating:"+payCart.getRate();
            block += "<button class=\"img-btn2-"+ payCart.getPid() +" payCart-block-img-btn2 align-bottom btn btn-secondary\" onclick='rate(" + payCart.getPid() + ")' >"+rate+"</button>";
        }

        block+= "</img>  </div>" ;
        if(!isItemNull){
        block+=        "  <div onclick='window.location.href=\"/item/"+payCart.getItem().getIid()+"\";'>" ;
        block+=        "  <p style='float:right'>$"+(Math.round(payCart.getQty()*payCart.getItem().getPrice()*100)/100)+"</p>";
            block += "  <p class='payCart-title payCart-name'>" + payCart.getItem().getName() + "</p>";
            if(payCart.getItem().getCategory()!=null)
            block += "  <p class='payCart-name'>" + payCart.getItem().getCategory().getName();
        }
        if(!isItemNull && !payCart.getItem().getTab().isEmpty() )
            block+=" - "+payCart.getItem().getTab().get(0);
        block+="</p>" ;
        if(!isItemNull){
            block+=        "  </div>" ;
        }
        block+=        " </div>";
        block+="</div>" ;
        return block;
    }

}
