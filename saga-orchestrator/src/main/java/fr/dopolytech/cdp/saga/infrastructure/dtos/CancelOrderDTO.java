package fr.dopolytech.cdp.saga.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderDTO {

    private Long orderId;

}
