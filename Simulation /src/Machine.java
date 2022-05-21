
import java.util.ArrayList;
import java.util.List;

/**
 *	Machine in a factory
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Machine implements CProcess,ProductAcceptor
{
    /** Product that is being handled  */
    private Product product;
    /** Eventlist that will manage events */
    private final CEventList eventlist;
    /** Queue from which the machine has to take products */
    private Queue queue1;

    private Queue queue2;
    /** Sink to dump products */
    private ProductAcceptor sink;
    /** Status of the machine (b=busy, i=idle) */
    private char status;
    /** Machine name */
    private final String name;
    /** Mean processing time */
    private double meanProcTime;
    private double meanProcTime2;
    /** Processing times (in case pre-specified) */
    private double[] processingTimes;
    private List<Double> departureTimes = new ArrayList<>() ;
    /** Processing time iterator */
    private int procCnt;

    private double delay;

    private NormalServiceDist normal;
    private double std;


    /**
     *	Constructor
     *        Service times are exponentially distributed with mean 30
     *	@param q	Queue from which the machine has to take products
     *	@param s	Where to send the completed products
     *	@param e	Eventlist that will manage events
     *	@param n	The name of the machine
     */
    public Machine(Queue q, ProductAcceptor s, CEventList e, String n)
    {
        status='i';
        queue1=q;
        queue2 = null;
        sink=s;
        eventlist=e;
        name=n;
        meanProcTime=30;
        queue1.askProduct(this);
    }

    /**
     *	Constructor
     *        Service times are exponentially distributed with specified mean
     *	@param q	Queue from which the machine has to take products
     *	@param s	Where to send the completed products
     *	@param e	Eventlist that will manage events
     *	@param n	The name of the machine
     *   @param m	Mean processing time
     */
    public Machine(Queue q, ProductAcceptor s, CEventList e, String n, double m, double std)
    {
        status='i';
        queue1=q;
        queue2 = null;
        sink=s;
        eventlist=e;
        name=n;
        meanProcTime=m;
        this.std = std;
        queue1.askProduct(this);
        this.normal = new NormalServiceDist(meanProcTime, std);

    }


    /**
     *	Constructor
     *        Service times are exponentially distributed with specified mean
     *	@param q1	Queue from which the machine has to take products
     *	@param s	Where to send the completed products
     *	@param e	Eventlist that will manage events
     *	@param n	The name of the machine
     *   @param m	Mean processing time
     */
    public Machine(Queue q1, Queue q2,  ProductAcceptor s, CEventList e, String n, double m, double m1, double std )
    {
        status='i';
        queue1 =q1;
        queue2 = q2;
        sink=s;
        eventlist=e;
        name=n;
        meanProcTime=m;
        meanProcTime2 = m1;
        this.std = std;
        queue1.askProduct(this);
        queue2.askProduct(this);
        this.normal = new NormalServiceDist(meanProcTime, std);
    }

    /**
     *	Constructor
     *        Service times are pre-specified
     *	@param q	Queue from which the machine has to take products
     *	@param s	Where to send the completed products
     *	@param e	Eventlist that will manage events
     *	@param n	The name of the machine
     *        @param st	service times
     */
    public Machine(Queue q, ProductAcceptor s, CEventList e, String n, double[] st)
    {
        status='i';
        queue1=q;
        queue2= null;
        sink=s;
        eventlist=e;
        name=n;
        meanProcTime=-1;
        processingTimes=st;
        procCnt=0;
        queue1.askProduct(this);
    }

    /**
     *	Method to have this object execute an event
     *	@param type	The type of the event that has to be executed
     *	@param tme	The current time
     */
    public void execute(int type, double tme)
    {
        // show arrival
        //System.out.println("Product finished at time for " + name + " :  " +  tme);
        departureTimes.add(tme);
        // Remove product from system
        product.stamp(tme,"Production complete",name);
        System.out.println(product.getEvents() + " " + product.getTimes());
        delay = product.getTimes().get(0) - product.getTimes().get(2) ;
        sink.giveProduct(product);
        product=null;
        // set machine status to idle
        status='i';
        // Ask the queue for products
        queue1.askProduct(this);
        if(queue2 != null) {
            queue2.askProduct(this);
        }
    }

    /**
     *	Let the machine accept a product and let it start handling it
     *	@param p	The product that is offered
     *	@return	true if the product is accepted and started, false in all other cases
     */
    @Override
    public boolean giveProduct(Product p)
    {
        // Only accept something if the machine is idle
        if(status=='i')
        {
            // accept the product
            product=p;
            // mark starting time
            product.stamp(eventlist.getTime(),"Production started",name);
            // start production
            startProduction();
            // Flag that the product has arrived
            return true;
        }
        // Flag that the product has been rejected
        else return false;
    }

    /**
     *	Starting routine for the production
     *	Start the handling of the current product with an exponentionally distributed processingtime with average 30
     *	This time is placed in the eventlist
     */
    private void startProduction()
    {
        // generate duration
        if(meanProcTime>0)
        {
            double duration;
            if(queue2 != null && queue2.getLength() != 0){
                normal = new NormalServiceDist(4.1, 1.1);
                duration = normal.drawServiceTime();
            }else{
                duration = normal.drawServiceTime();
            }
            // Create a new event in the eventlist
            double tme = eventlist.getTime();
            eventlist.add(this,0,tme+duration); //target,type,time
            // set status to busy
            status='b';
        }
        else
        {
            if(processingTimes.length>procCnt)
            {
                eventlist.add(this,0,eventlist.getTime()+processingTimes[procCnt]); //target,type,time
                // set status to busy
                status='b';
                procCnt++;
            }
            else
            {
                eventlist.stop();
            }
        }
    }
    public List<Double> getDepartureTimes(){

        return departureTimes;

    }

    public String getName() {

        return name;
    }

    public double getDelay(){
        return delay;
    }

    //TODO : change this to normal distribution

}