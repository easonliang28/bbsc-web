package uow.bbsc.web.data.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class CustomerConfig {
    // TODO: this is for testing database
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository){
        return args -> {
            /*Customer sun = new Customer(
                    "Sun",
                    "address",
                    "email@gmail.com",
                    LocalDate.of(2000, JANUARY, 8),
                    12345678,
                    "/customer/icon",
                    "123456"
            );
            Customer sad = new Customer(
                    "Sad",
                    "address",
                    "Sad@gmail.com",
                    LocalDate.of(2003, MAY, 3),
                    12345678,
                    "/customer/icon",
                    "123456"
            );
            repository.saveAll(
                    List.of(sun,sad)
            );*/
        };
    }
}
