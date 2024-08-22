package uow.bbsc.web.page.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uow.bbsc.web.data.comment.Comment;
import uow.bbsc.web.data.comment.CommentRepository;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.item.ItemRepository;
import uow.bbsc.web.page.HTMLComponents;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentPageService extends HTMLComponents {
    private  final CommentRepository commentRepository;
    private  final ItemRepository itemRepository;
    public String getComment(Long iid) {
        return HTMLCard(HTMLHeading(3,"Comment",""),
                getAddComment(iid)+getAllComment(iid),"","");
    }

    private String getAddComment(Long iid) {
        String content = "<div id='iid' iid='"+iid+"'></div>";
        content +=HTMLElement("textarea","add-comment","form-control mb-3 add-comment","","value placeholder=\"Comment It!\"","");
        content +=HTMLElement("button","add-comment-btn","btn btn-primary mb-3 add-comment-btn","","style='width=100%' value ","Add Comment!");
        return content;
    }

    private String getAllComment(Long iid) {
        Comment comment = itemRepository.findCommentByIid(iid);
        if(comment==null)return HTMLHeading(4,"No comment","");
        List<Comment> commentList = itemRepository.findAllCommentByIid(comment.getCmID());
        String block = HTMLCard(getUserName(comment.getCustomer()),getFirstCommentBlock(comment),"mb-3","");
        for (int i= 0;i<commentList.size();i++) {
            String name = "Guest";
            if(commentList.get(i).getCustomer()!=null)
                name=commentList.get(i).getCustomer().getName();

            block += HTMLCard(HTMLHeading(5, name , ""), getCommentBlock(commentList.get(i)), "mb-3", "");
        }
        return block;
    }

    private String getCommentBlock(Comment comment) {
        String content = "";
        List<Comment> relyCommentList = commentRepository.findCommentByReyplyId(comment.getCmID());
        content += HTMLElement("p","","", comment.getComment());
        for (int i= 0;i<relyCommentList.size();i++)
            content+=HTMLCard(HTMLHeading(5,getUserName(relyCommentList.get(i).getCustomer()),""),getCommentBlock(relyCommentList.get(i)),"mb-3","") ;
        return content;
    }
    private String getFirstCommentBlock(Comment comment) {
        String content = "";
        List<Comment> relyCommentList = commentRepository.findCommentByReyplyId(comment.getCmID());
        content += HTMLElement("p","","", comment.getComment());
        return content;
    }

    public String getUserName(Customer customer){
        if(customer==null)
            return "Guest";
        else return customer.getName();
    }
}
