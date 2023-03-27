import React, { useCallback, useMemo } from "react";
import { Button, Card, Text, Metric } from "@tremor/react";
import { format } from "currency-formatter";
import {
  useGetShoppingCartQuery,
  useUpdateShoppingCartMutation,
} from "../redux/service/shopping-cart";
import { nanoid } from "@reduxjs/toolkit";
import { cloneDeep } from "lodash";

export default function ProductCard({ product }) {
  const { data, isLoading, isError } = useGetShoppingCartQuery();
  const [triggerUpdateShoppingCart] = useUpdateShoppingCartMutation();

  const handleAddToCheckout = useCallback(() => {
    const newShoppingCart = cloneDeep(data);

    // check if shopping cart has products
    if (!newShoppingCart.products) {
      newShoppingCart.products = [];
    }

    // check if product is already in the shopping cart
    const productInShoppingCart = newShoppingCart.products.find(
      (p) => p.productId === product.id
    );

    if (productInShoppingCart) {
      // if product is already in the shopping cart, increase the quantity
      productInShoppingCart.quantity += 1;
    } else {
      // if product is not in the shopping cart, add it
      newShoppingCart.products.push({
        productId: product.id,
        name: product.name,
        price: product.price,
        quantity: 1,
      });
    }

    // update the shopping cart
    triggerUpdateShoppingCart(newShoppingCart);
  }, [triggerUpdateShoppingCart, data]);

  const isOutOfStock = useMemo(() => {
    if (isLoading || isError) return false;

    const productInShoppingCart = data?.products?.find(
      (p) => p.productId === product.id
    );

    return product.quantity - (productInShoppingCart?.quantity || 0) <= 0;
  }, [isLoading, isError, data, product]);

  return (
    <Card key={`${product.id}-${nanoid()}`}>
      <Metric>{product.name}</Metric>
      <Text>{format(product.price, { code: "USD" })}</Text>
      <Button
        className="mt-2"
        onClick={handleAddToCheckout}
        disabled={isOutOfStock}
      >
        {isOutOfStock ? "Out of stock" : "Add to checkout"}
      </Button>
    </Card>
  );
}
