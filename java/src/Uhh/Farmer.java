package Uhh;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Farmer {
  public String name;
  public VDMMap production;
  public Collective collective = new Collective("fake");
  public Boolean hasCollective = false;

  public void cg_init_Farmer_1(final String n, final VDMMap prods) {

    name = n;
    production = Utils.copy(prods);
    return;
  }

  public Farmer(final String n, final VDMMap prods) {

    cg_init_Farmer_1(n, Utils.copy(prods));
  }

  public void apply(final Collective cl) {

    hasCollective = true;
    collective = cl;
  }

  public void quit() {

    hasCollective = false;
    collective = new Collective("fake");
  }

  public void updateCollectiveInfo() {

    Collective cl = collective;
    cl.removeFarmer(this);
    cl.addFarmer(this);
  }

  public void addProduct(final Product prod, final Number gr) {

    production = MapUtil.override(Utils.copy(production), MapUtil.map(new Maplet(prod, gr)));
  }

  public void removeProduct(final Product prod) {

    production = MapUtil.domResBy(SetUtil.set(prod), Utils.copy(production));
  }

  public Farmer() {}

  public String toString() {

    return "Farmer{"
        + "name := "
        + Utils.toString(name)
        + ", production := "
        + Utils.toString(production)
        + ", collective := "
        + Utils.toString(collective)
        + ", hasCollective := "
        + Utils.toString(hasCollective)
        + "}";
  }
}
