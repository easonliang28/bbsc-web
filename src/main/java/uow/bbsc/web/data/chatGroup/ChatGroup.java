package uow.bbsc.web.data.chatGroup;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import uow.bbsc.web.data.chat.Chat;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.shop.Shop;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "chat_group")
public class ChatGroup {

    @Id
    @SequenceGenerator(
            name = "chat_group_sequence",
            sequenceName = "chat_group_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "chat_group_sequence"
    )

    private Long gid;
    @ManyToOne
    @JoinColumn(name = "id")
    private Customer customer;
    private Long groupId;


    public ChatGroup(Customer customer, Long groupId){
        this.customer = customer;
        this.groupId = groupId;
    }

    public ChatGroup() {}
}
