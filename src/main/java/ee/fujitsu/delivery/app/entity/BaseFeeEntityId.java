package ee.fujitsu.delivery.app.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class BaseFeeEntityId implements Serializable {
    private Long cityId;
    private Long vehicleId;
}
