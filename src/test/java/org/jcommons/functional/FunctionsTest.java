package org.jcommons.functional;

import static org.jcommons.functional.Functions.every;
import static org.jcommons.functional.Functions.filter;
import static org.jcommons.functional.Functions.forEach;
import static org.jcommons.functional.Functions.map;
import static org.jcommons.functional.Functions.reduce;
import static org.jcommons.functional.Functions.resolve;
import static org.jcommons.functional.Functions.some;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.jcommons.functional.function.UnaryFunction;
import org.jcommons.functional.functions.AsString;
import org.jcommons.functional.functions.DoubleSum;
import org.jcommons.functional.predicate.UnaryPredicate;
import org.junit.*;

/**
 * Test functional library <code>Functions</code>.
 *
 * @author Thorsten Goeckeler
 */
public class FunctionsTest
{
  /** dummy object to test against */
  class Customer
  {
    private Integer id;
    private String name;

    /**
     * create a customer with an id and name
     *
     * @param id the internal identifier
     * @param name the name for this customer
     */
    public Customer(final Integer id, final String name) {
      setId(id);
      setName(name);
    }

    /** @return the identifier for this customer */
    public Integer getId() {
      return id;
    }

    /**
     * set the id
     *
     * @param id the new identifier
     */
    public void setId(final Integer id) {
      this.id = id;
    }

    /**
     * @return the name of this customer
     */
    public String getName() {
      return name;
    }

    /**
     * rename the customer
     *
     * @param name the new name, never null
     */
    public void setName(final String name) {
      this.name = name;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
      return name != null ? name : "";
    }
  }

  /**
   * sample unary function
   *
   * @author Thorsten Göckeler
   */
  class CustomerToString
    implements UnaryFunction<String, Customer>
  {
    private final List<String> names = new ArrayList<String>();

    /** @return the list of all customer string representations executed so far */
    public List<String> getNames() {
      return names;
    }

    /** {@inheritDoc} */
    @Override
    public String execute(final Customer customer) {
      StringBuilder text = new StringBuilder();
      if (customer != null) {
        if (customer.getId() != null) {
          text.append("(");
          text.append(customer.getId());
          text.append(") ");
        }

        text.append("\"");
        if (customer.getName() != null) {
          text.append(customer.getName());
        }
        text.append("\"");
      }

      names.add(text.toString());
      return text.toString();
    }
  }

  /**
   * sample unary predicate
   *
   * @author Thorsten Göckeler
   */
  class CustomerLikeFilter
    implements UnaryPredicate<Customer>
  {
    private final String name;

    /**
     * constructor that accepts a like expression
     *
     * @param name the name pattern to match against
     */
    public CustomerLikeFilter(final String name) {
      this.name = name;
    }

    /** {@inheritDoc} */
    @Override
    public boolean execute(final Customer customer) {
      return customer.toString().indexOf(name) >= 0;
    }
  }

  private final Double[] doubles = { 1.0, 2.0, 3.0 };

  private List<Customer> customers;

  /**
   * Sets up the test case. Invoked by default before every test case.
   *
   * @throws Exception super.setUp() throws an Exception
   */
  @Before
  public void setUp()
    throws Exception
  {
    customers = new ArrayList<Customer>();
    customers.add(new Customer(1, "Hermann Maier"));
    customers.add(new Customer(4, "Markus Stahl"));
    customers.add(new Customer(8, "Jochen Busser"));
  }

  /**
   * Cleans up the test case. Invoked by default after every test case.
   *
   * @throws Exception super.tearDown() throws an Exception
   */
  @After
  public void tearDown()
    throws Exception
  {
    customers = null;
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.forEach(UnaryFunction&lt;R, T&gt;, List&lt;T&gt;) &lt;R, T&gt;'
   */
  @Test
  public void testForEachList() {
    CustomerToString function = new CustomerToString();
    forEach(function, customers);
    assertTrue(function.getNames() != null);
    assertTrue(!function.getNames().isEmpty());
    assertEquals(3, function.getNames().size());
    assertEquals("(1) \"Hermann Maier\"", function.getNames().get(0).toString());

    function = null;
    forEach(function, customers);
    assertEquals(3, customers.size());

    customers = null;
    function = new CustomerToString();
    forEach(function, customers);
    assertTrue(function.getNames() != null);
    assertTrue(function.getNames().isEmpty());

  }

  /**
   * Test method for 'org.jcommons.functional.Functions.forEach(UnaryFunction&lt;R, T&gt;, T...) &lt;R, T&gt;'
   */
  @Test
  public void testForEachArray() {
    CustomerToString function = new CustomerToString();
    forEach(function, customers.toArray(new Customer[0]));
    assertTrue(function.getNames() != null);
    assertTrue(!function.getNames().isEmpty());
    assertEquals(3, function.getNames().size());
    assertEquals("(1) \"Hermann Maier\"", function.getNames().get(0).toString());

    forEach(null, customers.toArray(new Customer[0]));
    function = new CustomerToString();
    forEach(function, (Customer[]) null);
    assertNotNull(function.getNames());
    assertTrue(function.getNames().isEmpty());
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.map(UnaryFunction&lt;R, T&gt;, List&lt;T&gt;) &lt;R, T&gt;'
   */
  @Test
  public void testMapList() {
    List<String> list = map(new AsString<Customer>(), customers);
    assertTrue(list != null);
    assertTrue(!list.isEmpty());
    assertEquals(3, list.size());
    assertEquals("Hermann Maier", list.get(0).toString());

    list = map(new CustomerToString(), customers);
    assertTrue(list != null);
    assertTrue(!list.isEmpty());
    assertEquals(3, list.size());
    assertEquals("(1) \"Hermann Maier\"", list.get(0).toString());

    customers = null;
    assertNull(map(new CustomerToString(), customers));
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.map(UnaryFunction&lt;R, T&gt;, T...) &lt;R, T&gt;'
   */
  @Test
  public void testMapArray() {
    Customer[] clients = customers.toArray(new Customer[0]);

    List<String> list = map(new AsString<Customer>(), clients);
    assertTrue(list != null);
    assertTrue(!list.isEmpty());
    assertEquals(3, list.size());
    assertEquals("Hermann Maier", list.get(0).toString());

    list = map(new CustomerToString(), clients[0], clients[1], clients[2]);
    assertTrue(list != null);
    assertTrue(!list.isEmpty());
    assertEquals(3, list.size());
    assertEquals("(1) \"Hermann Maier\"", list.get(0).toString());

    assertNull(map(new CustomerToString(), (Customer[]) null));
    assertNotNull(map(null, clients));
    assertTrue(map(null, clients).isEmpty());
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.some(UnaryPredicate&lt;T&gt;, List&lt;T&gt;) &lt;T&gt;'
   */
  @Test
  public void testSomeList() {
    assertTrue(some(new CustomerLikeFilter("Maier"), customers));
    assertTrue(some(new CustomerLikeFilter("e"), customers));
    assertFalse(some(new CustomerLikeFilter("Richard"), customers));

    assertFalse(some(null, customers));
    assertFalse(some(new CustomerLikeFilter("e"), (List<Customer>) null));
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.some(UnaryPredicate&lt;T&gt;, T...) &lt;T&gt;'
   */
  @Test
  public void testSomeArray() {
    Customer[] clients = customers.toArray(new Customer[0]);
    assertTrue(some(new CustomerLikeFilter("Maier"), clients));
    assertTrue(some(new CustomerLikeFilter("e"), clients[0], clients[1], clients[2]));
    assertFalse(some(new CustomerLikeFilter("Richard"), clients));

    assertFalse(some(null, clients));
    assertFalse(some(new CustomerLikeFilter("e"), (Customer[]) null));
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.every(UnaryPredicate&lt;T&gt;, List&lt;T&gt;) &lt;T&gt;'
   */
  @Test
  public void testEveryList() {
    assertTrue(every(new CustomerLikeFilter("r"), customers));
    assertFalse(every(new CustomerLikeFilter("e"), customers));
    assertFalse(every(null, customers));

    List<Customer> nullList = null;
    assertFalse(every(new CustomerLikeFilter("r"), nullList));
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.every(UnaryPredicate&lt;T&gt;, T...) &lt;T&gt;'
   */
  @Test
  public void testEveryArray() {
    Customer[] clients = customers.toArray(new Customer[0]);
    assertTrue(every(new CustomerLikeFilter("r"), clients));
    assertTrue(every(new CustomerLikeFilter("r"), clients[0], clients[1], clients[2]));
    assertFalse(every(new CustomerLikeFilter("e"), clients));

    assertFalse(every(new CustomerLikeFilter("r"), (Customer[]) null));
    assertFalse(every(null, clients));
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.filter(UnaryPredicate&lt;T&gt;, List&lt;T&gt;) &lt;T&gt;'
   */
  @Test
  public void testFilterList() {
    List<Customer> list = filter(new CustomerLikeFilter("s"), customers);
    assertTrue(list != null);
    assertTrue(!list.isEmpty());
    assertEquals(2, list.size());
    assertEquals(new Integer(4), list.get(0).getId());

    list = filter(new CustomerLikeFilter("z"), customers);
    assertTrue(list != null);
    assertTrue(list.isEmpty());

    list = filter(null, customers);
    assertTrue(list != null);
    assertEquals(customers.size(), list.size());

    List<Customer> nullList = null;
    list = filter(new CustomerLikeFilter("s"), nullList);
    assertNull(list);
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.filter(UnaryPredicate&lt;T&gt;, T...) &lt;T&gt;'
   */
  @Test
  public void testFilterArray() {
    Customer[] clients = customers.toArray(new Customer[0]);
    List<Customer> list = filter(new CustomerLikeFilter("s"), clients);
    assertTrue(list != null);
    assertTrue(!list.isEmpty());
    assertEquals(2, list.size());
    assertEquals(new Integer(4), list.get(0).getId());

    list = filter(new CustomerLikeFilter("z"), clients);
    assertTrue(list != null);
    assertTrue(list.isEmpty());

    list = filter(null, clients);
    assertTrue(list != null);
    assertEquals(clients.length, list.size());

    clients = null;
    assertNull(filter(new CustomerLikeFilter("z"), clients));
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.resolve(BinaryFunction&lt;T, T&gt;, List&lt;T&gt;) &lt;T&gt;'
   */
  @Test
  public void testResolveList() {
    assertEquals(new Double(6.0), resolve(new DoubleSum(), Arrays.asList(doubles)));
    assertEquals(new Double(6.0), reduce(new DoubleSum(), Arrays.asList(doubles)));

    assertNull(resolve(null, Arrays.asList(doubles)));
    assertNull(resolve(new DoubleSum(), (List<Double>) null));
    assertNull(reduce(null, Arrays.asList(doubles)));
    assertNull(reduce(new DoubleSum(), (List<Double>) null));
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.resolve(BinaryFunction&lt;T, T&gt;, T...) &lt;T&gt;'
   */
  @Test
  public void testResolveArray() {
    assertEquals(new Double(6.0), resolve(new DoubleSum(), doubles));
    assertEquals(new Double(3.5), resolve(new DoubleSum(), 1.0, 2.5));
    assertEquals(new Double(6.0), reduce(new DoubleSum(), doubles));
    assertEquals(new Double(3.5), reduce(new DoubleSum(), 1.0, 2.5));

    assertNull(resolve(null, 1.0, 2.5));
    assertNull(resolve(new DoubleSum(), (Double[]) null));
    assertNull(reduce(null, 1.0, 2.5));
    assertNull(reduce(new DoubleSum(), (Double[]) null));
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.resolve(BinaryFunction&lt;T, T&gt;, T, List&lt;T&gt;) &lt;T&gt;'
   */
  @Test
  public void testResolveListInitial() {
    assertEquals(new Double(12.0), resolve(6.0, new DoubleSum(), Arrays.asList(doubles)));
    assertEquals(new Double(12.0), reduce(6.0, new DoubleSum(), Arrays.asList(doubles)));
  }

  /**
   * Test method for 'org.jcommons.functional.Functions.resolve(BinaryFunction&lt;T, T&gt;, T, T...) &lt;T&gt;'
   */
  @Test
  public void testResolveArrayInitial() {
    assertEquals(new Double(12.0), resolve(6.0, new DoubleSum(), doubles));
    assertEquals(new Double(9.5), resolve(6.0, new DoubleSum(), 1.0, 2.5));
    assertEquals(new Double(12.0), reduce(6.0, new DoubleSum(), doubles));
    assertEquals(new Double(9.5), reduce(6.0, new DoubleSum(), 1.0, 2.5));

    assertNull(resolve(6.0, null, 1.0, 2.5));
    assertNull(resolve(6.0, new DoubleSum(), (Double[]) null));
    assertNull(reduce(6.0, null, 1.0, 2.5));
    assertNull(reduce(6.0, new DoubleSum(), (Double[]) null));
  }
}
