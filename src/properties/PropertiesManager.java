package properties;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * PropertiesManager using Singleton Design Pattern.
 * @author Itamar&Chen
 */
public class PropertiesManager {
	
	private static PropertiesManager instance;
	
	private Properties properties;
	
	public Properties getProperties() {
		return properties;
	}
	
	/***
	 * Private constructor to allow creation of one instance only.
	 */
	private PropertiesManager() 
	{
		properties = new Properties();
		try {
			File file = new File("resources/properties.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(properties.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(properties, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * returns the created instance of type PropertiesManager
	 * @return
	 */
	public static PropertiesManager getInstance() {
		if (instance == null) 
			instance = new PropertiesManager();
		return instance;
	}
	
	public Properties readXML(String path) {
		getInstance();
		try {
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(Properties.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			properties = (Properties)unmarshaller.unmarshal(file);
		} 
		catch (JAXBException e) {
			e.printStackTrace();
		}
		return properties;
	}


	public void writeXml(String viewType) {
		getInstance();
		properties.setViewType(viewType);
		try {
			File file = new File("resources/properties.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(properties.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(properties, file);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
