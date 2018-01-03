package Uhh;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Client {
  public String name;
  public Boolean hasPaidAnual;
  public Boolean hasCollected;
  public Boolean hasCancelled;
  public Boolean undefinedCollective;
  public Collective collective;
  public Boolean undefinedBasket;
  public Basket basketReceived;
  public Object basketRequest;

  public void cg_init_Client_1(final String n, final Object type) {

    name = n;
    basketRequest = type;
    hasPaidAnual = false;
    hasCollected = false;
    hasCancelled = false;
    undefinedCollective = true;
    undefinedBasket = true;
  }

  public Client(final String n, final Object type) {

    cg_init_Client_1(n, type);
  }

  public Boolean applyCollective(final Collective c) {

    undefinedCollective = !(c.addAssociate(this));
    collective = c;
    return !(undefinedCollective);
  }

  public void getBasket() {

    Basket possibleBasket = null;
    possibleBasket = collective.collectBasket(this);
    if (!(undefinedBasket)) {
      basketReceived = possibleBasket;
    }
  }

  public Boolean quitCollective() {

    collective.removeAssociate(this);
    return undefinedCollective;
  }

  public void cancelBasket() {

    hasCancelled = true;
  }

  public void setHasCollected(final Boolean hc) {

    hasCollected = hc;
  }

  public void setUndefinedCollective(final Boolean uc) {

    undefinedCollective = uc;
  }

  public void setUndefinedBasket(final Boolean ub) {

    undefinedBasket = ub;
  }

  public void newWeek() {

    hasCollected = false;
    hasCancelled = false;
    undefinedBasket = true;
  }

  public void newYear() {

    hasCollected = false;
    hasCancelled = false;
    undefinedBasket = true;
    hasPaidAnual = false;
  }

  public Client() {}

  public String toString() {

    return "Client{"
        + "name := "
        + Utils.toString(name)
        + ", hasPaidAnual := "
        + Utils.toString(hasPaidAnual)
        + ", hasCollected := "
        + Utils.toString(hasCollected)
        + ", hasCancelled := "
        + Utils.toString(hasCancelled)
        + ", undefinedCollective := "
        + Utils.toString(undefinedCollective)
        + ", undefinedBasket := "
        + Utils.toString(undefinedBasket)
        + ", basketReceived := "
        + Utils.toString(basketReceived)
        + ", basketRequest := "
        + Utils.toString(basketRequest)
        + "}";
  }
}
