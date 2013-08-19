package org.jcommons.functional.predicate;

import java.util.List;

/**
 * A predicate that accepts any amount of parameters.
 *
 * @author Thorsten Goeckeler
 *
 * @param <T> template for the object class
 */
public interface NaryPredicate<T>
  extends Predicate
{
  /**
   * Executes the predicate with the given arguments.
   *
   * @param arguments the arguments
   * @return true, if the predicate evaluates correctly; otherwise false
   */
  boolean execute(@SuppressWarnings("unchecked") T... arguments);

  /**
   * Executes the predicate with the given list of arguments.
   *
   * @param arguments the list of arguments
   * @return true, if the predicate evaluates correctly; otherwise false
   */
  boolean execute(List<T> arguments);
}
