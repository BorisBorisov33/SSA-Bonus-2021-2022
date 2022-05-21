

import org.w3c.dom.html.HTMLImageElement;

import java.util.ArrayList;

public class Customer {


    private final ArrayList<Double> times;
    private final ArrayList<String> events;
    private final ArrayList<String> stations;
    private final CustType customertype;

    public Customer(CustType custtype) {
        this.times = new ArrayList<>();
        this.events = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.customertype = custtype;

    }

    /**
     * Record how the product got processed
     *
     * @param time
     * @param event
     * @param station
     */
    public void stamp(double time, String event, String station) {
        this.times.add(time);
        this.events.add(event);
        this.stations.add(station);
    }

    public ArrayList<Double> getTimes() {
        return times;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public ArrayList<String> getStations() {
        return stations;
    }

    public CustType getcustomerType() {
        return customertype;
    }
    public double[] getTimesAsArray(){
        times.trimToSize();
        double[] tempo = new double[times.size()];
        for(int i=0; i<times.size();i++){
            tempo[i] = times.get(i);
        }
        return tempo;
    }
    public String[] getEventsAsArray() {
        String[] tmp = new String[events.size()];
        tmp = events.toArray(tmp);
        return tmp;
    }
    public String[]getStationsAssArray(){
        String[] tempo = new String[stations.size()];
        tempo = stations.toArray(tempo);
        return tempo;
    }
    @Override
    public String toString() {
        return "Product{" +
                "productType=" + customertype +
                '}';
    }


}
