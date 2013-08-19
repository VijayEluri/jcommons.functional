package org.jcommons.functional.functions;

import org.jcommons.functional.function.BinaryFunction;

/**
 * Sums two numbers.
 *
 * @author Thorsten Göckeler
 *
 * @param <T> template for the object class
 */
public abstract class Sum<T>
  implements BinaryFunction<T, T>
{
  /**
   * Sums two numbers.
   *
   * @param numberLeft the 1st number
   * @param numberRight the 2nd number
   * @return the sum of the two numbers
   */
  public abstract T sum(T numberLeft, T numberRight);

  /**
   * Sums two numbers to fulfill interface contract.
   *
   * @param numberLeft the 1st number
   * @param numberRight the 2nd number
   * @return the sum of the two numbers
   */
  public T execute(final T numberLeft, final T numberRight) {
    return sum(numberLeft, numberRight);
  }
}
