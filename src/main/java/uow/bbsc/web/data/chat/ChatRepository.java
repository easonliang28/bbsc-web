package uow.bbsc.web.data.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {

    //SELECT * FROM chat WHERE email = ?
    @Query("SELECT c FROM Chat c WHERE c.cid = ?1")
    Optional<Chat> findChatById(Long cid);
    //SELECT * FROM chat WHERE content = ?
    @Query("SELECT c FROM Chat c WHERE c.content = ?1")
    List<Chat> findChatByContent(String content);

}

