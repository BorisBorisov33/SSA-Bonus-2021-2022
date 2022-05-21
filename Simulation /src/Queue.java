

import java.util.ArrayList;

/**
 *	Queue that stores products until they can be handled on a machine machine
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Queue implements ProductAcceptor
{
    //	priority for service desl
    private final boolean priorityservicedesk = false;

    /** List in which the products are kept */
    private ArrayList<Product> row;
    /** Requests from machine that will be handling the products */
    private ArrayList<Machine> requests;


    //If it has regular customer next to the service customer
    private final boolean hasServiceDesk;
    /**
     *	Initializes the queue and introduces a dummy machine
     *	the machine has to be specified later
     */
    public Queue(boolean hasServiceDesk)
    {
        this.row = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.hasServiceDesk = hasServiceDesk;
    }

    /**
     *	Asks a queue to give a product to a machine
     *	True is returned if a product could be delivered; false if the request is queued
     */
    public boolean askProduct(Machine machine)

    {
        // This is only possible with a non-empty queue
        if(row.size()>0)
        {
            // If the machine accepts the product
            if(machine.giveProduct(row.get(0)))
            {
                row.remove(0);// Remove it from the queue
                return true;
            }
            else
                return false; // Machine rejected; don't queue request
        }
        else
        {
            requests.add(machine);
            return false; // queue request
        }
    }

    public Product getPriority(){
        for(Product p : row){
            if(p.getCustomerType().equals(CustType.valueOf("ServiceDesk"))){
                return p;
            }
        }
        return row.get(0);

    }

    /**
     *	Offer a product to the queue
     *	It is investigated whether a machine wants the product, otherwise it is stored
     */
    public boolean giveProduct(Product p)
    {
        // Check if the machine accepts it
        // each queue can store up to 4 customers
        if(requests.size()<1 && row.size() <= 3)
            row.add(p); // Otherwise store it
        else
        {
            boolean delivered = false;
            while(!delivered & (requests.size()>0))
            {
                delivered=requests.get(0).giveProduct(p);
                // remove the request regardless of whether or not the product has been accepted
                requests.remove(0);
            }
            if(!delivered && row.size() <= 3)
                row.add(p); // Otherwise store it
        }
        return true;
    }



//	private int getNextCustomerIndex() {
//		if (hasServiceDesk && priorityservicedesk) {
//			for (int i = 0; i < row.size(); i++) {
//				Product cust = row.get(i);
//				if (cust.get() == JobType.GPU) {
//					return i;
//				}
//			}
//		}
//
//		return 0;
//	}

    public int getLength() {

        return row.size();

    }

    public double expectedWaitingTime() {
        double waitingTime = 0;

        for (Product cust : row) {
            if (cust.getCustomerType() == CustType.Regular) {
                //mean Regular customer time
                waitingTime += (2 + 6. / 60);
            } else if (cust.getCustomerType() == CustType.ServiceDesk) {
                //mean Servicedesk cust time
                waitingTime += (4 + 1. / 60);
            }
        }

        return waitingTime;
    }
}