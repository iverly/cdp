package fr.dopolytech.cdp.catalog.infrastructure.controllers.forms;

import fr.dopolytech.cdp.catalog.domain.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateProductForm {

    private String name;

    private float price;

    public Product toProduct() {
        return new Product(this.name, this.price);
    }

}
