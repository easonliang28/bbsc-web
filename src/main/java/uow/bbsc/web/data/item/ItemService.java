package uow.bbsc.web.data.item;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uow.bbsc.web.data.category.CategoryRepository;
import uow.bbsc.web.data.shop.ShopRepository;
import uow.bbsc.web.page.item.ItemRegistrationRequest;
import uow.bbsc.web.page.item.ItemUpdateRequest;
import uow.bbsc.web.page.search.SearchPageRequest;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;


@AllArgsConstructor
@Service
public class ItemService {

    private final static String ITEM_NOT_FOUND_MSG = "Item with iid %s not found.";
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;

    private PageRequest getPageRequest(ItemPage itemPage) {
        return PageRequest.of(
                itemPage.getPageNumber(),
                itemPage.getPageSize(),
                Sort.by(itemPage.getSortDirection(), itemPage.getSortBy()));
    }
    public Page<Item> getItems(ItemPage itemPage){
        Pageable pageable = getPageRequest(itemPage);
        return itemRepository.findAll(pageable);
    }
    public Page<Item> getUserItems(Long id, ItemPage itemPage){
        Pageable pageable = getPageRequest(itemPage);
        return itemRepository.findItemByIid(id,pageable);
    }
    public Page<Item> getShopItems(Long id, ItemPage itemPage){
        Pageable pageable = getPageRequest(itemPage);
        return itemRepository.findItemById(id,pageable);
    }
    public Page<Item> getItemsByCategoryId(ItemPage itemPage, Long categoryId){
        Pageable pageable = getPageRequest(itemPage);
        return itemRepository.findItemPageByCategoryId(categoryId, 0, 30000,  pageable);
    }

    private Page<Item> getItemsByCategoryIdAndPrice(ItemPage itemPage, Long categoryId, int min, int max) {
        Pageable pageable = getPageRequest(itemPage);
        return itemRepository.findItemPageByCategoryId(categoryId, min, max,  pageable);
    }

    public Page<Item> getItemsByCategoryAndTab(ItemPage itemPage, Long categoryId, String tab){
        Pageable pageable = getPageRequest(itemPage);
        return itemRepository.findItemPageByCategoryAndTabAndPrice(categoryId, 0, 30000, tab, pageable);
    }

    private Page<Item> getItemsByCategoryIdAndKeywordAndPrice(ItemPage itemPage, Long categoryId, String keyword, int min, int max) {
        Pageable pageable = getPageRequest(itemPage);
        return itemRepository.getItemPageByCategoryIdAndKeyword(categoryId, min, max,keyword, pageable);
    }

    public Page<Item> getItemsByCategoryAndTabAndPrice(ItemPage itemPage, Long categoryId, String tab, int min, int max){
        Pageable pageable = getPageRequest(itemPage);
        return itemRepository.findItemPageByCategoryAndTabAndPrice(categoryId, min, max, tab, pageable);
    }


    public Page<Item> getItemsByCategoryAndTabs(ItemPage itemPage, Long categoryId, List<String> tab){
        Pageable pageable = getPageable(itemPage);
        String tabs = "";
        for (int i=0;i<tab.size();i++)
            tabs+=tab.get(i)+" ";

        return itemRepository.findItemPageByCategoryAndTabAndPrice(categoryId, 0, 30000, tabs, pageable);
    }

    private PageRequest getPageable(ItemPage itemPage) {
        return getPageRequest(itemPage);
    }

    public Item addItem(Item item){

        itemRepository.save(item);
        return item;
    }


    public String deleteItem(Long payCartId) {
        boolean exists = itemRepository.existsById(payCartId);
        if(!exists){
            throw new IllegalStateException("item("+payCartId+") does not exists");
        }
        itemRepository.deleteById(payCartId);
        return "{'message':'deleted item!'}";
    }


    @Transactional
    public String updateShop(Long payCartId,String name,String email) {

        Item Item = itemRepository.findById(payCartId)
                .orElseThrow(()-> new IllegalStateException(
                        "item("+payCartId+") does not exists"
                ));
        itemRepository.save(Item);
        return "{'message':'updated item!'}";
    }

    public Page<Item> getItemPage(Long categoryId, SearchPageRequest request){
        ItemPage itemPage = new ItemPage(request.getPageNumber()-1,48,request.getSortDirection(),request.getSortBy());
        Pageable pageable = getPageRequest(itemPage);
        if(categoryId == 0){
            if(request.getKeyword()=="")
                return itemRepository.getItemPage(request.getMin(),
                        request.getMax(),
                        pageable);
               else
            return itemRepository.getItemPageByKeyword(request.getMin(),
                    request.getMax(),
                    request.getKeyword(),
                    pageable);
        }
        if(request.getKeyword()=="")
            if(request.getTabs().size()==0)
                return getItemsByCategoryIdAndPrice(itemPage,categoryId,request.getMin(),request.getMax());
            else
                return getItemsByCategoryAndTabAndPrice(itemPage,categoryId,convertTabsToTab(request.getTabs(),categoryId),request.getMin(),request.getMax());

        if(request.getTabs().size()==0)
            return getItemsByCategoryIdAndKeywordAndPrice(itemPage,categoryId,request.getKeyword(),request.getMin(),request.getMax());
        else
        return itemRepository.getItemPageByCategoryIdAndKeywordAndTab(categoryId,
                request.getMin(),
                request.getMax(),
                request.getKeyword(),
                convertTabsToTab(request.getTabs(),categoryId),
                pageable);
    }

    public String getSearchBarSuggestion(Long categoryId, SearchPageRequest request){
        ItemPage itemPage = new ItemPage(request.getPageNumber()-1,48,request.getSortDirection(),request.getSortBy());
        Pageable pageable = getPageRequest(itemPage);
        List<Item> itemList;
        if(categoryId==0){
            itemList = itemRepository.getItemList(request.getMin(),
                    request.getMax(),
                    pageable);
        }
        else
        if(request.getKeyword()==""){

            itemList = itemRepository.findItemListByCategoryAndTabAndPrice(categoryId,
                    request.getMin(),
                    request.getMax(),
                    convertTabsToTab(request.getTabs(),categoryId),
                    pageable);
        }else {
            itemList = itemRepository.getItemListByKeywordAndCategoryIdAndTab(categoryId,
                request.getMin(),
                request.getMax(),
                request.getKeyword(),
                convertTabsToTab(request.getTabs(),categoryId),
                pageable);
        }
        String content = "";
        for (int i = 0; i <itemList.size();i++){
            Item item=itemList.get(i);
            content+=(item.getName()+",");
            content+=(item.getShop().getName()+",");
        }

        return removeDuplicateWords(content);
    }
    public String convertTabsToTab(List<String> tabs,Long categoryId){
        String tab = "";
        if(tabs.size()>0)
            for (int i = 0; i < tabs.size();i++)
                tab +=" " +tabs.get(i);
        else{
            List<String> list = categoryRepository.findCategoryById2(categoryId).getTab();
            for (int i = 0; i < list.size();i++)
                tab +=" " +list.get(i);
        }
        return tab;
    }
    public String removeDuplicateWords(String str){

        String[] strWords = str.split(",");

        //convert String array to LinkedHashSet to remove duplicates
        LinkedHashSet<String> lhSetWords
                = new LinkedHashSet<String>( Arrays.asList(strWords) );

        //join the words again by space
        StringBuilder sbTemp = new StringBuilder();
        int index = 0;

        for(String s : lhSetWords){

            if(index > 0)
                sbTemp.append(",");

            sbTemp.append(s);
            index++;
        }
        return sbTemp.toString();
    }

    public String register(ItemRegistrationRequest request) {
        Item item= new Item(
                shopRepository.findShopBySid2(request.getSid()) ,
                request.getName(),
                request.getUpdate_time(),
                request.getDescription(),
                request.getPrice(),
                request.getIcon(),
                request.getStock(),
                categoryRepository.findCategoryById2(request.getCategory_id()) ,
                request.getTabs(),
                null
        );
        itemRepository.save(item);
        return item.getIid().toString();
    }

    public String update(ItemUpdateRequest request) {
        Item item = itemRepository.findItemByIid(request.getIid())
                .orElseThrow(()-> new UsernameNotFoundException(String.format(ITEM_NOT_FOUND_MSG,request.getIid())));
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setIcon(request.getIcon());
        item.setStock(request.getStock());
        item.setTab(request.getTabs());
        item.setCategory(categoryRepository.findCategoryById2(request.getCategory_id()));
        itemRepository.save(item);
        return item.getIid().toString();
    }
}
