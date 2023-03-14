package fr.dopolytech.cdp.catalog.domain;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

}
