import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const shoppingCartApi = createApi({
  reducerPath: "shoppingCartApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8081" }),
  tagTypes: ["ShoppingCart"],
  endpoints: (builder) => ({
    getShoppingCart: builder.query({
      query: () => `/shopping-carts`,
      providesTags: ["ShoppingCart"],
    }),
    updateShoppingCart: builder.mutation({
      query: (shoppingCart) => ({
        url: `/shopping-carts`,
        method: "POST",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
        body: {
          products: shoppingCart.products,
        },
      }),
      invalidatesTags: ["ShoppingCart"],
    }),
  }),
});

export const { useGetShoppingCartQuery, useUpdateShoppingCartMutation } =
  shoppingCartApi;
