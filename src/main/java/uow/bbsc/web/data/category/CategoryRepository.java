package uow.bbsc.web.data.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    //SELECT * FROM chat WHERE email = ?
    @Query("SELECT c FROM Category c WHERE c.category_id = ?1")
    Optional<Category> findCategoryById(Long category_id);
    @Query("SELECT c FROM Category c WHERE c.category_id = ?1")
    Category findCategoryById2(Long category_id);
    @Query("SELECT c FROM Category c order by c.tab.size DESC, c.name")
    List<Category> findAllOrderByCountTab();


}

