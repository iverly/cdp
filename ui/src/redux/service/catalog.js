import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const catalogApi = createApi({
  reducerPath: "catalogApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8080" }),
  endpoints: (builder) => ({
    getCatalog: builder.query({
      query: () => `/catalog`,
    }),
  }),
});

export const { useGetCatalog } = catalogApi;
