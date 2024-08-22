package uow.bbsc.web.page.comment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CommentRegistrationRequest {
    private Long id;
    private Long iid;
    private String comment;
    public LocalDateTime getTime(){return LocalDateTime.now();}
    CommentRegistrationRequest(){}
}
