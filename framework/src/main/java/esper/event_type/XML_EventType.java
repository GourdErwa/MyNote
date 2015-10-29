package esper.event_type;

import com.espertech.esper.client.ConfigurationEventTypeXMLDOM;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import document.Unfinished;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-28
 * Time: 17:57
 */
@Unfinished
public class XML_EventType {


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        XML_EventType xml_eventType = new XML_EventType();
        xml_eventType.getA();
    }

    public void getA() throws ParserConfigurationException, IOException, SAXException {
        URL schemaURL = this.getClass().getClassLoader().getResource("sensor.xsd");
        URL xml = this.getClass().getClassLoader().getResource("sensor.xml");

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        ConfigurationEventTypeXMLDOM sensorcfg = new ConfigurationEventTypeXMLDOM();
        sensorcfg.setRootElementName("Sensor");
        sensorcfg.setSchemaResource(schemaURL.toString());
        epService.getEPAdministrator().getConfiguration()
                .addEventType("SensorEvent", sensorcfg);

        String epl = "select ID, Observation.Command, Observation.ID, " +
                "  Observation.Tag[0].ID, Observation.Tag[1].ID" +
                "from SensorEvent";

        InputSource source = new InputSource(new StringReader(xml.toString()));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document doc = builderFactory.newDocumentBuilder().parse(source);

        epService.getEPRuntime().sendEvent(doc);
    }

}
