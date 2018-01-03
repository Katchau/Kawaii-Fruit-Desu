package Uhh;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Product {
  public String name;

  public void cg_init_Product_1(final String n) {

    name = n;
    return;
  }

  public Product(final String n) {

    cg_init_Product_1(n);
  }

  public Boolean is_equal(final Product prod) {

    return cg_Utils.stringsEquals(name, prod.name);
  }

  public Product() {}

  public String toString() {

    return "Product{" + "name := " + Utils.toString(name) + "}";
  }
}
