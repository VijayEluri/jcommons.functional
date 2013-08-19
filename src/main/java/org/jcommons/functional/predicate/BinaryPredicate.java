package org.jcommons.functional.predicate;

/**
 * A predicate that accepts two arguments.
 *
 * @author Thorsten Goeckeler
 *
 * @param <T> template for the object class
 */
public interface BinaryPredicate<T>
  extends Predicate
{
  /**
   * Executes the predicate with the two given arguments.
   *
   * @param argument0 the first argument
   * @param argument1 the second argument
   * @return true, if the predicate evaluates correctly; otherwise false
   */
  boolean execute(T argument0, T argument1);
}
