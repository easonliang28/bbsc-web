package uow.bbsc.web.data.shop;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import uow.bbsc.web.data.customer.Customer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicUpdate
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "shop")
public class Shop {


    @Id
    @SequenceGenerator(
            name = "shop_sequence",
            sequenceName = "shop_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "shop_sequence"
    )
    private Long sid;
    @ManyToOne
    @JoinColumn(name = "id")
    private Customer customer;
    @Column private String name;
    @Column private String address;
    @Column private String email;
    @Column private String tel;
    @Column private String icon;
    @Column private LocalDate registered_date;
    @Column private String description;

    public Shop(Customer customer,
                String name,
                String address,
                String email,
                String tel,
                String icon,
                LocalDate registered_date,
                String description) {
        this.customer = customer;
        this.name = name;
        this.address = address;
        this.email = email;
        this.tel = tel;
        this.icon = icon;
        this.registered_date = registered_date;
        this.description = description;
    }

    public Shop() {

    }
}
