package generators;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import settings.Column;
import settings.Settings;

public class PageGenerator {

  private static final Logger log = Logger.getLogger(PageGenerator.class);

  private static final String PAGE_SEPARATOR = "~";
  private Settings settings;
  private RowGenerator rowGenerator;

  public PageGenerator(Settings settings) {
    this.settings = settings;
  }

  public List<String> generate(List<String[]> inputData) {
    List<String> resultPage = new ArrayList<>();
    rowGenerator = new RowGenerator(settings);

    resultPage.add(rowGenerator.getHeadRow());
    resultPage.add(rowGenerator.getSeparatorRow());
    try {
      for (String[] dataRow : inputData) {
        int i = 0;
        int height = 1;
        for (Column column : settings.getColumns()) {
          if (dataRow[i].length() / column.getWidth() >= 1) {
            if (height < dataRow[i].length() / column.getWidth()) {
              height = dataRow[i].length() / column.getWidth();
            }
            if (dataRow[i].length() % column.getWidth() > 0) {
              height++;
            }
          }
          i++;
        }

        for (int j = 0; j < height; j++) {
          String[] inner = new String[dataRow.length];
          for (int k = 0; k < dataRow.length; k++) {
            if (dataRow[k].length() < settings.getColumns().get(k).getWidth()) {
              if (j == 0) {
                inner[k] = dataRow[k];
              } else {
                inner[k] = "";
              }
            } else {
              int rowMultiplier = settings.getColumns().get(k).getWidth() * j;
              if (rowMultiplier >= dataRow[k].length()) {
                inner[k] = "";
              } else if (rowMultiplier + settings.getColumns().get(k).getWidth() - 1 >= dataRow[k]
                  .length()) {
                inner[k] = dataRow[k].substring(rowMultiplier);
              } else {
                inner[k] = dataRow[k].substring(rowMultiplier,
                    rowMultiplier + settings.getColumns().get(k).getWidth());
              }
            }
          }
          resultPage.add(rowGenerator.generate(inner));
          if (resultPage.size() % settings.getPage().getHeight() == 0) {
            resultPage.add(PAGE_SEPARATOR);
            resultPage.add(rowGenerator.getHeadRow());
            resultPage.add(rowGenerator.getSeparatorRow());
          } else if (j == height - 1) {
            resultPage.add(rowGenerator.getSeparatorRow());
          }
        }
      }
    } catch (Exception e) {
      log.error("Problems with settings from xml file", e);
    }
    return resultPage;
  }

}
