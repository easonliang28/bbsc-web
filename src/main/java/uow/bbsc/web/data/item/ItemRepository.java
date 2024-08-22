package uow.bbsc.web.data.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uow.bbsc.web.data.comment.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query("SELECT i FROM Item i")
    Page<Item> findAll(Pageable pageable);
    //SELECT * FROM item WHERE iid = ?
    @Query("SELECT i FROM Item i WHERE i.iid = ?1")
    Optional<Item> findItemByIid(Long iid);
    @Query("SELECT i FROM Item i WHERE i.iid = ?1")
    Item findItemByIid2(Long iid);
    //SELECT * FROM item WHERE iid = ?
    @Query("SELECT i FROM Item i WHERE i.shop.sid = ?1")
    Optional<List<Item>> findItemBySid(Long shopId);
    @Query("SELECT i FROM Item i WHERE i.name = ?1")
    Optional<Item> findItemByName(String name);
    @Query("SELECT i FROM Item i WHERE i.category.category_id = ?1")
    List<Item> findItemByCategoryCategory_id(Long category_id);

    @Query("SELECT i FROM Item i WHERE i.shop.sid = ?1")
    List<Item> findItemsBySid(Long sid);

    @Query("SELECT i FROM Item i " +
            "LEFT JOIN PayCart p ON i.iid = p.item.iid " +
            " WHERE p.customer.id = ?1")
    Page<Item> findItemByIid(Long id, Pageable itemPage);
    @Query("SELECT i FROM Item i " +
            " WHERE i.shop.customer.id = ?1")
    Page<Item> findItemById(Long id, Pageable itemPage);
    @Query(value="SELECT * FROM item i WHERE i.category_id = ?1",nativeQuery = true)
    List<Item> findItemsByCategoryAndTab(Long categoryId, String tab);

    @Query(value="SELECT * FROM item i " +
            "WHERE i.price >= ?1 AND i.price <= ?2",nativeQuery = true)
    Page<Item> getItemPage(int min, int max, Pageable pageable);

    @Query(value="SELECT * FROM item i " +
            "LEFT JOIN bbsc.category cc ON i.category_id = cc.category_id " +
            "LEFT JOIN bbsc.category_tab t ON cc.category_id = t.category_category_id " +
            "LEFT JOIN bbsc.item_tab it ON i.iid = it.item_iid " +
            "WHERE i.category_id = ?1 AND i.price >= ?2 AND i.price <= ?3 AND " +
            "(MATCH (it.tab) AGAINST (?4 IN BOOLEAN MODE) OR " +
            //"MATCH (cc.name) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "MATCH (i.description) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "MATCH (i.name) AGAINST (?4 IN BOOLEAN MODE)" +
            ")",nativeQuery = true)
    Page<Item> findItemPageByCategoryAndTabAndPrice(Long categoryId, int min, int max, String tab, Pageable pageable);

    @Query(value="SELECT * FROM item i " +
            "LEFT JOIN bbsc.category cc ON i.category_id = cc.category_id " +
            "LEFT JOIN bbsc.category_tab t ON cc.category_id = t.category_category_id " +
            "LEFT JOIN bbsc.item_tab it ON i.iid = it.item_iid " +
            "LEFT JOIN bbsc.shop shop ON i.sid = shop.sid " +
            "WHERE i.category_id = ?1 AND i.price >= ?2 AND i.price <= ?3 AND " +
            "(MATCH (it.tab) AGAINST (?5 IN BOOLEAN MODE) OR " +
            //"MATCH (cc.name) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "MATCH (i.description) AGAINST (?5 IN BOOLEAN MODE) OR " +
            "MATCH (i.name) AGAINST (?5 IN BOOLEAN MODE)" +
            ") AND " +
            "(MATCH (it.tab) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "MATCH (i.name) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "i.name like ?4 OR " +
            "MATCH (i.description) AGAINST (?4 IN BOOLEAN MODE) OR "+
            "MATCH (shop.name) AGAINST (?4 IN BOOLEAN MODE) " +
            ")",nativeQuery = true)
    Page<Item> getItemPageByCategoryIdAndKeywordAndTab(Long categoryId, int min, int max, String keyword, String tab, Pageable pageable);
    @Query(value="SELECT * FROM item i " +
            "LEFT JOIN bbsc.category cc ON i.category_id = cc.category_id " +
            "LEFT JOIN bbsc.category_tab t ON cc.category_id = t.category_category_id " +
            "LEFT JOIN bbsc.item_tab it ON i.iid = it.item_iid " +
            "LEFT JOIN bbsc.shop shop ON i.sid = shop.sid " +
            "WHERE i.category_id = ?1 AND i.price >= ?2 AND i.price <= ?3 AND " +
            "(MATCH (it.tab) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "MATCH (i.name) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "i.name like ?4 OR " +
            "MATCH (i.description) AGAINST (?4 IN BOOLEAN MODE) OR "+
            "MATCH (shop.name) AGAINST (?4 IN BOOLEAN MODE) " +
            ")",nativeQuery = true)
    Page<Item> getItemPageByCategoryIdAndKeyword(Long categoryId, int min, int max, String keyword, Pageable pageable);

    @Query(value="SELECT * FROM item i " +
            "LEFT JOIN bbsc.category cc ON i.category_id = cc.category_id " +
            "LEFT JOIN bbsc.category_tab t ON cc.category_id = t.category_category_id " +
            "LEFT JOIN bbsc.item_tab it ON i.iid = it.item_iid " +
            "LEFT JOIN bbsc.shop shop ON i.sid = shop.sid " +
            "WHERE i.price >= ?1 AND i.price <= ?2 AND " +
            "(MATCH (it.tab) AGAINST (?3 IN BOOLEAN MODE) OR " +
            "MATCH (i.name) AGAINST (?3 IN BOOLEAN MODE) OR " +
            "i.name like ?3 OR " +
            "MATCH (i.description) AGAINST (?3 IN BOOLEAN MODE) OR "+
            "MATCH (shop.name) AGAINST (?3 IN BOOLEAN MODE) " +
            ")",nativeQuery = true)
    Page<Item> getItemPageByKeyword(int min, int max, String keyword, Pageable pageable);

    @Query(value="SELECT * FROM item i " +
            "LEFT JOIN bbsc.category cc ON i.category_id = cc.category_id " +
            "LEFT JOIN bbsc.category_tab t ON cc.category_id = t.category_category_id " +
            "LEFT JOIN bbsc.item_tab it ON i.iid = it.item_iid " +
            "WHERE i.category_id = ?1 AND i.price >= ?2 AND i.price <= ?3 ",nativeQuery = true)
    Page<Item> findItemPageByCategoryId(Long categoryId, int min, int max, Pageable pageable);

    //-------------------------------------for search suggestion--------------------------------------------//

    @Query(value="SELECT * FROM item i " +
            "LEFT JOIN bbsc.category cc ON i.category_id = cc.category_id " +
            "LEFT JOIN bbsc.category_tab t ON cc.category_id = t.category_category_id " +
            "LEFT JOIN bbsc.item_tab it ON i.iid = it.item_iid " +
            "WHERE i.category_id = ?1 AND i.price >= ?2 AND i.price <= ?3 AND " +
            "(MATCH (it.tab) AGAINST (?4 IN BOOLEAN MODE) OR " +
            //"MATCH (cc.name) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "MATCH (i.description) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "MATCH (i.name) AGAINST (?4 IN BOOLEAN MODE) "+
            ")" +
            "GROUP BY i.name ",nativeQuery = true)
    List<Item> findItemListByCategoryAndTabAndPrice(Long categoryId, int min, int max, String tab, Pageable pageable);

    @Query(value="SELECT * FROM item i " +
            "LEFT JOIN bbsc.category cc ON i.category_id = cc.category_id " +
            "LEFT JOIN bbsc.category_tab t ON cc.category_id = t.category_category_id " +
            "LEFT JOIN bbsc.item_tab it ON i.iid = it.item_iid " +
            "LEFT JOIN bbsc.shop shop ON i.sid = shop.sid " +
            "WHERE i.category_id = ?1 AND i.price >= ?2 AND i.price <= ?3 AND " +
            "(MATCH (it.tab) AGAINST (?5 IN BOOLEAN MODE) OR " +
            //"MATCH (cc.name) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "MATCH (i.description) AGAINST (?5 IN BOOLEAN MODE) OR " +
            "MATCH (i.name) AGAINST (?5 IN BOOLEAN MODE)" +
            ") AND " +
            "(MATCH (it.tab) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "MATCH (i.name) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "i.name like ?4 OR " +
            "MATCH (i.description) AGAINST (?4 IN BOOLEAN MODE) OR "+
            "MATCH (shop.name) AGAINST (?4 IN BOOLEAN MODE) " +
            ")" +
            "GROUP BY i.name ",nativeQuery = true)
    List<Item> getItemListByKeywordAndCategoryIdAndTab(Long categoryId, int min, int max, String keyword, String tab, Pageable pageable);

    @Query(value="SELECT * FROM item i " +
            "LEFT JOIN bbsc.category cc ON i.category_id = cc.category_id " +
            "LEFT JOIN bbsc.category_tab t ON cc.category_id = t.category_category_id " +
            "LEFT JOIN bbsc.item_tab it ON i.iid = it.item_iid " +
            "LEFT JOIN bbsc.shop shop ON i.sid = shop.sid " +
            "WHERE i.category_id = ?1 AND i.price >= ?2 AND i.price <= ?3 AND " +
            "(MATCH (it.tab) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "MATCH (i.name) AGAINST (?4 IN BOOLEAN MODE) OR " +
            "i.name like ?4 OR " +
            "MATCH (i.description) AGAINST (?4 IN BOOLEAN MODE) OR "+
            "MATCH (shop.name) AGAINST (?4 IN BOOLEAN MODE)" +
            ")" +
            "GROUP BY i.name ",nativeQuery = true)
    List<Item> getItemListByCategoryIdAndKeyword(Long categoryId, int min, int max, String keyword, Pageable pageable);

    @Query(value="SELECT * FROM item i " +
            "LEFT JOIN bbsc.category cc ON i.category_id = cc.category_id " +
            "LEFT JOIN bbsc.category_tab t ON cc.category_id = t.category_category_id " +
            "LEFT JOIN bbsc.item_tab it ON i.iid = it.item_iid " +
            "LEFT JOIN bbsc.shop shop ON i.sid = shop.sid " +
            "WHERE i.price >= ?1 AND i.price <= ?2 AND " +
            "(MATCH (it.tab) AGAINST (?3 IN BOOLEAN MODE) OR " +
            "MATCH (i.name) AGAINST (?3 IN BOOLEAN MODE) OR " +
            "i.name like ?3 OR " +
            "MATCH (i.description) AGAINST (?3 IN BOOLEAN MODE) OR "+
            "MATCH (shop.name) AGAINST (?3 IN BOOLEAN MODE)" +
            ")" +
            "GROUP BY i.name ",nativeQuery = true)
    List<Item> getItemListByKeyword(int min, int max, String keyword, Pageable pageable);
    @Query(value="SELECT * FROM item i " +
            "LEFT JOIN bbsc.category cc ON i.category_id = cc.category_id " +
            "LEFT JOIN bbsc.category_tab t ON cc.category_id = t.category_category_id " +
            "LEFT JOIN bbsc.item_tab it ON i.iid = it.item_iid " +
            "WHERE i.price >= ?1 AND i.price <= ?2 " +
            "GROUP BY i.name ",nativeQuery = true)
    List<Item> getItemList(int min, int max, Pageable pageable);


    @Query(value = "SELECT next_val FROM item_sequence",nativeQuery = true)
    Long getNextIid();

    @Query("SELECT i.comment FROM Item i " +
            "LEFT JOIN Comment c ON i.comment.cmID = c.cmID " +
            " WHERE i.iid = ?1")
    Comment findCommentByIid(Long iid);
    @Query("SELECT c FROM Comment c " +
            " WHERE c.replyComment.cmID = ?1")
    List<Comment> findAllCommentByIid(Long cmID);
}