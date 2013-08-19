package org.jcommons.functional.functions;

/**
 * Sums two integer numbers
 *
 * @author Thorsten Göckeler
 */
public class IntegerSum
  extends Sum<Integer>
{
  /**
   * Sum the given numbers.
   *
   * @param numberLeft the 1st number
   * @param numberRight the 2nd number
   * @return the sum of the two numbers
   */
  @Override
  public Integer sum(final Integer numberLeft, final Integer numberRight) {
    return numberLeft + numberRight;
  }
}
