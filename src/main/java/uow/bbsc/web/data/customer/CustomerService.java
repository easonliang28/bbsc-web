package uow.bbsc.web.data.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uow.bbsc.web.data.shop.FakeShopDataGenerator;
import uow.bbsc.web.data.shop.ShopService;
import uow.bbsc.web.page.customer.CustomerAddMoneyRequest;
import uow.bbsc.web.page.customer.CustomerRegistrationRequest;
import uow.bbsc.web.page.customer.CustomerResetPasswordRequest;
import uow.bbsc.web.page.customer.CustomerUpdateRequest;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
@Service
public class CustomerService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final static String USER_NOT_FOUND_MSG2 = "user with id %s not found";
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FakeCustomerDataGenerator fakeCustomerDataGenerator;
    private final FakeShopDataGenerator fakeShopDataGenerator;
    private final ShopService shopService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           FakeShopDataGenerator fakeShopDataGenerator,
                           ShopService shopService,
                           FakeCustomerDataGenerator fakeCustomerDataGenerator) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.fakeCustomerDataGenerator = fakeCustomerDataGenerator;
        this.fakeShopDataGenerator = fakeShopDataGenerator;
        this.shopService = shopService;
    }

    public Page<Customer> getAllCustomer(CustomerPage customerPage){
        Pageable pageable =PageRequest.of(
                customerPage.getPageNumber(),
                customerPage.getPageSize(),
                Sort.by(customerPage.getSortDirection(),customerPage.getSortBy()));
        return customerRepository.findAll(pageable);

    }
    public Customer getCustomer(Long id){
        return customerRepository.findCustomerById(id);

    }

    public String signUp(Customer customer){

        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customer.getEmail());
        if(customerOptional.isPresent()){
            throw new IllegalStateException("email token");
        }
        if(customer.getDob().isAfter(LocalDate.now())&& customer.getDob().isBefore(LocalDate.of(1900,1,1))){
            throw new IllegalStateException("Dob wrong");
        }

        String enCodedPassword = bCryptPasswordEncoder.encode(customer.getPwd());
        customer.setPwd(enCodedPassword);
        customerRepository.save(customer);
        return "{\"message\":\"Registered!\"}";
    }

    public String deleteCustomer(Long customerId) {
        boolean exists = customerRepository.existsById(customerId);
        if(!exists){
            throw new IllegalStateException("customer id("+customerId+") does not exists");
        }
        customerRepository.deleteById(customerId);
        return "{'deleted!'}";
    }
    @Transactional
    public String updateCustomer(Long customerId,String name,String email,String pwd) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new IllegalStateException(
                        "Customer id("+customerId+") does not exists"
                ));
        if(name != null && name.length() > 0 && !Objects.equals(customer.getName(),name)){
            customer.setName(name);
        }
        if(email != null && email.length() > 0 && !Objects.equals(customer.getEmail(),email)){

            Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customer.getEmail());
            if(customerOptional.isPresent()){
                throw new IllegalStateException("email token");
            }
            customer.setEmail(email);
        }
        if(pwd != null && pwd.length() > 0 && Objects.equals(customer.getPwd(),pwd)){
            String enCodedPassword = bCryptPasswordEncoder.encode(customer.getPwd());
            customer.setPwd(enCodedPassword);
        }
        customerRepository.save(customer);
        return "{'updated!'}";
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepository.findCustomerByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public void generateFakeData(int times){
        for(int i = 0;i < times;i++){
            Customer customer = fakeCustomerDataGenerator.getFakeCustomer();
            String enCodedPassword = bCryptPasswordEncoder.encode(customer.getPwd());
            customer.setPwd(enCodedPassword);

            customerRepository.save(customer);
            shopService.signUp(fakeShopDataGenerator.getFakeShop(customer));
        }
    }
    public String register(CustomerRegistrationRequest request) {
        return signUp(
                new Customer(
                        request.getName(),
                        request.getAddress(),
                        request.getEmail(),
                        request.getDob(),
                        request.getTel(),
                        "/itemNull.jpg",
                        request.getPwd(),
                        "Hey! I am handsome.",
                        UserRole.USER

                )
        );
    }

    public String update(CustomerUpdateRequest request) {
        Customer customer = customerRepository.findCustomerById2(request.getId())
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG2,request.getId())));
        Optional<Customer> shopOptional = customerRepository.findCustomerByEmail(request.getEmail());

        if(shopOptional.isPresent() && !customer.getEmail().equals(request.getEmail()))
            throw new IllegalStateException("Email token!");
        customer.setName(request.getName());
        customer.setDescription(request.getDescription());
        customer.setAddress(request.getAddress());
        customer.setEmail(request.getEmail());
        customer.setAge(request.getAge());
        customer.setDob(request.getDob());
        customer.setTel(request.getTel());
        customer.setIcon(request.getIcon());
        customerRepository.save(customer);
        return "User saved!";
    }

    public double addMoney(Long userId, CustomerAddMoneyRequest request) {
        Customer customer = customerRepository.findCustomerById(userId);
        customer.setMoney(customer.getMoney()+ request.getMoney());
        customerRepository.save(customer);
        return customer.getMoney();
    }

    public String resetPassword(CustomerResetPasswordRequest request) {
        Customer customer = customerRepository.findCustomerById(request.getId());
        if(! bCryptPasswordEncoder.matches(request.getOldPassword(),customer.getPassword()))
            return "Wrong password!";
        customer.setPwd(bCryptPasswordEncoder.encode(request.getNewPassword()));
        customerRepository.save(customer);
        return "Password updated!";
    }
}
