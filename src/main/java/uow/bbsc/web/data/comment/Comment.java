package uow.bbsc.web.data.comment;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import uow.bbsc.web.data.customer.Customer;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@DynamicUpdate
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "comment")
public class Comment {
    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    private Long cmID;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Comment replyComment;
    private LocalDateTime time;

    @ManyToOne
    private Customer customer;

    public Comment(String comment, Comment replyComment, LocalDateTime time,Customer customer) {
        this.comment = comment;
        this.replyComment = replyComment;
        this.time = time;
        this.customer = customer;
    }
    public Comment(){}

}
