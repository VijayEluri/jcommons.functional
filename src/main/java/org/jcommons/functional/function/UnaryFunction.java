package org.jcommons.functional.function;

/**
 * A function that accepts a single argument.
 *
 * @author Thorsten Goeckeler
 *
 * @param <R> template for the return class
 * @param <T> template for the object class
 */
public interface UnaryFunction<R, T>
  extends Function
{
  /**
   * Executes the function with the given argument.
   *
   * @param argument the argument
   * @return the result of the function depends on their implementation
   */
  R execute(T argument);
}
