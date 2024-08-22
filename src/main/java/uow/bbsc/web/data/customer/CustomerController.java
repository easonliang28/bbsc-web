package uow.bbsc.web.data.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/database/customer")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @RequestMapping("/get")
    public ResponseEntity<Page<Customer>> getCustomers(CustomerPage customerPage){
        return new ResponseEntity<Page<Customer>>(customerService.getAllCustomer(customerPage), HttpStatus.OK);
    }
    @GetMapping("/get/{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Long customerId){
        return customerService.getCustomer(customerId);
    }

    @DeleteMapping(path = "/delete/{customerId}")
    public void  deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }
    @PutMapping(path = "/update/{customerId}")
    public void  updateCustomer(@PathVariable("customerId") Long customerId,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String pwd){
        customerService.updateCustomer(customerId,name,email,pwd);
    }
    @GetMapping("/generate/{times}")
    public void generateCustomer(@PathVariable("times")int times){
        customerService.generateFakeData(times);
    }
}
