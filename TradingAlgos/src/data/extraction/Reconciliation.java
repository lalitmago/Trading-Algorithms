package data.extraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Reconciliation {

    private static String FIELD_SEPARATOR = ",";

    private static String fileName = "data/MIS-List.txt";
    private static String filenameWithCodes = "data/NSE-codes.csv";
    private static String filenameOutput = "data/NSE-MIS-List.txt";

    public static void main(String[] args) {

        String line;

        Set<String> stockSymbols = new HashSet<>();
        Map<String, String> stockSymbolsWithCodes = new HashMap<>();

        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;

        try {
            FileReader fileReader = new FileReader(new File(fileName));
            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                stockSymbols.add(line);
            }

            fileReader = new FileReader(new File(filenameWithCodes));
            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                stockSymbolsWithCodes.put(line.split(FIELD_SEPARATOR)[0].split("/")[1], line.split(FIELD_SEPARATOR)[0]);
            }

            FileWriter fileWriter = new FileWriter(new File(filenameOutput));
            printWriter = new PrintWriter(fileWriter);

            for (String eachStockSymbol : stockSymbols) {
                printWriter.println(stockSymbolsWithCodes.get(eachStockSymbol));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            if (printWriter != null)
                printWriter.close();

        }

    }
}
