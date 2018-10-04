package parsers;

import com.univocity.parsers.tsv.TsvParserSettings;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.log4j.Logger;

public class TsvParser implements Parsable {

  private static final Logger log = Logger.getLogger(TsvParser.class);

  private List<String[]> tsvData;

  public List<String[]> getTsvData() {
    return tsvData;
  }

  public void parse(String fileName) {
    TsvParserSettings settings = new TsvParserSettings();
    com.univocity.parsers.tsv.TsvParser parser = new com.univocity.parsers.tsv.TsvParser(settings);

    FileInputStream fileIn;
    try {
      fileIn = new FileInputStream(fileName);
      tsvData = parser.parseAll(new InputStreamReader(fileIn, "UTF-16"));

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      log.error("File \"" + fileName + "\" not found", e);
    } catch (UnsupportedEncodingException e) {
      log.error(".tsv file EncodingException", e);
    }
  }
}
