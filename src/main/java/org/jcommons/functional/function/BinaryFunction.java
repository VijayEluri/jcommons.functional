package org.jcommons.functional.function;

/**
 * A function that accepts two arguments.
 *
 * @author Thorsten Goeckeler
 *
 * @param <R> template for the return class
 * @param <T> template for the object class
 */
public interface BinaryFunction<R, T>
  extends Function
{
  /**
   * Executes the function with the two given arguments.
   *
   * @param argument0 the first argument
   * @param argument1 the second argument
   * @return the result of the function depends on their implementation
   */
  R execute(T argument0, T argument1);
}
