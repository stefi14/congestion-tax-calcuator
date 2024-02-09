package volvo.test.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "toll_prices")
public class TollPrices implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private int price;
    private String type;
    @Column(name = "city_id")
    private Long cityId;
    private String cityName;

}
