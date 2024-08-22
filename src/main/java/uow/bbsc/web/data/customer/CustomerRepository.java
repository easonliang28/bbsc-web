package uow.bbsc.web.data.customer;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    final Pageable customerPage = (Pageable) PageRequest.of(
            0,
            48,
            Sort.by(Sort.Direction.ASC,"name"));
    //SELECT * FROM customer WHERE email = ?
    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    Optional<Customer> findCustomerByEmail(String email);
    //SELECT * FROM customer WHERE email = ?
    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    Customer findCustomerByEmail2(String email);
    //SELECT * FROM customer WHERE email = ?
    @Query("SELECT c FROM Customer c WHERE c.id = ?1")
    Customer findCustomerById(Long id);

    @Query("SELECT c FROM Customer c WHERE c.name like %:name%")
    Page<Customer> findByNameIsLike(@Param("name") String name,Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.id = ?1")
    Optional<Customer> findCustomerById2(Long id);
}
