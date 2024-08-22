package uow.bbsc.web.data.category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@DynamicUpdate
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "category")
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )

    private Long category_id;

    @Column private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @Column private List<String> tab;

    public Category(String name,
                    List<String> tab) {
        this.name = name;
        Collections.sort(tab);
        this.tab = tab;
    }
    public Category() {

    }
}
