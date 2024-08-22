package uow.bbsc.web.data.payCart;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uow.bbsc.web.data.item.ItemPage;

@AllArgsConstructor
@RestController
@RequestMapping("/database/pay_cart")
public class PayCartController {
    private final PayCartService payCartService;

    @GetMapping
    public ResponseEntity<Page<PayCart>> showItemPage(ItemPage itemPage,Long id){
        return new ResponseEntity<Page<PayCart>>(payCartService.getAllItem(itemPage,id), HttpStatus.OK);
    }
    @RequestMapping("/pay")
    public String pay(){

        return "";
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity<String> deleteCategory(@PathVariable("pid") Long pid){
        return new ResponseEntity<String>(payCartService.deleteItem(pid), HttpStatus.OK);
    }
}
