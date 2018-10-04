import generators.ReportGenerator;
import parsers.ParserFactory;
import parsers.TsvParser;
import parsers.XmlParser;

public class Generator {

  private static final String XML = "xml";
  private static final String TSV = "tsv";

  public static void main(String[] args) {

    XmlParser xmlParser = (XmlParser) ParserFactory.getParser(XML);
    xmlParser.parse(args[0]);

    TsvParser tsvParser = (TsvParser) ParserFactory.getParser(TSV);
    tsvParser.parse(args[1]);

    ReportGenerator reportGenerator = new ReportGenerator(xmlParser.getSettings(),
        tsvParser.getTsvData());
    reportGenerator.generate(args[2]);
  }
}
