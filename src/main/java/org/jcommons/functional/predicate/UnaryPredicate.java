package org.jcommons.functional.predicate;

/**
 * A predicate that accepts a single argument.
 *
 * @author Thorsten Goeckeler
 *
 * @param <T> template for the object class
 */
public interface UnaryPredicate<T>
  extends Predicate
{
  /**
   * Executes the predicate with the given argument.
   *
   * @param argument the argument
   * @return true, if the predicate evaluates correctly; otherwise false
   */
  boolean execute(T argument);
}
