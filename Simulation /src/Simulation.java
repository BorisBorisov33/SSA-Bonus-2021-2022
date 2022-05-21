/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */
import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {

    public CEventList list;
    public Queue queue;
    public Source source;
    public Sink sink;
    public Machine mach;



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int number_simulation = 100;
        int maxSimulation_duration = 24*7*6;
        Random random = new Random();

        List<ArrayList<List<Double>>> reportDataPerSimulation = new ArrayList<>();

        for (int i = 0; i < number_simulation; i++) {

            // Create an eventlist
            CEventList l = new CEventList();

            List<Machine> cashRegisters = new ArrayList<>();
            List<Source> sources = new ArrayList<>();
            List<Queue> queues = new ArrayList<>();

            //create queues
            Queue queue1 = new Queue(false);
            Queue queue2 = new Queue(false);
            Queue queue3 = new Queue(false);
            Queue queue4 = new Queue(false);
            Queue queue5 = new Queue(false);
            Queue queue6 = new Queue(true);
            Queue queue7 = new Queue(false);


            Source source1 = new Source(queue1 , l , "Source 1", 1);
            Source source2 = new Source(queue2 , l , "Source 2",1);
            Source source3 = new Source(queue3, l, "Source 3", 1);
            Source source4 = new Source(queue4, l, "Source 4", 1);
            Source source5 = new Source(queue5, l, "Source 5", 1);
            Source source6 = new Source(queue6 , l , "Source 6",5);
            Source source7 = new Source(queue7 , l , "Source 7",1);


            Sink sink = new Sink("Sink 1");
            // A machine
            Machine cashRegister1 = new Machine(queue1, sink, l, "Cash Register 1",2.6,1.1);
            Machine cashRegister2 = new Machine(queue2, sink, l, "Cash Register 2",2.6,1.1);
            Machine cashRegister3 = new Machine(queue3, sink, l, "CashRegister 3 ", 2.6,1.1);
            Machine cashRegister4 = new Machine(queue4, sink, l, "Cash Register 4", 2.6, 1.1);
            Machine cashRegister5 = new Machine(queue5, sink, l, "Cash Register 5", 2.6,1.1);
            Machine cashRegister6 = new Machine(queue6,queue7 , sink, l, "Cash Register 6",2.6, 4.1, 1.1);

            cashRegisters.add(cashRegister1);
            cashRegisters.add(cashRegister2);
            cashRegisters.add(cashRegister3);
            cashRegisters.add(cashRegister4);
            cashRegisters.add(cashRegister5);
            cashRegisters.add(cashRegister6);

            sources.add(source1);
            sources.add(source2);
            sources.add(source6);
            sources.add(source7);
            sources.add(source3);
            sources.add(source4);
            sources.add(source5);

            queues.add(queue1);
            queues.add(queue2);
            queues.add(queue6);
            queues.add(queue7);

            // calculate average delay
            //to calculate only for service desk as given in the question, remove other
            // regular cash registers.
           /* double sumD = 0;
            for(Machine machine : cashRegisters){
                sumD += machine.getDelay();
            }
            double meanDelay = sumD / cashRegisters.size();
            System.out.println("Mean Delay : " + meanDelay);*/

            l.start(2000); // 2000 is maximum time

            double sumD = 0;
            for(Machine machine : cashRegisters){
                sumD += machine.getDelay();
            }
            double meanDelay = sumD / cashRegisters.size();
            System.out.println("Mean Delay : " + meanDelay);

           double  sumQ = 0;
            for(Queue queue : queues){
                sumQ += queue.getLength();

            }
            double meanQueue = sumQ/queues.size();
            System.out.println("Average Queue Length : " + meanQueue);

         










        }
    }

}
