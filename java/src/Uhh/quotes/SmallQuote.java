package Uhh.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class SmallQuote {
  private static int hc = 0;
  private static SmallQuote instance = null;

  public SmallQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static SmallQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new SmallQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof SmallQuote;
  }

  public String toString() {

    return "<Small>";
  }
}
