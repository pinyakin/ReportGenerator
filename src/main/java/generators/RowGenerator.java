package generators;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import settings.Column;
import settings.Settings;

public class RowGenerator {

  private static final Logger log = Logger.getLogger(RowGenerator.class);

  private static final char WORD_SEPARATOR = ' ';
  private static final char COLUMN_SEPARATOR = '|';
  private static final char ROW_SEPARATOR = '-';

  private String headRow;
  private String separatorRow;
  private Settings settings;

  public RowGenerator(Settings settings) {
    this.settings = settings;
    generateHead(settings.getColumns());
    generateRowDelimiter();
  }


  public String getHeadRow() {
    return headRow;
  }

  public String getSeparatorRow() {
    return separatorRow;
  }

  public String generate(String[] data) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(COLUMN_SEPARATOR);
    for (int i = 0; i < settings.getColumns().size(); i++) {
      stringBuilder.append(generateColumn(data[i], settings.getColumns().get(i).getWidth()));
    }
    return stringBuilder.toString();
  }

  private StringBuilder generateColumn(String s, int columnWidth) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(WORD_SEPARATOR);
    stringBuilder.append(s);
    int diffSize = columnWidth - s.length();
    if (diffSize > 0) {
      stringBuilder.append(StringUtils.repeat(" ", diffSize));
    }
    stringBuilder.append(WORD_SEPARATOR).append(COLUMN_SEPARATOR);
    return stringBuilder;
  }

  private void generateRowDelimiter() {
    separatorRow = StringUtils.repeat(ROW_SEPARATOR, settings.getPage().getWidth());
  }

  private void generateHead(List<Column> columns) {
    if (columns == null) {
      log.info("Failed headrow creation, check columns in xml file");
      headRow = "";
      return;
    }
    String[] columnsTitlesStringArray = new String[columns.size()];
    int i = 0;
    for (Column column : columns) {
      columnsTitlesStringArray[i] = column.getTitle();
      i++;
    }
    headRow = generate(columnsTitlesStringArray);
  }
}
