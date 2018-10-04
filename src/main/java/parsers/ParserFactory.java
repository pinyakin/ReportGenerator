package parsers;

public class ParserFactory {

  public static Parsable getParser(String extension) {
    if (extension.equals("tsv")) {
      return new TsvParser();
    }
    if (extension.equals("xml")) {
      return new XmlParser();
    }
    return null;
  }
}
