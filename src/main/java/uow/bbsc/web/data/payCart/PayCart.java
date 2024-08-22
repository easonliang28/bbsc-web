package uow.bbsc.web.data.payCart;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import uow.bbsc.web.data.customer.Customer;
import uow.bbsc.web.data.item.Item;
import uow.bbsc.web.data.shop.Shop;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "pay_cart")
public class PayCart {

    @Id
    @SequenceGenerator(
            name = "pay_cart_sequence",
            sequenceName = "pay_cart_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pay_cart_sequence"
    )
    private Long pid;
    @ManyToOne
    @JoinColumn(name = "sid")
    private Shop shop;
    @ManyToOne
    @JoinColumn(name = "id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "iid")
    private Item item;
    @Column private int qty;
    @Column private LocalDateTime pay_time;
    @Column private String pay_type;

    @Column private int rate;
    @Column private String status;

    public PayCart(Shop shop,
                   Customer customer,
                   Item item,
                   int qty,
                   LocalDateTime pay_time,
                   String pay_type,
                   int rate,
                   String status) {
        this.shop = shop;
        this.customer = customer;
        this.item = item;
        this.qty = qty;
        this.pay_time = pay_time;
        this.pay_type = pay_type;
        this.rate = rate;
        this.status = status;
    }

    public PayCart() {}

}
