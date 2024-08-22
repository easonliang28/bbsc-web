package uow.bbsc.web.data.payCart;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.item.ItemPage;
import uow.bbsc.web.data.item.ItemRepository;
import uow.bbsc.web.data.shop.ShopRepository;
import uow.bbsc.web.page.payCart.PayCartBuyRequest;
import uow.bbsc.web.page.payCart.PayCartRatingRequest;
import uow.bbsc.web.page.payCart.PayCartRegistrationRequest;
import uow.bbsc.web.page.payCart.PayCartUpdateRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class PayCartService {

    private static final String PAYCART_NOT_FOUND_MSG = "pay cart with id %s not found";
    private final PayCartRepository payCartRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
    private final ShopRepository ShopRepository;

    public String register(PayCartRegistrationRequest request) {
        Item item = itemRepository.findItemByIid2(request.getIid());
        PayCart payCart= new PayCart(
                item.getShop() ,
                customerRepository.findCustomerById(request.getId()),
                itemRepository.findItemByIid2(request.getIid()),
                request.getQty(),
                null,
                "cash",
                0 ,
                "inCart"
        );
        payCartRepository.save(payCart);
        return payCart.getPid().toString();
    }

    public Page<PayCart> getAllItem(ItemPage itemPage, Long id) {
        Pageable pageable = PageRequest.of(
                itemPage.getPageNumber(),
                itemPage.getPageSize(),
                Sort.by(itemPage.getSortDirection(),itemPage.getSortBy()));
        return payCartRepository.findAllItem(pageable, id);
    }

    public String addItem(PayCart payCart){

        payCartRepository.save(payCart);
        return "added item!";
    }


    public String deleteCartItem(Long payCartId) {
        boolean exists = payCartRepository.existsById(payCartId);
        if(!exists){
            throw new IllegalStateException("Cart item("+payCartId+") does not exists");
        }
        payCartRepository.deleteById(payCartId);
        return "item deleted!";
    }


    @Transactional
    public String updateShop(Long payCartId,String name,String email) {

        PayCart payCart = payCartRepository.findById(payCartId)
                .orElseThrow(()-> new IllegalStateException(
                        "Cart item("+payCartId+") does not exists"
                ));
        payCartRepository.save(payCart);
        return "updated!";
    }

    public String update(PayCartUpdateRequest request) {
        Optional<PayCart> optionalPayCart = payCartRepository.findPayCartById(request.getPid());
        if(optionalPayCart.isPresent()){
        PayCart payCart = optionalPayCart.get();
        payCart.setPay_type(request.getPayType());
        payCart.setRate(request.getRate());
        payCart.setQty(request.getQty());
        if(request.isPay())
            payCart.setPay_time(LocalDateTime.now());
        payCartRepository.save(payCart);
        return "Pay cart saved!";
        }
        return "Pay cart not saved!";
    }

    public String deleteItem(Long pid) {
        boolean exists = payCartRepository.existsById(pid);
        if(!exists){
            throw new IllegalStateException("pay cart("+pid+") does not exists");
        }
        payCartRepository.deleteById(pid);
        return "Item deleted!";
    }

    public String buy(PayCartBuyRequest request) {
        List<Long> pidList = request.getPidList();
        Long pid =0L;
        for (int i=0;i<pidList.size();i++){
            pid = pidList.get(i);
            Long finalPid = pid;
            PayCart payCart = payCartRepository.findPayCartById(pid)
                    .orElseThrow(()-> new UsernameNotFoundException(String.format(PAYCART_NOT_FOUND_MSG,finalPid)));
            Customer customer=payCart.getCustomer();
            Item item = payCart.getItem();
            if (payCart.getItem().getStock()<payCart.getQty()) return "No stock!";
            if(payCart.getPay_type().equals("money")) {
                double money = customer.getMoney() - (payCart.getQty() * payCart.getItem().getPrice());
                if (money < 0) {
                    return "Please add more money!!";
                }
                customer.setMoney(getDouble(money));
                Customer seller = payCart.getShop().getCustomer();
                seller.setMoney(getDouble(seller.getMoney()+payCart.getQty() * payCart.getItem().getPrice()));
                customerRepository.save(seller);
            }
            item.setStock(item.getStock()-payCart.getQty() );
            itemRepository.save(item);
            payCart.setPay_time(LocalDateTime.now());
            customerRepository.save(customer);
            payCartRepository.save(payCart);

        }
        return "Bought!";
    }

    private double getDouble(double d){
        return  Math.round(d * 100) / 100;
    }

    public double rate(PayCartRatingRequest request) {
        PayCart payCart = payCartRepository.findPayCartById(request.getPid())
                .orElseThrow(()-> new UsernameNotFoundException(String.format(PAYCART_NOT_FOUND_MSG,request.getPid())));
        payCart.setRate(request.getRate());

        payCartRepository.save(payCart);
        return request.getRate();
    }
}
