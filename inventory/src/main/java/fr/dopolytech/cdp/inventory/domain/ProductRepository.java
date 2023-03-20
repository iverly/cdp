package fr.dopolytech.cdp.inventory.domain;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

}
