

/**
 * Blueprint for accepting jobs
 * Classes that implement this interface can accept products
 */
public interface CustAcceptor {
    /**
     * Method to have this object process an event
     *
     * @param customer The customer that is accepted
     * @return true if accepted
     */
    boolean giveProduct(Customer customer);
}
