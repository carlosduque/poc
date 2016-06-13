package o.xml.jaxb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "vehiculo")
class Vehicle {
	@XmlElement(name = "identificador")
	private String id;
	Vehicle (String identification) {
		id = identification;
	}
	Vehicle () {
		id = "unknown";
	}
	public String getId() { return id; }
}
@XmlRootElement(name = "carro")
class Car extends Vehicle {
	@XmlElementWrapper(name = "partes")
	public Map<String, String> parts;
	Car(String id) {
		super(id);
		parts = new HashMap<String, String>();
	}
	Car() {
		this("somecar");
	}
	public void addPart(String name, String value) { parts.put(name, value); }
	public Map<String, String> getParts() { return parts; }
}
@XmlRootElement(name = "parqueo")
class ParkingLot {
	@XmlElementWrapper(name = "vehiculos")
	private List<Car> vehicles = new ArrayList<Car>();
	public void add(Car v) {
		vehicles.add(v);
	}
}

public class SimpleJaxbMain {

	public static void main(String... args) throws JAXBException {
		ParkingLot lot = new ParkingLot();
		Car kia = new Car("kia");
		kia.addPart("engine", "1.6");
		kia.addPart("wheels", "4");
		kia.addPart("doors", "4");
		lot.add(kia);
		lot.add(new Car("somevehicle"));

		// create JAXB context and instantiate marshaller
		JAXBContext context = JAXBContext.newInstance(ParkingLot.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out
		m.marshal(lot, System.out);
	}

}
