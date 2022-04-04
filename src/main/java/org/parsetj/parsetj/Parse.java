package org.parsetj.parsetj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {
    private String logFile = "";
    private String dbFile = "";
    private ArrayList<String> mainArray;

    Parse(String logFile, String dbFile ) {
        this.logFile = logFile;
        this.dbFile = dbFile;
    }

    public void doParse() {
        mainArray = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(logFile));
            String rePatern = "\\d{2}:\\d{2}.\\d{6}-";
            String str_log = "";

            String line = reader.readLine();

            while (line != null) {
                line = line.strip();
                System.out.println(line);

                Pattern pattern = Pattern.compile(rePatern);
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    if (!str_log.isEmpty()) {
                        mainArray.add(line);
                    }

                    str_log = line;
                }
                else {
                    str_log = String.join("-#-", line);
//                    str_log += "-#-" + line;
                }

                    line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
