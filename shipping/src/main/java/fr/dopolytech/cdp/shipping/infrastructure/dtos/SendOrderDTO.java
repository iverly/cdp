package fr.dopolytech.cdp.shipping.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendOrderDTO {

    private Long orderId;

    private Long paymentId;

    private float price;

    private List<OrderProduct> products;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderProduct {

        private String productId;

        private int quantity;

    }

}
