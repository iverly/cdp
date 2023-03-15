import { configureStore } from "@reduxjs/toolkit";
import { catalogApi } from "./service/catalog";
import { shoppingCartApi } from "./service/shopping-cart";
import { orderApi } from "./service/order";

export const store = configureStore({
  reducer: {
    [catalogApi.reducerPath]: catalogApi.reducer,
    [shoppingCartApi.reducerPath]: shoppingCartApi.reducer,
    [orderApi.reducerPath]: orderApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(
      catalogApi.middleware,
      shoppingCartApi.middleware,
      orderApi.middleware
    ),
  devTools: process.env.NODE_ENV !== "production",
});
