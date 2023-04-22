package fr.dopolytech.cdp.saga.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecreaseStockDTO {

    private Long orderId;

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
