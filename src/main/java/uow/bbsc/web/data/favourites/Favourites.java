package uow.bbsc.web.data.favourites;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.item.Item;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "favourites")
public class Favourites {
    @Id
    @SequenceGenerator(
            name = "favorites_sequence",
            sequenceName = "favorites_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "favorites_sequence"
    )
    private Long fid;
    @ManyToOne
    @JoinColumn(name = "id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "iid")
    private Item item;
    @Column
    private LocalDateTime time;

    public Favourites(Customer customer, Item item,LocalDateTime time) {
        this.customer = customer;
        this.item = item;
        this.time = time;
    }

    public Favourites() {

    }
}
