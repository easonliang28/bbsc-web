package uow.bbsc.web.data.comment;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    final Pageable customerPage = (Pageable) PageRequest.of(
            0,
            48,
            Sort.by(Sort.Direction.DESC,"time"));
    @Query("SELECT c FROM Comment c WHERE c.replyComment.cmID = ?1")
    List<Comment> findCommentByReyplyId(Long replyId);

    @Query("SELECT c FROM Comment c WHERE c.cmID = ?1")
    Comment findCommentById(Long cmID);
}
