package fr.dopolytech.cdp.catalog.infrastructure.repositories;

import fr.dopolytech.cdp.catalog.domain.Product;
import fr.dopolytech.cdp.catalog.domain.ProductRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMongoRepository extends MongoRepository<Product, String>, ProductRepository {
}
