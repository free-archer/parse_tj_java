package org.parsetj.parsetj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {
    long start = System.currentTimeMillis();

    private String logFile = "";
    private String dbFile = "";
    private ArrayList<String> mainArray;
    private Map<String, String> lparams = new HashMap<>();

    Parse(String logFile, String dbFile ) {
        this.logFile = logFile;
        this.dbFile = dbFile;
    }

    private String getFileName(String path) {
//        /home/archer/Programm/Java/ParseTJ/target/logs/21103114.log
        String strPatern = "/(\\d+).log";
        Pattern pattern = Pattern.compile(strPatern);
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    private void getMainArray() throws ParseException {
// 1. Создаем массив строк склеивая их по регулярному выражению
        mainArray = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(logFile));
            String rePatern = "\\d{2}:\\d{2}.\\d{6}-";
            Pattern pattern = Pattern.compile(rePatern);
            String str_log = "";

            String line = reader.readLine();

            while (line != null) {
                line = line.strip();
                System.out.println(line);

                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    if (!str_log.isEmpty()) {
                        mainArray.add(line);
                    }

                    str_log = line;
                } else {
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

    private void getParams(String elem, String strPatern) {
        Pattern pattern = Pattern.compile(strPatern);
        Matcher matcher = pattern.matcher(elem);
        while (matcher.find()) {
            String paramName = matcher.group(1);
            String paramValue = matcher.group(2);

            lparams.put(paramName, paramValue);
        }
    }

    public void doParse() throws ParseException {
// 1. Создаем массив строк склеивая их по регулярному выражению

        getMainArray();

        System.out.println("Длинна массива: " + mainArray.size());

// 2. Получаем список параметров
        String logFileName = getFileName(logFile);
        System.out.println("logFileName "+logFileName);
        String strPatern = "(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
        Pattern pattern = Pattern.compile(strPatern);
        Matcher matcher = pattern.matcher(logFileName);
        String year = "";
        String month = "";
        String day = "";
        String hour = "";
        if (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
            year = "20" + matcher.group(1);
            month = matcher.group(2);
            day = matcher.group(3);
            hour = matcher.group(4);
        }



        for (String elem : mainArray) {
            strPatern = "(\\d{2}):(\\d{2}).(\\d{6})";
            pattern = Pattern.compile(strPatern);
            matcher = pattern.matcher(elem);

            String sourseTime = "";
            String minute = "";
            String second = "";
            String msec = "";
            if (matcher.find()) {
                sourseTime = matcher.group(1);
                minute = matcher.group(1);
                second = matcher.group(2);
                msec = matcher.group(3);
            }
            String format = "yyyy-MM-dd HH:mm:ss.SSSSS";
            String stringDate = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + "." + msec;
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.parse(stringDate);

            lparams.put("time", stringDate);

            getParams(elem, ",(\\w+)='([^']+)");

        }


        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Прошло времени, мс: " + elapsed);
    }
}
