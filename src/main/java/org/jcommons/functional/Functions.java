package org.jcommons.functional;

import java.util.*;

import org.jcommons.functional.function.BinaryFunction;
import org.jcommons.functional.function.UnaryFunction;
import org.jcommons.functional.predicate.UnaryPredicate;

/**
 * Functional programming common functions in a default implementation.
 *
 * This is a side effect free implementation of the common functions, however, not the most effective for all cases
 * especially for large lists.
 *
 * Why would you want to use a different implementation? The default implementation avoids side effects by copying the
 * transformed elements. However, if you only need to execute a function once or twice over a large list, you might
 * prefer to execute the function "on-the-fly". Consider the following example for the map method:
 *
 * <pre>
 * public static&lt;R, T&gt; List&lt;R&gt; map(final UnaryFunction&lt;R,T&gt; function, final List&lt;T&gt; list) {
 *   return new AbstractList&lt;R&gt;() {
 *     public R get(int index) {
 *       return function.execute(list.get(index));
 *     }
 *
 *     public int size() {
 *       return list.size();
 *     }
 *   }
 * }
 * </pre>
 *
 * Just keep in mind that the list returned in this code sample is only a view on the original list, not a list for
 * itself. So you cannot modify any values, neither you can add or remove elements.
 *
 * The implementation used in here will be free of these side effects, but creating a deep copy will have a certain
 * effect when using large lists.
 */
public final class Functions
{
  /** hide default constructor */
  private Functions() {
  }

  /**
   * Executes the given function on each item of the given list.
   *
   * @param function the function to apply
   * @param list the list of items on which to apply the function
   * @param <R> template for the return class
   * @param <T> template for the object class
   */
  public static <R, T> void forEach(final UnaryFunction<R, T> function, final List<T> list) {
    if (list == null || function == null) return;

    for (T item : list) {
      function.execute(item);
    }
  }

  /**
   * Executes the given function on each item of the given argument list.
   *
   * @param function the function to apply
   * @param list the list of items on which to apply the function
   * @param <R> template for the return class
   * @param <T> template for the object class
   */
  public static <R, T> void forEach(final UnaryFunction<R, T> function, final T... list) {
    if (list == null || function == null) return;
    forEach(function, Arrays.asList(list));
  }

  /**
   * Applies the given function on each item of the given list.
   *
   * @param function the function to apply
   * @param list the list of items on which to apply the function
   * @param <R> template for the return class
   * @param <T> template for the object class
   * @return a corresponding list containing the respective results, can be null if the list is null. Will be empty if
   *         no function is defined.
   */
  public static <R, T> List<R> map(final UnaryFunction<R, T> function, final List<T> list) {
    if (list == null) return null;

    List<R> result = new ArrayList<R>(list.size());
    for (T item : list) {
      if (function != null) {
        result.add(function.execute(item));
      }
    }

    return result;
  }

  /**
   * Applies the given function on each item of the given argument list.
   *
   * @param function the function to apply
   * @param list the list of items on which to apply the function
   * @param <R> template for the return class
   * @param <T> template for the object class
   * @return a corresponding list containing the respective results, can be null if the list is null. Will be empty if
   *         no function is defined.
   */
  public static <R, T> List<R> map(final UnaryFunction<R, T> function, final T... list) {
    if (list == null) return null;
    return map(function, Arrays.asList(list));
  }

  /**
   * Determines if at least one element in the list fits the predicate.
   *
   * @param predicate the predicate to apply
   * @param list the list of elements to apply the predicate to
   * @param <T> template for the object class
   * @return true if at least one element meets the predicate, otherwise false
   */
  public static <T> boolean some(final UnaryPredicate<T> predicate, final List<T> list) {
    if (list == null || predicate == null) return false;

    boolean matches = false;
    for (T item : list) {
      matches = predicate.execute(item);
      if (matches) {
        break;
      }
    }
    return matches;
  }

  /**
   * Determines if at least one element in the argument list fits the predicate.
   *
   * @param predicate the predicate to apply
   * @param list the list of elements to apply the predicate to
   * @param <T> template for the object class
   * @return true if at least one element meets the predicate, otherwise false
   */
  public static <T> boolean some(final UnaryPredicate<T> predicate, final T... list) {
    if (list == null || predicate == null) return false;
    return some(predicate, Arrays.asList(list));
  }

  /**
   * Determine if every element in the list fits the predicate.
   *
   * @param predicate the predicate to apply
   * @param list the list of elements to apply the predicate to
   * @param <T> template for the object class
   * @return true if all elements meets the predicate, otherwise false
   */
  public static <T> boolean every(final UnaryPredicate<T> predicate, final List<T> list) {
    if (list == null || predicate == null) return false;

    boolean matches = true;
    for (T item : list) {
      matches = predicate.execute(item);
      if (!matches) {
        break;
      }
    }
    return matches;
  }

  /**
   * Determine if every element in the argument list fits the predicate.
   *
   * @param predicate the predicate to apply
   * @param list the list of elements to apply the predicate to
   * @param <T> template for the object class
   * @return true if all elements meets the predicate, otherwise false
   */
  public static <T> boolean every(final UnaryPredicate<T> predicate, final T... list) {
    if (list == null || predicate == null) return false;
    return every(predicate, Arrays.asList(list));
  }

  /**
   * Retrieve only those elements that meet the given predicate.
   *
   * @param predicate the predicate to apply
   * @param list the list of elements to apply the predicate to
   * @param <T> template for the object class
   * @return the list of elements that meet the predicate. If no function is defined, all items will be returned. If the
   *         list is null, null will be returned.
   */
  public static <T> List<T> filter(final UnaryPredicate<T> predicate, final List<T> list) {
    if (list == null) return null;

    // may be oversized, but at least it will be fast
    List<T> result = new ArrayList<T>(list.size());

    for (T item : list) {
      if (predicate != null) {
        if (predicate.execute(item)) {
          result.add(item);
        }
      } else {
        // every item is matched if no predicate is given
        result.add(item);
      }
    }
    return result;
  }

  /**
   * Retrieve only those elements that meet the given predicate.
   *
   * @param predicate the predicate to apply
   * @param list the list of elements to apply the predicate to
   * @param <T> template for the object class
   * @return the list of elements that meet the predicate. If no function is defined, all items will be returned. If the
   *         list is null, null will be returned.
   */
  public static <T> List<T> filter(final UnaryPredicate<T> predicate, final T... list) {
    if (list == null) return null;
    return filter(predicate, Arrays.asList(list));
  }

  /**
   * Eliminates all elements from the list until a single element is left over.
   *
   * @param function the function to reduce two values to one
   * @param list the list of elements to resolve, e.g. sum up
   * @param <T> template for the object class
   * @return the single element that is left after reduction of all other ones. Will be null if the list is null or no
   *         function is given.
   */
  public static <T> T resolve(final BinaryFunction<T, T> function, final List<T> list) {
    return resolve(null, function, list);
  }

  /**
   * Eliminates all elements from the list until a single element is left over.
   *
   * @param function the function to reduce two values to one
   * @param list the list of elements to resolve, e.g. sum up
   * @param <T> template for the object class
   * @return the single element that is left after reduction of all other ones. Will be null if the list is null or no
   *         function is given.
   */
  public static <T> T resolve(final BinaryFunction<T, T> function, final T... list) {
    if (list == null || function == null) return null;
    return resolve(function, Arrays.asList(list));
  }

  /**
   * Eliminates all elements from the list until a single element is left over.
   *
   * @param initial an initial value to start the reduction
   * @param function the function to reduce two values to one
   * @param list the list of elements to resolve, e.g. sum up
   * @param <T> template for the object class
   * @return the single element that is left after reduction of all other ones. Will be null if the list is null or no
   *         function is given.
   */
  public static <T> T resolve(final T initial, final BinaryFunction<T, T> function, final List<T> list) {
    if (list == null || function == null) return null;

    T result = initial;
    boolean first = true;

    for (T item : list) {
      if (first && initial == null) {
        // initialize with the first value of the list
        result = item;
      } else {
        result = function.execute(result, item);
      }
      first = false;
    }

    return result;
  }

  /**
   * Eliminates all elements from the array until a single element is left over.
   *
   * @param initial an initial value to start the reduction
   * @param function the function to reduce two values to one
   * @param list the list of elements to resolve, e.g. sum up
   * @param <T> template for the object class
   * @return the single element that is left after reduction of all other ones. Will be null if the list is null or no
   *         function is given.
   */
  public static <T> T resolve(final T initial, final BinaryFunction<T, T> function, final T... list) {
    if (list == null || function == null) return null;
    return resolve(initial, function, Arrays.asList(list));
  }

  /**
   * Eliminates all elements from the list until a single element is left over.
   *
   * Alternative name for <code>resolve</code>.
   *
   * @param function the function to reduce two values to one
   * @param list the list of elements to resolve, e.g. sum up
   * @param <T> template for the object class
   * @return the single element that is left after reduction of all other ones. Will be null if the list is null or no
   *         function is given.
   */
  public static <T> T reduce(final BinaryFunction<T, T> function, final List<T> list) {
    return resolve(null, function, list);
  }

  /**
   * Eliminates all elements from the list until a single element is left over.
   *
   * Alternative name for <code>resolve</code>.
   *
   * @param function the function to reduce two values to one
   * @param list the list of elements to resolve, e.g. sum up
   * @param <T> template for the object class
   * @return the single element that is left after reduction of all other ones. Will be null if the list is null or no
   *         function is given.
   */
  public static <T> T reduce(final BinaryFunction<T, T> function, final T... list) {
    if (list == null || function == null) return null;
    return resolve(function, Arrays.asList(list));
  }

  /**
   * Eliminates all elements from the list until a single element is left over.
   *
   * Alternative name for <code>resolve</code>.
   *
   * @param initial an initial value to start the reduction
   * @param function the function to reduce two values to one
   * @param list the list of elements to resolve, e.g. sum up
   * @param <T> template for the object class
   * @return the single element that is left after reduction of all other ones. Will be null if the list is null or no
   *         function is given.
   */
  public static <T> T reduce(final T initial, final BinaryFunction<T, T> function, final List<T> list) {
    return resolve(initial, function, list);
  }

  /**
   * Eliminates all elements from the array until a single element is left over.
   *
   * Alternative name for <code>resolve</code>.
   *
   * @param initial an initial value to start the reduction
   * @param function the function to reduce two values to one
   * @param list the list of elements to resolve, e.g. sum up
   * @param <T> template for the object class
   * @return the single element that is left after reduction of all other ones. Will be null if the list is null or no
   *         function is given.
   */
  public static <T> T reduce(final T initial, final BinaryFunction<T, T> function, final T... list) {
    if (list == null || function == null) return null;
    return resolve(initial, function, Arrays.asList(list));
  }
}
