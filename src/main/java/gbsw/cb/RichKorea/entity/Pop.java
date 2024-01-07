package gbsw.cb.RichKorea.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String area_a;
    @Column
    private String area;
    @Column
    private Integer hit;
}
