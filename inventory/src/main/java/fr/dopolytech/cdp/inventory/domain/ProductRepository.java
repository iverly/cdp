package fr.dopolytech.cdp.inventory.domain;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product findById(String id);

    Product save(Product product);

    Product findByProductId(String productId);

}
