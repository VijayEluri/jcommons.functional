package org.jcommons.functional.function;

import java.util.List;

/**
 * A function that accepts as n arguments.
 *
 * @author Thorsten Goeckeler
 *
 * @param <R> template for the return class
 * @param <T> template for the object class
 */
public interface NaryFunction<R, T>
  extends Function
{
  /**
   * Executes the function with the given arguments.
   *
   * @param t the arguments
   * @return the result of the function depends on their implementation
   */
  R execute(T... t);

  /**
   * Executes the function with the given list of arguments.
   *
   * @param list the list of arguments
   * @return the result of the function depends on their implementation
   */
  R execute(List<T> list);
}
