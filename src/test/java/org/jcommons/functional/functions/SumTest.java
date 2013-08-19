package org.jcommons.functional.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Sum Test
 */
public class SumTest
{
  /**
   * Test method for 'org.jcommons.functional.functions.Sum.execute(T, T)'
   */
  @Test
  public void testExecute() {
    IntegerSum sum = new IntegerSum();
    assertEquals(sum.execute(0, 1), new Integer(1));
    assertEquals(sum.execute(1, 2), new Integer(3));
    assertEquals(sum.execute(2, 1), new Integer(3));
    assertEquals(sum.execute(0, -11), new Integer(-11));
    assertEquals(sum.execute(sum.execute(4, 3), 1), new Integer(8));
  }

  /** check Long implementation as well */
  @Test
  public void testExecuteLong() {
    LongSum sum = new LongSum();
    assertEquals(sum.execute(0L, 1L), Long.valueOf(1L));
    assertEquals(sum.execute(1L, 2L), Long.valueOf(3L));
    assertEquals(sum.execute(2L, 1L), Long.valueOf(3L));
    assertEquals(sum.execute(0L, -11L), Long.valueOf(-11L));
    assertEquals(sum.execute(sum.execute(4L, 3L), 1L), Long.valueOf(8L));
  }


  /** check Double implementation as well */
  @Test
  public void testExecuteDouble() {
    DoubleSum sum = new DoubleSum();
    assertEquals(sum.execute(0.0, 1.0), Double.valueOf(1L));
    assertEquals(sum.execute(1.3, 1.7), Double.valueOf(3L));
    assertEquals(sum.execute(1.75, 1.25), Double.valueOf(3L));
    assertEquals(sum.execute(0.0, -11.0), Double.valueOf(-11L));
    assertEquals(sum.execute(sum.execute(3.5, 3.5), 1.0), Double.valueOf(8L));
  }
}
