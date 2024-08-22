package uow.bbsc.web.data.payCart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayCartRepository extends JpaRepository<PayCart,Long> {

    //SELECT * FROM pay_cart WHERE email = ?
    @Query("SELECT p FROM PayCart p WHERE p.pid = ?1")
    Optional<PayCart> findPayCartById(Long pid);
    @Query("SELECT p FROM PayCart p WHERE p.customer.id = ?1")
    List<PayCart> findPayCartById2(Long id);

    @Query(value = "SELECT * FROM bbsc.pay_cart p WHERE p.id = ?1",nativeQuery = true)
    Page<PayCart> findPayCartById(Pageable pageable,Long id);

    @Query("SELECT p FROM PayCart p WHERE p.customer.id = ?1")
    List<PayCart> findPayCartsById(Long id);

    @Query("SELECT p FROM PayCart p WHERE p.customer.id = ?1")
    Page<PayCart> findAllItem(Pageable pageable, Long id);

    @Query("SELECT COUNT(i) FROM Item i " +
            "LEFT JOIN PayCart p ON i.iid = p.item.iid " +
            " WHERE p.customer.id = ?1")
    int countById(Long id);

    @Query(value = "SELECT * FROM bbsc.pay_cart p WHERE p.id = ?1 AND p.pay_time IS NULL",nativeQuery = true)
    List<PayCart> findNotPayPayCartsById(Long id);
}

