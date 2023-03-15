import React, { Fragment } from "react";
import { useGetProductsQuery } from "../redux/service/catalog";
import { Grid } from "@tremor/react";
import ProductCard from "./ProductCard";
import { nanoid } from "@reduxjs/toolkit";

export default function ProductList() {
  const { data, isLoading, error, isError } = useGetProductsQuery();

  if (isLoading) return <div>Loading...</div>;

  if (isError) {
    return (
      <div role="alert">
        <div class="bg-red-500 text-white font-bold rounded-t px-4 py-2">
          Danger
        </div>
        <div class="border border-t-0 border-red-400 rounded-b bg-red-100 px-4 py-3 text-red-700">
          <p>
            There was an error while fetching our data. Please retry in a few
            seconds. {error.message ? `(${error.message})` : null}
          </p>
        </div>
      </div>
    );
  }

  return (
    <Grid numCols={1} numColsSm={2} numColsLg={3} className="gap-2">
      {data.map((product) => (
        <Fragment key={`${product.id}-${nanoid()}`}>
          <ProductCard product={product} />
        </Fragment>
      ))}
    </Grid>
  );
}
