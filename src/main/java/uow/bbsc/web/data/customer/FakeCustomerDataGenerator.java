package uow.bbsc.web.data.customer;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@Service
@AllArgsConstructor
public class FakeCustomerDataGenerator {
    private final Faker faker = new Faker(new Locale("en-US"));
    public String getName(){
        return faker.name().username();
    }
    public String getAddress(){
        return faker.address().fullAddress();
    }
    public String getEmail(){
        return faker.internet().safeEmailAddress();
    }
    public LocalDate getDob(){
        return convertToLocalDateViaMilisecond(faker.date().birthday());
    }
    public String getTel(){
        return faker.phoneNumber().phoneNumber();
    }
    public String getIcon(){
        return "/itemNull.jpg";
    }
    public String getPwd(){
        return "0000";
    }

    public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    public Customer getFakeCustomer(){
        return new Customer(getName(),
                getAddress(),
                getEmail(),
                getDob(),
                getTel(),
                getIcon(),
                getPwd(),
                "Hi! I am really handsome!",
                UserRole.USER
                );
    }
}
