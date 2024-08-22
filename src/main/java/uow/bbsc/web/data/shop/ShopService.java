package uow.bbsc.web.data.shop;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.customer.UserRole;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.item.ItemRepository;
import uow.bbsc.web.page.shop.ShopUpdateRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ShopService {
    private final static String SHOP_NOT_FOUND_MSG = "Shop with sid %s not found.";
    private final ShopRepository shopRepository;
    private final ItemRepository itemRepository;

    public Page<Shop> getShops(ShopPage shopPage){
        Pageable pageable = PageRequest.of(
                shopPage.getPageNumber(),
                shopPage.getPageSize(),
                Sort.by(shopPage.getSortDirection(),shopPage.getSortBy()));
        return shopRepository.findAll(pageable);
    }
    public Page<Shop> getUserShops(Long id,ShopPage shopPage){
        Pageable pageable = PageRequest.of(
                shopPage.getPageNumber(),
                shopPage.getPageSize(),
                Sort.by(shopPage.getSortDirection(),shopPage.getSortBy()));
        return shopRepository.findShopById2(id,pageable);
    }

    public Shop getShop(Long sid) {
        return shopRepository.findShopBySid2(sid);
    }
    public String signUp(Shop shop){

        Optional<Shop> shopOptional = shopRepository.findShopByEmail(shop.getEmail());
        if(shopOptional.isPresent()){
            throw new IllegalStateException("email token");
        }
        shopRepository.save(shop);
        return "{\"message\":\"Registered!\"," +
                "\"sid\":\""+shop.getSid()+"\"}";
    }


    public String deleteShop(Long shopId) {
        boolean exists = shopRepository.existsById(shopId);
        if(!exists){
            throw new IllegalStateException("shop id("+shopId+") does not exists");
        }
        Customer customer = shopRepository.findShopBySid2(shopId).getCustomer();
        shopRepository.deleteById(shopId);
        Optional<List<Item>> shopList= itemRepository.findItemBySid(shopId);
        for (int i=0;i <shopList.get().size();i++)
            itemRepository.deleteById(shopList.get().get(i).getIid());
        exists = shopRepository.findShopById(customer.getId()).isPresent();
        if(!exists)
            customer.setRole(UserRole.USER);
        return "{'message':'Shop deleted!'}";
    }


    @Transactional
    public String updateShop(Long shopId,String name,String email) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(()-> new IllegalStateException(
                        "shop id("+shopId+") does not exists"
                ));
        if(name != null && name.length() > 0 && !Objects.equals(shop.getName(),name)){
            shop.setName(name);
        }
        if(email != null && email.length() > 0 && !Objects.equals(shop.getEmail(),email)){

            Optional<Shop> shopOptional = shopRepository.findShopByEmail(shop.getEmail());
            if(shopOptional.isPresent()){
                throw new IllegalStateException("Email token!");
            }
            shop.setEmail(email);
        }
        shopRepository.save(shop);
        return "{'message':'Shop updated!'}";
    }

    public String update(ShopUpdateRequest request) {
        Shop shop = shopRepository.findShopsBySid(request.getSid())
                .orElseThrow(()-> new UsernameNotFoundException(String.format(SHOP_NOT_FOUND_MSG,request.getSid())));
        Optional<Shop> shopOptional = shopRepository.findShopByEmail(request.getEmail());
        if(shopOptional.isPresent() && !shop.getEmail().equals(request.getEmail()))
            throw new IllegalStateException("Email token!");
        shop.setName(request.getName());
        shop.setEmail(request.getEmail());
        shop.setAddress(request.getAddress());
        shop.setTel(request.getTel());
        shop.setIcon(request.getIcon());
        shop.setDescription(request.getDescription());
        shopRepository.save(shop);
        return "{'message':'Shop updated!'}";
    }
}
