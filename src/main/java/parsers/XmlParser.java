package parsers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.log4j.Logger;
import settings.Settings;

public class XmlParser implements Parsable {

  private static final Logger log = Logger.getLogger(XmlParser.class);
  private Settings settings;

  public XmlParser() {
  }

  public Settings getSettings() {
    return settings;
  }

  public void parse(String fileName) {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
      XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory()
          .createXMLStreamReader(new FileInputStream(fileName));
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      xmlStreamReader.nextTag();
      settings = (Settings) unmarshaller.unmarshal(xmlStreamReader);
    } catch (JAXBException e) {
      log.error("Exception parsing elements of xml file", e);
    } catch (FileNotFoundException e) {
      log.error("xml file not found", e);
    } catch (XMLStreamException e) {
      log.error("Exception reading xml file", e);
    }
  }
}
