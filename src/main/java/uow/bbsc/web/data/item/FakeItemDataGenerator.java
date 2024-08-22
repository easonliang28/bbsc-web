package uow.bbsc.web.data.item;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.category.Category;
import uow.bbsc.web.data.category.CategoryRepository;
import uow.bbsc.web.data.shop.Shop;
import uow.bbsc.web.data.shop.ShopRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
@AllArgsConstructor
public class FakeItemDataGenerator {
    private final Faker faker = new Faker(new Locale("en-US"));
    private final CategoryRepository categoryRepository;
    private final ShopRepository shopRepository;

    public Shop getShop(){
        List<Shop> shopList=shopRepository.findAll();
        Random rand = new Random();
        return shopList.get(rand.nextInt(12312)%shopList.size());
    }
    public String getName(){
        return faker.funnyName().name();
    }
    public LocalDateTime getUpdate_time(){
        return LocalDateTime.now();
    }
    public String getDescription(){
        return faker.howIMetYourMother().quote();
    }
    public double getPrice(){
        Random rand = new Random();
        return Math.ceil(300+(rand.nextDouble()*10000)*100)/100;
    }
    public String getIcon(){
        return "/itemNull.jpg";
    }
    public int getStock(){
        Random rand = new Random();
        return rand.nextInt(3001) %1001;
    }
    public Category getCategory(){
        Random rand = new Random();
        List<Category> categoryList=categoryRepository.findAll();
        return categoryList.get(rand.nextInt(3002)%categoryList.size());
    }
    public List<String> getTab(Category category){
        Random rand = new Random();
        List<String> list = new ArrayList<String>();
        int count = rand.nextInt() %3+1;
        List<String> categoryTab =category.getTab();
        if(categoryTab.size()<count)
            count = categoryTab.size();
        if(categoryTab.size()>0)
            for (int i =0;i<count;i++)
                list.add(categoryTab.get(rand.nextInt(3000)%categoryTab.size()));
        return list;
    }
    public Item getFakerItem(){
        Category category = getCategory();
        return new Item(getShop(),
                getName(),
                getUpdate_time(),
                getDescription(),
                getPrice(),
                getIcon(),
                getStock(),
                category,
                getTab(category),
                null
                );
    }
    public Item getFakerItem(Category category){
        return new Item(getShop(),
                getName(),
                getUpdate_time(),
                getDescription(),
                getPrice(),
                getIcon(),
                getStock(),
                category,
                getTab(category),
                null
                );
    }
}
