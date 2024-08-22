package uow.bbsc.web.data.favourites;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uow.bbsc.web.data.item.Item;

import java.util.List;
import java.util.Optional;
@Repository
public interface FavouritesRepository extends JpaRepository<Favourites,Long> {

    //SELECT * FROM item WHERE iid = ?
    @Query("SELECT p FROM Favourites p WHERE p.customer.id = ?1")
    Optional<Favourites> findFavoritesById(Long id);
    //SELECT * FROM item WHERE iid = ?
    @Query("SELECT p FROM Favourites p WHERE p.fid = ?1")
    Optional<Favourites> findFavoritesByFid(Long Fid);
    //SELECT * FROM item WHERE iid = ?
    @Query("SELECT p FROM Favourites p WHERE p.item.iid = ?1")
    Optional<Favourites> findFavoritesByIid(Long iid);

    @Query("SELECT i FROM Favourites f "+
            "LEFT JOIN Item i ON i.iid = f.item.iid " +
            " WHERE f.customer.id = ?1")
    Page<Item> findAllItem(Pageable pageable,Long id);
    @Query("SELECT f FROM Favourites f " +
            "LEFT JOIN Item i ON i.iid = f.item.iid " +
            " WHERE f.customer.id = ?1 AND f.item.iid = ?2")
    List<Favourites> findFavouritesItem(Long id, Long iid);

    @Query("SELECT f FROM Favourites f,Item i WHERE f.customer.id = ?1 ")
    List<Favourites> findAllFavouritesItemById(Long id);

}