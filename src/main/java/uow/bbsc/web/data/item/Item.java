package uow.bbsc.web.data.item;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import uow.bbsc.web.data.category.Category;
import uow.bbsc.web.data.comment.Comment;
import uow.bbsc.web.data.shop.Shop;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DynamicUpdate
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "item")
public class Item {

    @Id
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_sequence"
    )
    private Long iid;
    @ManyToOne
    @JoinColumn(name = "sid")
    private Shop shop;
    @Column private String name;
    @Column private LocalDateTime update_time;
    @Column private String description;
    @Column private double price;
    @Column private String icon;
    @Column private int stock;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ElementCollection
    @Column private List<String> tab;

    @ManyToOne
    private Comment comment;

    public Item(Shop shop,
                String name,
                LocalDateTime update_time,
                String description,
                double price,
                String icon,
                int stock,
                Category category,
                List<String> tab,
                Comment comment) {
        this.shop = shop;
        this.name = name;
        this.update_time = update_time;
        this.description = description;
        this.price = price;
        this.icon = icon;
        this.stock = stock;
        this.category = category;
        this.tab = tab;
        this.comment = comment;
    }

    public Item() {

    }
}
