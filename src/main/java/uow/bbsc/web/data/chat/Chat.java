package uow.bbsc.web.data.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import uow.bbsc.web.data.chatGroup.ChatGroup;
import uow.bbsc.web.data.customer.Customer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "chat")
public class Chat {
    @Id
    @SequenceGenerator(
            name = "chat_sequence",
            sequenceName = "chat_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "chat_sequence"
    )

    private Long cid;

    @ManyToOne
    @JoinColumn(name = "gid")
    private ChatGroup chatGroup;
    @Column private LocalDateTime time;
    @Column private String content;

    public Chat(ChatGroup chatGroup,
                Customer customer,
                LocalDateTime time,
                String content) {
        this.chatGroup = chatGroup;
        this.time = time;
        this.content = content;
    }

    public Chat() {

    }
}
