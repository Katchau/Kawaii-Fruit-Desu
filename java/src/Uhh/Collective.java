package Uhh;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Collective {
  private static final Number basketSmall = 7L;
  private static final Number basketBig = 8L;
  private static final Number minSmall =
      Utils.divide(((Number) cg_Utils.smallWeight).doubleValue(), Collective.basketSmall.longValue());
  private static final Number minBig =
      Utils.divide(((Number) cg_Utils.bigWeight).doubleValue(), Collective.basketBig.longValue());
  public VDMSet clients = SetUtil.set();
  public VDMSet farmers = SetUtil.set();
  public Stack waiting = new Stack();
  public VDMSeq baskets = SeqUtil.seq();
  public String location;
  public Number smallBaskets = 0L;
  public Number bigBaskets = 0L;
  public VDMMap prodAvailability;

  public void cg_init_Collective_1(final String loc) {

    location = loc;
    prodAvailability = MapUtil.map();
    return;
  }

  public Collective(final String loc) {

    cg_init_Collective_1(loc);
  }

  public Client getClient(final String cname) {

    Client cli = null;
    for (Iterator iterator_18 = clients.iterator(); iterator_18.hasNext(); ) {
      Client c = (Client) iterator_18.next();
      if (cg_Utils.stringsEquals(c.name, cname)) {
        cli = c;
      }
    }
    return cli;
  }

  public Basket generateBasket(final Object basketType, final VDMMap curProds) {

    Number n = 1L;
    Number minSize = 0.0;
    Basket ret = new Basket(MapUtil.map(), ((Object) basketType));
    Boolean stopCycle = false;
    if (Utils.equals(basketType, Uhh.quotes.SmallQuote.getInstance())) {
      n = Collective.basketSmall;
      minSize = Collective.minSmall;

    } else {
      n = Collective.basketBig;
      minSize = Collective.minBig;
    }

    for (Iterator iterator_19 = MapUtil.dom(Utils.copy(curProds)).iterator();
        iterator_19.hasNext();
        ) {
      Product prod = (Product) iterator_19.next();
      Boolean andResult_16 = false;

      if (((Number) Utils.get(curProds, prod)).doubleValue() >= minSize.doubleValue()) {
        Boolean andResult_17 = false;

        if (!(SetUtil.inSet(prod, MapUtil.dom(ret.content)))) {
          if (MapUtil.dom(ret.content).size() < n.longValue()) {
            andResult_17 = true;
          }
        }

        if (andResult_17) {
          andResult_16 = true;
        }
      }

      if (andResult_16) {
        ret.add(prod, minSize);
      } else {
        Boolean andResult_18 = false;

        if (((Number) Utils.get(curProds, prod)).doubleValue() < minSize.doubleValue()) {
          Boolean andResult_19 = false;

          if (!(SetUtil.inSet(prod, MapUtil.dom(ret.content)))) {
            if (MapUtil.dom(ret.content).size() < n.longValue()) {
              andResult_19 = true;
            }
          }

          if (andResult_19) {
            andResult_18 = true;
          }
        }

        if (andResult_18) {
          stopCycle = false;
          for (Iterator iterator_20 = MapUtil.dom(Utils.copy(curProds)).iterator();
              iterator_20.hasNext();
              ) {
            Product p = (Product) iterator_20.next();
            Boolean orResult_1 = false;

            if (((Number) Utils.get(curProds, p)).doubleValue() - minSize.doubleValue()
                < minSize.doubleValue() - ((Number) Utils.get(curProds, prod)).doubleValue()) {
              orResult_1 = true;
            } else {
              orResult_1 = stopCycle;
            }

            if (orResult_1) {
              /* skip */
            } else {
              Boolean andResult_20 = false;

              if (SetUtil.inSet(p, MapUtil.dom(ret.content))) {
                if (((Number) Utils.get(curProds, p)).doubleValue()
                        - ret.getQuantity(p).doubleValue()
                    >= minSize.doubleValue() - ((Number) Utils.get(curProds, prod)).doubleValue()) {
                  andResult_20 = true;
                }
              }

              if (andResult_20) {
                ret.updateProductQuantity(
                    p, minSize.doubleValue() - ((Number) Utils.get(curProds, prod)).doubleValue());
                ret.add(prod, ((Number) Utils.get(curProds, prod)));
                stopCycle = true;

              } else {
                if (!(SetUtil.inSet(p, MapUtil.dom(ret.content)))) {
                  ret.add(
                      p,
                      minSize.doubleValue()
                          + minSize.doubleValue()
                          - ((Number) Utils.get(curProds, prod)).doubleValue());
                  ret.add(prod, ((Number) Utils.get(curProds, prod)));
                  stopCycle = true;
                }
              }
            }
          }
        }
      }
    }
    return ret;
  }

  public void createBaskets() {

    VDMMap curProds = Utils.copy(prodAvailability);
    Basket basket = null;
    baskets = SeqUtil.seq();
    for (Iterator iterator_21 = clients.iterator(); iterator_21.hasNext(); ) {
      Client cli = (Client) iterator_21.next();
      basket = generateBasket(((Object) cli.basketRequest), Utils.copy(curProds));
      baskets = SeqUtil.conc(Utils.copy(baskets), SeqUtil.seq(basket));
      curProds = subtractMaps(Utils.copy(curProds), basket.content);
    }
  }

  public Boolean canAddAssociate(final Number small, final Number big) {

    VDMMap curProds = Utils.copy(prodAvailability);
    Number i = 1L;
    Number validBaskets = 0L;
    Basket basket = null;
    Boolean whileCond_1 = true;
    while (whileCond_1) {
      whileCond_1 = i.longValue() <= small.longValue();
      if (!(whileCond_1)) {
        break;
      }

      {
        i = i.longValue() + 1L;
        if (Basket.getTotalWeight(Utils.copy(curProds)).doubleValue() + 0.1
            >= cg_Utils.smallWeight.longValue()) {
          basket = generateBasket(Uhh.quotes.SmallQuote.getInstance(), Utils.copy(curProds));
          Boolean andResult_22 = false;

          if (Utils.equals(MapUtil.dom(basket.content).size(), Collective.basketSmall)) {
            Boolean andResult_23 = false;

            if (basket.totalWeight.doubleValue() + 0.1 >= cg_Utils.smallWeight.longValue()) {
              if (basket.totalWeight.doubleValue() <= cg_Utils.smallWeight.longValue() + 0.1) {
                andResult_23 = true;
              }
            }

            if (andResult_23) {
              andResult_22 = true;
            }
          }

          if (andResult_22) {
            validBaskets = validBaskets.longValue() + 1L;
            curProds = subtractMaps(Utils.copy(curProds), basket.content);
          }
        }
      }
    }

    i = 1L;
    Boolean whileCond_2 = true;
    while (whileCond_2) {
      whileCond_2 = i.longValue() <= big.longValue();
      if (!(whileCond_2)) {
        break;
      }

      {
        i = i.longValue() + 1L;
        if (Basket.getTotalWeight(Utils.copy(curProds)).doubleValue() + 0.1
            >= cg_Utils.bigWeight.longValue()) {
          basket = generateBasket(Uhh.quotes.BigQuote.getInstance(), Utils.copy(curProds));
          Boolean andResult_24 = false;

          if (Utils.equals(MapUtil.dom(basket.content).size(), Collective.basketBig)) {
            Boolean andResult_25 = false;

            if (basket.totalWeight.doubleValue() + 0.1 >= cg_Utils.bigWeight.longValue()) {
              if (basket.totalWeight.doubleValue() <= cg_Utils.bigWeight.longValue() + 0.1) {
                andResult_25 = true;
              }
            }

            if (andResult_25) {
              andResult_24 = true;
            }
          }

          if (andResult_24) {
            validBaskets = validBaskets.longValue() + 1L;
            curProds = subtractMaps(Utils.copy(curProds), basket.content);
          }
        }
      }
    }

    return Utils.equals(validBaskets, small.longValue() + big.longValue());
  }

  public Boolean addAssociate(final Client cli) {

    Boolean andResult_30 = false;

    if (Utils.equals(cli.basketRequest, Uhh.quotes.SmallQuote.getInstance())) {
      if (canAddAssociate(smallBaskets.longValue() + 1L, bigBaskets)) {
        andResult_30 = true;
      }
    }

    if (andResult_30) {
      VDMSet atomicTmp_1 = SetUtil.union(Utils.copy(clients), SetUtil.set(cli));
      Number atomicTmp_2 = smallBaskets.longValue() + 1L;
      {
          /* Start of atomic statement */
        clients = Utils.copy(atomicTmp_1);
        smallBaskets = atomicTmp_2;
      } /* End of atomic statement */

      cli.setUndefinedCollective(false);
      return true;
    }

    Boolean andResult_31 = false;

    if (Utils.equals(cli.basketRequest, Uhh.quotes.BigQuote.getInstance())) {
      if (canAddAssociate(smallBaskets, bigBaskets.longValue() + 1L)) {
        andResult_31 = true;
      }
    }

    if (andResult_31) {
      VDMSet atomicTmp_3 = SetUtil.union(Utils.copy(clients), SetUtil.set(cli));
      Number atomicTmp_4 = bigBaskets.longValue() + 1L;
      {
          /* Start of atomic statement */
        clients = Utils.copy(atomicTmp_3);
        bigBaskets = atomicTmp_4;
      } /* End of atomic statement */

      cli.setUndefinedCollective(false);
      return true;
    }

    waiting.push(cli);
    return false;
  }

  public void removeAssociate(final Client cli) {

    if (Utils.equals(cli.basketRequest, Uhh.quotes.SmallQuote.getInstance())) {
      VDMSet atomicTmp_5 = SetUtil.diff(Utils.copy(clients), SetUtil.set(cli));
      Number atomicTmp_6 = smallBaskets.longValue() - 1L;
      {
          /* Start of atomic statement */
        clients = Utils.copy(atomicTmp_5);
        smallBaskets = atomicTmp_6;
      } /* End of atomic statement */

    } else {
      VDMSet atomicTmp_7 = SetUtil.diff(Utils.copy(clients), SetUtil.set(cli));
      Number atomicTmp_8 = bigBaskets.longValue() - 1L;
      {
          /* Start of atomic statement */
        clients = Utils.copy(atomicTmp_7);
        bigBaskets = atomicTmp_8;
      } /* End of atomic statement */
    }

    cli.setUndefinedCollective(true);
  }

  public Boolean addFromWaitingList() {

    Client nextCli = ((Client) waiting.top());
    Boolean hasFreeSpace = false;
    if (Utils.equals(nextCli.basketRequest, Uhh.quotes.SmallQuote.getInstance())) {
      hasFreeSpace = canAddAssociate(smallBaskets.longValue() + 1L, bigBaskets);
    } else {
      hasFreeSpace = canAddAssociate(smallBaskets, bigBaskets.longValue() + 1L);
    }

    if (hasFreeSpace) {
      waiting.pop();
      return addAssociate(nextCli);

    } else {
      return false;
    }
  }

  public void kickClients() {

    Number size = 1L;
    for (Iterator iterator_22 = clients.iterator(); iterator_22.hasNext(); ) {
      Client cli = (Client) iterator_22.next();
      if (Utils.equals(cli.basketRequest, Uhh.quotes.SmallQuote.getInstance())) {
        size = Collective.basketSmall;
      } else {
        size = Collective.basketBig;
      }

      if (!(canAddAssociate(smallBaskets, bigBaskets))) {
        removeAssociate(cli);
        waiting.push(cli);
      }
    }
  }

  public Farmer getFarmer(final String fname) {

    Farmer farmer = null;
    for (Iterator iterator_23 = farmers.iterator(); iterator_23.hasNext(); ) {
      Farmer f = (Farmer) iterator_23.next();
      if (cg_Utils.stringsEquals(f.name, fname)) {
        farmer = f;
      }
    }
    return farmer;
  }

  public void addFarmer(final Farmer farmer) {

    farmers = SetUtil.union(Utils.copy(farmers), SetUtil.set(farmer));
    for (Iterator iterator_24 = MapUtil.dom(farmer.production).iterator();
        iterator_24.hasNext();
        ) {
      Product prod = (Product) iterator_24.next();
      if (!(SetUtil.inSet(prod, MapUtil.dom(Utils.copy(prodAvailability))))) {
        prodAvailability =
            MapUtil.override(
                Utils.copy(prodAvailability),
                MapUtil.map(new Maplet(prod, ((Number) Utils.get(farmer.production, prod)))));
      } else {
        prodAvailability =
            MapUtil.override(
                Utils.copy(prodAvailability),
                MapUtil.map(
                    new Maplet(
                        prod,
                        ((Number) Utils.get(farmer.production, prod)).doubleValue()
                            + ((Number) Utils.get(prodAvailability, prod)).doubleValue())));
      }
    }
    farmer.apply(this);
  }

  public void removeFarmer(final Farmer farmer) {

    farmers = SetUtil.diff(Utils.copy(farmers), SetUtil.set(farmer));
    for (Iterator iterator_25 = MapUtil.dom(farmer.production).iterator();
        iterator_25.hasNext();
        ) {
      Product prod = (Product) iterator_25.next();
      if (Utils.equals(
          ((Number) Utils.get(prodAvailability, prod)),
          ((Number) Utils.get(farmer.production, prod)))) {
        prodAvailability = MapUtil.domResBy(SetUtil.set(prod), Utils.copy(prodAvailability));
      } else {
        prodAvailability =
            MapUtil.override(
                Utils.copy(prodAvailability),
                MapUtil.map(
                    new Maplet(
                        prod,
                        ((Number) Utils.get(prodAvailability, prod)).doubleValue()
                            - ((Number) Utils.get(farmer.production, prod)).doubleValue())));
      }
    }
    farmer.quit();
  }

  public Basket collectBasket(final Client cl) {

    VDMSet basketSet = SeqUtil.elems(Utils.copy(baskets));
    Product p1 = new Product("apple");
    Product p2 = new Product("banana");
    if (cl.hasCancelled) {
      cl.setUndefinedBasket(true);
      return new Basket(
          MapUtil.map(new Maplet(p1, 100L), new Maplet(p2, 300L)),
          Uhh.quotes.BigQuote.getInstance());
    }

    if (cl.hasCollected) {
      cl.setUndefinedBasket(false);
      return new Basket(
          MapUtil.map(new Maplet(p1, 100L), new Maplet(p2, 300L)),
          Uhh.quotes.BigQuote.getInstance());
    }

    if (Utils.equals(cl.basketRequest, Uhh.quotes.BigQuote.getInstance())) {
      for (Iterator iterator_26 = basketSet.iterator(); iterator_26.hasNext(); ) {
        Basket b = (Basket) iterator_26.next();
        if (Utils.equals(b.size, Uhh.quotes.BigQuote.getInstance())) {
          basketSet = SetUtil.diff(Utils.copy(basketSet), SetUtil.set(b));
          cl.setHasCollected(true);
          cl.setUndefinedBasket(false);
          return b;
        }
      }
    } else {
      for (Iterator iterator_27 = basketSet.iterator(); iterator_27.hasNext(); ) {
        Basket b = (Basket) iterator_27.next();
        if (Utils.equals(b.size, Uhh.quotes.SmallQuote.getInstance())) {
          basketSet = SetUtil.diff(Utils.copy(basketSet), SetUtil.set(b));
          cl.setHasCollected(true);
          cl.setUndefinedBasket(false);
          return b;
        }
      }
    }

    cl.setUndefinedBasket(true);
    return new Basket(
        MapUtil.map(new Maplet(p1, 100L), new Maplet(p2, 300L)), Uhh.quotes.BigQuote.getInstance());
  }

  public void newYear() {

    for (Iterator iterator_28 = clients.iterator(); iterator_28.hasNext(); ) {
      Client cli = (Client) iterator_28.next();
      cli.newYear();
    }
  }

  public void newWeek() {

    for (Iterator iterator_29 = clients.iterator(); iterator_29.hasNext(); ) {
      Client cli = (Client) iterator_29.next();
      cli.newWeek();
    }
  }

  public Collective() {}

  public static VDMMap subtractMaps(final VDMMap p1, final VDMMap p2) {

    Boolean orResult_3 = false;

    if (Utils.empty(p1)) {
      orResult_3 = true;
    } else {
      orResult_3 =
          Utils.empty(SetUtil.intersect(MapUtil.dom(Utils.copy(p1)), MapUtil.dom(Utils.copy(p2))));
    }

    if (orResult_3) {
      return MapUtil.map();

    } else {
      VDMMap letBeStExp_2 = null;
      Product v = null;
      Boolean success_2 = false;
      VDMSet set_10 = SetUtil.intersect(MapUtil.dom(Utils.copy(p1)), MapUtil.dom(Utils.copy(p2)));
      for (Iterator iterator_10 = set_10.iterator(); iterator_10.hasNext() && !(success_2); ) {
        v = ((Product) iterator_10.next());
        success_2 = true;
      }
      if (!(success_2)) {
        throw new RuntimeException("Let Be St found no applicable bindings");
      }

      letBeStExp_2 =
          MapUtil.override(
              MapUtil.override(
                  Utils.copy(p1),
                  MapUtil.map(
                      new Maplet(
                          v,
                          ((Number) Utils.get(p1, v)).doubleValue()
                              - ((Number) Utils.get(p2, v)).doubleValue()))),
              subtractMaps(
                  MapUtil.domResBy(SetUtil.set(v), Utils.copy(p1)),
                  MapUtil.domResBy(SetUtil.set(v), Utils.copy(p2))));
      return Utils.copy(letBeStExp_2);
    }
  }

  public String toString() {

    return "Collective{"
        + ", clients := "
        + Utils.toString(clients)
        + ", farmers := "
        + Utils.toString(farmers)
        + ", waiting := "
        + Utils.toString(waiting)
        + ", baskets := "
        + Utils.toString(baskets)
        + ", location := "
        + Utils.toString(location)
        + ", smallBaskets := "
        + Utils.toString(smallBaskets)
        + ", bigBaskets := "
        + Utils.toString(bigBaskets)
        + ", prodAvailability := "
        + Utils.toString(prodAvailability)
        + "}";
  }
}
