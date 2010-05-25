package org.jcommons.functional.functions;

import org.jcommons.functional.function.UnaryFunction;

/**
 * Transforms any item into its string representation.
 *
 * @author Thorsten Goeckeler
 *
 * @param <T> template for the object class
 */
public class AsString<T>
  implements UnaryFunction<String, T>
{
  /**
   * Transforms the item into its string representation in a <code>null</code> safe manner.
   *
   * @param argument the item
   * @return a string representation of the given item or <code>null</code> if the item is null
   */
  public String execute(final T argument) {
    if (argument != null) return argument.toString();
    return null;
  }
}
