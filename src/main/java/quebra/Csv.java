package quebra;

import java.io.FileReader;

import com.opencsv.CSVReader;

public class Csv {
    public static void lerHistorico(String file) {
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }

            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
