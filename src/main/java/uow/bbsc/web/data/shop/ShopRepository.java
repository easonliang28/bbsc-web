package uow.bbsc.web.data.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {

    //SELECT * FROM shop WHERE email = ?
    @Query("SELECT s FROM Shop s WHERE s.email = ?1")
    Optional<Shop> findShopByEmail(String email);

    @Query("SELECT s FROM Shop s WHERE s.sid = ?1")
    Optional<Shop> findShopsBySid(Long sid);

    @Query("SELECT s FROM Shop s WHERE s.customer.id = ?1")
    Optional<List<Shop>> findShopById(Long id);
    @Query("SELECT s FROM Shop s WHERE s.customer.id = ?1")
    List<Shop> findShopById2(Long id);
    @Query("SELECT s FROM Shop s WHERE s.customer.id = ?1")
    Page<Shop> findShopById2(Long Id, Pageable shopPage);
    @Query("SELECT s FROM Shop s WHERE s.sid = ?1")
    Shop findShopBySid(Long sid);

    @Query("SELECT s FROM Shop s WHERE s.sid = ?1")
    Shop findShopBySid2(Long sid);

    @Query(value = "SELECT next_val FROM shop_sequence WHERE sequences_name = 'shop_sequences'",nativeQuery = true)
    Long getNextSid();

    @Query("SELECT s FROM Shop s WHERE s.customer.id = ?1")
    Optional<Shop> findShopsById(Long id);
}
