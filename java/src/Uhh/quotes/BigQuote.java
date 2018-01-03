package Uhh.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class BigQuote {
  private static int hc = 0;
  private static BigQuote instance = null;

  public BigQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static BigQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new BigQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof BigQuote;
  }

  public String toString() {

    return "<Big>";
  }
}
