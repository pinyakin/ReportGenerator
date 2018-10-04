package settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "column")
public class Column {

  @XmlElement(name = "title")
  private String title;
  @XmlElement(name = "width")
  private int width;

  public String getTitle() {
    return title;
  }

  public int getWidth() {
    return width;
  }
}
