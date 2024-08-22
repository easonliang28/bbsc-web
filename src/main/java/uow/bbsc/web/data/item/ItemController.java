package uow.bbsc.web.data.item;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/database/item")
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;
    @GetMapping
    public ResponseEntity<Page<Item>> showItemPage(ItemPage itemPage){
        return new ResponseEntity<Page<Item>>(itemService.getItems(itemPage), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Item> addItem(Item item){
        return new ResponseEntity<Item>(itemService.addItem(item), HttpStatus.OK);
    }

    @DeleteMapping("/{iid}")
    public ResponseEntity<String> deleteCategory(@PathVariable("iid") Long iid){
        return new ResponseEntity<String>(itemService.deleteItem(iid), HttpStatus.OK);
    }

    @GetMapping("/nextIid")
    public Long nextSid(){
        return itemRepository.getNextIid();
    }
}
