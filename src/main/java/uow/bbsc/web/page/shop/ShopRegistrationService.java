package uow.bbsc.web.page.shop;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.data.customer.UserRole;
import uow.bbsc.web.data.shop.Shop;
import uow.bbsc.web.data.shop.ShopService;
import uow.bbsc.web.page.HTMLPacker;

@Service
@AllArgsConstructor
public class ShopRegistrationService {
    private final ShopService shopService;
    private final HTMLPacker htmlPacker;
    private final CustomerRepository customerRepository;

    public String register(ShopRegistrationRequest request) {
        Customer customer = customerRepository.findCustomerByEmail2(htmlPacker.getUsername());
        if(customer.getUserRole()!=UserRole.ADMIN)
            customer.setRole(UserRole.SHOP);
        customerRepository.save(customer);
        String icon = "/itemNull.jpg";
        if(request.getIcon()!="")
            icon = request.getIcon();
        return shopService.signUp(
                new Shop(customer,
                        request.getName(),
                        request.getAddress(),
                        request.getEmail(),
                        request.getTel(),
                        icon,
                        request.getRegistered_date(),
                        "YO! I know you are so handsome!")
        );
    }
}
