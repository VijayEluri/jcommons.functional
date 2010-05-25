package org.jcommons.functional.functions;

/**
 * Sums two double numbers.
 *
 * @author Thorsten Göckeler
 */
public class DoubleSum
  extends Sum<Double>
{
  /**
   * Sum the given numbers.
   *
   * @param numberLeft the 1st number
   * @param numberRight the 2nd number
   * @return the sum of the two numbers
   */
  @Override
  public Double sum(final Double numberLeft, final Double numberRight) {
    return numberLeft + numberRight;
  }
}
