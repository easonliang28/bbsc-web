package uow.bbsc.web.data.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.customer.CustomerRepository;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.item.ItemRepository;
import uow.bbsc.web.page.comment.CommentRegistrationRequest;
import uow.bbsc.web.page.comment.CommentRelyRequest;

@AllArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CustomerRepository CustomerRepository;
    private final ItemRepository itemRepository ;
    public String deleteComment(Long cmID) {

        Comment comment = commentRepository.findCommentById(cmID);
        commentRepository.deleteById(cmID);
        return "deleted!";
    }

    public Long newComment(CommentRegistrationRequest request) {
        Customer customer=null;
        if(request.getId() !=null)customer=  CustomerRepository.findCustomerById(request.getId());
        Comment comment = new Comment(request.getComment(),
                null,request.getTime(),customer
               );
        Item item=itemRepository.findItemByIid2(request.getIid());
        if(item.getComment() ==null) {
            item.setComment(comment);
        }
        else{
            comment.setReplyComment(item.getComment());
        }
        commentRepository.save(comment);
        itemRepository.save(item);
        return comment.getCmID();
    }

    public Long newRelyComment(CommentRelyRequest request) {
        Comment comment = new Comment(request.getComment(),
                commentRepository.findCommentById(request.getRelyId()),request.getTime(),CustomerRepository.findCustomerById(request.getId()));
        commentRepository.save(comment);
        return comment.getCmID();
    }

}
