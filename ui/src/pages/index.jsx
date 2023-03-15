import Header from "../components/Header";
import ProductList from "../components/ProductList";
import { Grid, Col, Card } from "@tremor/react";
import Checkout from "../components/Checkout";

export default function HomePage() {
  return (
    <>
      <Header />
      <div className="container mx-auto py-4">
        <Grid numCols={1} numColsSm={3} numColsLg={4} className="gap-2">
          <Col numColSpan={1} numColSpanLg={3}>
            <ProductList />
          </Col>
          <Checkout />
        </Grid>
      </div>
    </>
  );
}
