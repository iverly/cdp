package fr.dopolytech.cdp.shipping.domain;

public interface ShippingRepository {

    Shipping save(Shipping payment);

    Shipping getById(Long id);

    void delete(Shipping payment);

}
