import {
  Button,
  Card,
  Grid,
  Metric,
  List,
  ListItem,
  Flex,
  Bold,
  Text,
} from "@tremor/react";
import { useGetShoppingCartQuery } from "../redux/service/shopping-cart";
import { useUpdateShoppingCartMutation } from "../redux/service/shopping-cart";
import { useCreateOrderMutation } from "../redux/service/order";
import { format } from "currency-formatter";
import { nanoid } from "@reduxjs/toolkit";
import { useCallback } from "react";
import { cloneDeep } from "lodash";

export default function Checkout() {
  const { data, isLoading, error, isError } = useGetShoppingCartQuery();
  const [triggerUpdateShoppingCart] = useUpdateShoppingCartMutation();
  const [
    triggerCreateOrder,
    { isLoading: isLoadingOrder, isError: isErrorOrder },
  ] = useCreateOrderMutation();

  const handleClearCheckout = useCallback(() => {
    const newShoppingCart = cloneDeep(data);

    newShoppingCart.products = [];

    triggerUpdateShoppingCart(newShoppingCart);
  }, [data, triggerUpdateShoppingCart]);

  const handlePlaceOrder = useCallback(() => {
    triggerCreateOrder({
      price: data.products.reduce(
        (acc, product) => acc + product.price * product.quantity,
        0
      ),
      products: data.products.map((product) => ({
        productId: product.productId,
        quantity: product.quantity,
      })),
    });

    handleClearCheckout();
  }, [data, triggerCreateOrder]);

  return (
    <Card decoration="top" className="h-fit">
      <Metric>Checkout</Metric>
      {/* // Error state */}
      {(isError || isErrorOrder) && (
        <div role="alert" className="mt-4">
          <div className="bg-red-500 text-white font-bold rounded-t px-4 py-2">
            Danger
          </div>
          <div className="border border-t-0 border-red-400 rounded-b bg-red-100 px-4 py-3 text-red-700">
            <p>
              There was an error while fetching our data. Please retry in a few
              seconds. {error?.message ? `(${error.message})` : null}
            </p>
          </div>
        </div>
      )}

      {/* // Loading state */}
      {isLoading && <p>Loading ...</p>}

      {/* // Success state */}
      {!isError && !isLoading && (
        <div className="divide-y">
          {(!data.products || data.products.length === 0) && (
            <div className="mt-4">
              <Text>Your checkout is empty.</Text>
            </div>
          )}
          {data.products && data.products.length > 0 && (
            <>
              <List className="mt-4">
                {data.products.map((product) => (
                  <ListItem key={`${product.id}-${nanoid()}`}>
                    <Flex justifyContent="start" className="truncate space-x-4">
                      <div className="truncate">
                        <Text className="truncate">
                          <Bold>{product.name}</Bold>
                        </Text>
                        <Text className="truncate">{`x${product.quantity}`}</Text>
                      </div>
                    </Flex>
                    <Text>
                      {format(product.quantity * product.price, {
                        code: "USD",
                      })}
                    </Text>
                  </ListItem>
                ))}
              </List>
              <Grid numCols={1} numColsSm={2} numColsLg={2} className="pt-4">
                <Button variant="light" onClick={handleClearCheckout}>
                  Clear checkout
                </Button>
                <Button
                  onClick={handlePlaceOrder}
                  disabled={isLoadingOrder || isErrorOrder}
                >
                  Place order
                </Button>
              </Grid>
            </>
          )}
        </div>
      )}
    </Card>
  );
}
