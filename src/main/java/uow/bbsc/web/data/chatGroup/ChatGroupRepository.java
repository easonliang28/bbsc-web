package uow.bbsc.web.data.chatGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ChatGroupRepository extends JpaRepository<ChatGroup,Long> {

    //SELECT * FROM chat WHERE email = ?
    @Query("SELECT c FROM ChatGroup c WHERE c.gid = ?1")
    Optional<ChatGroup> findChatById(Long cid);
    //SELECT * FROM chat WHERE email = ?
    @Query("SELECT c FROM ChatGroup c WHERE c.customer.id = ?1")
    List<ChatGroup> findChatGroupByCustomerId(Long id);
}
