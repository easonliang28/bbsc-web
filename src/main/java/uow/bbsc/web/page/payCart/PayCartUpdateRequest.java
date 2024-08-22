package uow.bbsc.web.page.payCart;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uow.bbsc.web.data.comment.Comment;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PayCartUpdateRequest {
    private Long pid;
    private int qty;
    private String payType;
    private Comment comment;
    private int rate;
    private boolean pay;
}
