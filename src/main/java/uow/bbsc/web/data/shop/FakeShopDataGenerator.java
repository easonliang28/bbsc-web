package uow.bbsc.web.data.shop;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.customer.Customer;

import java.time.LocalDate;
import java.util.Locale;

@Service
@AllArgsConstructor
public class FakeShopDataGenerator {
    private final Faker faker = new Faker(new Locale("en-US"));
    public String getName(){
        return faker.pokemon().name();
    }
    public String getAddress(){
        return faker.address().fullAddress();
    }
    public String getEmail(){
        return faker.internet().safeEmailAddress();
    }
    public String getTel(){
        return faker.phoneNumber().phoneNumber();
    }
    public String getIcon(){
        return "/itemNull.jpg";
    }
    public Shop getFakeShop(Customer customer){
        return new Shop(customer,
                getName(),
                getAddress(),
                getEmail(),
                getTel(),
                getIcon(),
                LocalDate.now(),
                "Yeah! I become handsome!"
                );
    }
}
