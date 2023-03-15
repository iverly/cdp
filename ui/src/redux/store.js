import { configureStore } from "@reduxjs/toolkit";
import { catalogApi } from "./service/catalog";

export const store = configureStore({
  reducer: {
    [catalogApi.reducerPath]: catalogApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(catalogApi.middleware),
  devTools: process.env.NODE_ENV !== "production",
});
