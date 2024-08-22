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
public class CommentRelyRequest {
    private Long id;
    private String comment;
    private Long relyId;
    public LocalDateTime getTime(){return LocalDateTime.now();}
}
