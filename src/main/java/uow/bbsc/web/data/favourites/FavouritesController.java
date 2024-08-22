package uow.bbsc.web.data.favourites;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.item.ItemPage;

@AllArgsConstructor
@RestController
@RequestMapping("/database/favourites")
public class FavouritesController {

    private final FavouritesService favouritesService;
    private final FavouritesRepository favouritesRepository;
    @GetMapping
    public ResponseEntity<Page<Item>> showFavouritesItemPage(Long id,ItemPage itemPage){

//        List<Favourites> f= favouritesRepository.findAllFavouritesItemById(id);
//        List<Item> i= new ArrayList<>();
//        for (int j=0;j<f.size();j++)
//            i.add(f.get(j).getItem());
        return new ResponseEntity<Page<Item>>(favouritesService.getItems(itemPage,id), HttpStatus.OK);
    }
    @DeleteMapping(path = "/{fid}")
    public String deleteCustomer(@PathVariable("fid") Long fid){

        favouritesRepository.delete(favouritesRepository.getById(fid));
        return "deleted!";
    }
}
