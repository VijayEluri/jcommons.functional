package org.jcommons.functional.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * To String Test
 */
public class ToStringTest
{
  /**
   * Test method for 'org.jcommons.functional.functions.ToString.execute(T)'
   */
  @Test
  public void testExecute() {
    String[] strings = { "hello", "äöüÄÖÜß", "\u20AC" };
    AsString<String> simple = new AsString<String>();

    for (String text : strings) {
      assertEquals(simple.execute(text), text);
    }

    assertNull(simple.execute(null));
  }
}
