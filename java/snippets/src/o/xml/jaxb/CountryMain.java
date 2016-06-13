/**
 *
 */
package o.xml.jaxb;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//Below annotation defines root element of XML file
@XmlRootElement
//You can define order in which elements will be created in XML file
//Optional
@XmlType(propOrder = { "countryName", "countryPopulation", "listOfStates"})
class Country {

    private String countryName;
    private double countryPopulation;

    private ArrayList<State> listOfStates;
    public Country() {

    }
    public String getCountryName() {
        return countryName;
    }
    @XmlElement
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public double getCountryPopulation() {
        return countryPopulation;
    }

    @XmlElement
    public void setCountryPopulation(double countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    public ArrayList<State> getListOfStates() {
        return listOfStates;
    }

    // XmLElementWrapper generates a wrapper element around XML representation
    @XmlElementWrapper(name = "stateList")
    // XmlElement sets the name of the entities in collection
    @XmlElement(name = "state")
    public void setListOfStates(ArrayList<State> listOfStates) {
        this.listOfStates = listOfStates;
    }

}

//Below statement means that class 'Country.java' is the root-element of our example
@XmlRootElement(namespace = "o.xml.jaxb.Country")
class State {
    private String stateName;
    long statePopulation;

    public State() {
    }

    public State(String stateName, long statePopulation) {
        super();
        this.stateName = stateName;
        this.statePopulation = statePopulation;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public long getStatePopulation() {
        return statePopulation;
    }

    public void setStatePopulation(long statePopulation) {
        this.statePopulation = statePopulation;
    }
}

public class CountryMain {

    public static void main(String... args) {
        marshall();
        unmarshall();
    }

    private static void marshall() {
        // creating country object
        Country countryIndia = new Country(); 
        countryIndia.setCountryName("India");
        countryIndia.setCountryPopulation(5000000);
        // Creating listOfStates
        ArrayList<State> stateList = new ArrayList<State>();
        State mpState=new State("Madhya Pradesh",1000000);
        stateList.add(mpState);
        State maharastraState = new State("Maharastra", 2000000);
        stateList.add(maharastraState);

        countryIndia.setListOfStates(stateList);

        try {

            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Country.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // for getting nice formatted output
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //specify the location and name of xml file to be created
            File XMLfile = new File("CountryRecord.xml");

            // Writing to XML file
            jaxbMarshaller.marshal(countryIndia, XMLfile);
            // Writing to console
            jaxbMarshaller.marshal(countryIndia, System.out);

        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }
    }

    private static void unmarshall() {
        try {
            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Country.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File("CountryRecord.xml");

            // this will create Java object - country from the XML file
            Country countryIndia = (Country) jaxbUnmarshaller.unmarshal(XMLfile);

            System.out.println("Country Name: " + countryIndia.getCountryName());
            System.out.println("Country Population: " + countryIndia.getCountryPopulation());

            ArrayList<State> listOfStates=countryIndia.getListOfStates();

            int i=0;
            for(State state:listOfStates) {
                i++;
                System.out.println("State:" + i + " " + state.getStateName());
            }

        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }
    }

}
