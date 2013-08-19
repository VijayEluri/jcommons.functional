package org.jcommons.functional.functions;

/**
 * Sums two long numbers.
 *
 * @author Thorsten Göckeler
 */
public class LongSum
  extends Sum<Long>
{
  /**
   * Sum the given numbers.
   *
   * @param numberLeft the 1st number
   * @param numberRight the 2nd number
   * @return the sum of the two numbers
   */
  @Override
  public Long sum(final Long numberLeft, final Long numberRight) {
    return numberLeft + numberRight;
  }
}
