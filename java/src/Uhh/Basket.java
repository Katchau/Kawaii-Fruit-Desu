package Uhh;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Basket {
  public Number totalWeight;
  public VDMMap content;
  public Object size;

  public void cg_init_Basket_1(final VDMMap prods, final Object s) {

    content = Utils.copy(prods);
    totalWeight = getTotalWeight(Utils.copy(prods));
    size = s;
    return;
  }

  public Basket(final VDMMap prods, final Object s) {

    cg_init_Basket_1(Utils.copy(prods), s);
  }

  public void add(final Product prod, final Number gr) {

    content = MapUtil.override(Utils.copy(content), MapUtil.map(new Maplet(prod, gr)));
    totalWeight = getTotalWeight(Utils.copy(content));
  }

  public void updateProductQuantity(final Product prod, final Number gr) {

    content =
        MapUtil.override(
            Utils.copy(content),
            MapUtil.map(
                new Maplet(
                    prod, ((Number) Utils.get(content, prod)).doubleValue() + gr.doubleValue())));
    totalWeight = getTotalWeight(Utils.copy(content));
  }

  public void remove(final Product prod) {

    content = MapUtil.domResBy(SetUtil.set(prod), Utils.copy(content));
    totalWeight = getTotalWeight(Utils.copy(content));
  }

  public Number getQuantity(final Product prod) {

    return ((Number) Utils.get(content, prod));
  }

  public Basket() {}

  public static Number getTotalWeight(final VDMMap prods) {

    if (Utils.empty(prods)) {
      return 0L;

    } else {
      Number letBeStExp_1 = null;
      Product p = null;
      Boolean success_1 = false;
      VDMSet set_1 = MapUtil.dom(Utils.copy(prods));
      for (Iterator iterator_1 = set_1.iterator(); iterator_1.hasNext() && !(success_1); ) {
        p = ((Product) iterator_1.next());
        success_1 = true;
      }
      if (!(success_1)) {
        throw new RuntimeException("Let Be St found no applicable bindings");
      }

      letBeStExp_1 =
          ((Number) Utils.get(prods, p)).doubleValue()
              + getTotalWeight(MapUtil.domResBy(SetUtil.set(p), Utils.copy(prods))).doubleValue();
      return letBeStExp_1;
    }
  }

  public String toString() {

    return "Basket{"
        + "totalWeight := "
        + Utils.toString(totalWeight)
        + ", content := "
        + Utils.toString(content)
        + ", size := "
        + Utils.toString(size)
        + "}";
  }
}
