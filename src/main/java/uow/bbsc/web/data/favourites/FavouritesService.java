package uow.bbsc.web.data.favourites;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.item.ItemPage;

import java.util.List;

@AllArgsConstructor
@Service
public class FavouritesService {

    private final FavouritesRepository favouritesRepository;

    public Page<Item> getItems(ItemPage itemPage,Long id){
        Pageable pageable = PageRequest.of(
                itemPage.getPageNumber(),
                itemPage.getPageSize(),
                Sort.by(itemPage.getSortDirection(),itemPage.getSortBy()));
        return favouritesRepository.findAllItem(pageable, id);
    }

    public String addItem(Favourites favourites){

        favouritesRepository.save(favourites);
        return "added item!";
    }


    public String deleteCartItem(Long favoritesId) {
        boolean exists = favouritesRepository.existsById(favoritesId);
        if(!exists){
            throw new IllegalStateException("favourites("+favoritesId+") does not exists");
        }
        favouritesRepository.deleteById(favoritesId);
        return "favourites deleted!";
    }


    @Transactional
    public String updateShop(Long favoritesId,String name,String email) {

        Favourites favourites = favouritesRepository.findById(favoritesId)
                .orElseThrow(()-> new IllegalStateException(
                        "item("+favoritesId+") does not exists"
                ));
        favouritesRepository.save(favourites);
        return "favourites updated!";
    }
}
