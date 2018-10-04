package generators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import settings.Settings;

public class ReportGenerator {

  private static final Logger log = Logger.getLogger(ReportGenerator.class);
  private static final String NEW_LINE = "\r\n";

  private Settings settings;
  private List<String[]> inputData;
  private List<String> resultReport = new ArrayList<>();

  public ReportGenerator(Settings settings, List<String[]> inputData) {
    Validate.notNull(settings, "xml hasn't settings");
    Validate.notNull(inputData, "tsv file is empty");
    this.settings = settings;
    this.inputData = inputData;
  }

  public void generate(String nameReportFile) {
    PageGenerator pageGenerator = new PageGenerator(settings);
    resultReport = pageGenerator.generate(inputData);
    String fileName = nameReportFile;

    try {

      File fileDir = new File(fileName);

      Writer out = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(fileDir), "UTF-16"));

      for (String line : resultReport) {
        out.append(line).append(NEW_LINE);
      }

      out.flush();
      log.info("Report \"" + fileName + "\" created.");
      out.close();

    } catch (IOException e) {
      log.error("Exception writing file \"" + fileName + "\".", e);
    }

  }
}
