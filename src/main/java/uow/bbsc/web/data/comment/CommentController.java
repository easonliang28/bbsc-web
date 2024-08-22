package uow.bbsc.web.data.comment;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
@RequestMapping("/database/comment")
public class CommentController {
    private final CommentService commentService;
    @DeleteMapping(path = "/delete/{cmID}")
    public void  deleteCustomer(@PathVariable("cmID") Long cmID){
        commentService.deleteComment(cmID);
    }
}
