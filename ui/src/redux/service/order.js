import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const orderApi = createApi({
  reducerPath: "orderApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8082" }),
  endpoints: (builder) => ({
    createOrder: builder.mutation({
      query: (order) => ({
        url: `/orders`,
        method: "POST",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
        body: order,
      }),
    }),
  }),
});

export const { useCreateOrderMutation } = orderApi;
