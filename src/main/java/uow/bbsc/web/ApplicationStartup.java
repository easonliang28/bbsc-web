package uow.bbsc.web;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import uow.bbsc.web.data.category.Category;
import uow.bbsc.web.data.category.CategoryRepository;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.data.customer.UserRole;
import uow.bbsc.web.data.item.FakeItemDataGenerator;
import uow.bbsc.web.data.item.ItemRepository;
import uow.bbsc.web.data.shop.Shop;
import uow.bbsc.web.data.shop.ShopRepository;

import java.util.List;

@AllArgsConstructor
@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final FakeItemDataGenerator fakeItemDataGenerator;
    private final CategoryRepository categoryRepository;


    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        //checkUserRole();
        generateItems(100);//for each tab
        return;
    }
    public void generateItems(int number){
        List<Category> categoryList = categoryRepository.findAll();
        for (int i = 0;i<categoryList.size();i++){
            while (itemRepository.findItemByCategoryCategory_id(categoryList.get(i).getCategory_id()).size()<number)
            itemRepository.save(fakeItemDataGenerator.getFakerItem(categoryList.get(i)));
        }
    }
    public void checkUserRole(){
        List<Shop> shopList = shopRepository.findAll();
        for(int i=0;i <shopList.size();i++){
            Customer customer=shopList.get(i).getCustomer();
            if(customer.getUserRole()== UserRole.USER){
                customer.setRole(UserRole.SHOP);
                customerRepository.save(customer);
            }
        }
    }
}
