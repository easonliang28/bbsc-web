package uow.bbsc.web.data.shop;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/database/shop")
public class ShopController {
    private final ShopService shopService;
    private final ShopRepository shopRepository;
    @GetMapping()
    public ResponseEntity<Page<Shop>> showShopsPage(ShopPage shopPage){
        return new ResponseEntity<Page<Shop>>(shopService.getShops(shopPage), HttpStatus.OK);
    }
    @RequestMapping("/{shopId}")
    public Shop showShopPage(@PathVariable("shopId") Long shopId){
        return shopService.getShop(shopId);
    }
    @DeleteMapping(path = "/update/{shopId}")
    public void  deleteShop(@PathVariable("shopId") Long shopId){
        shopService.deleteShop(shopId);
    }
    @PutMapping(path = "/delete/{shopId}")
    public void  updateShop(@PathVariable("shopId") Long shopId,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String email){
        shopService.updateShop(shopId,name,email);
    }

    @DeleteMapping("/{sid}")
    public ResponseEntity<String> deleteCategory(@PathVariable("sid") Long sid){
        return new ResponseEntity<String>(shopService.deleteShop(sid), HttpStatus.OK);
    }

    @GetMapping("/nextSid")
    public Long nextSid(){
        return shopRepository.getNextSid();
    }
}
