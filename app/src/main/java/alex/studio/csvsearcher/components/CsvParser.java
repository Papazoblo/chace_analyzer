package alex.studio.csvsearcher.components;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import alex.studio.csvsearcher.dto.CardSet;

public class CsvParser {

    public static ArrayList<CardSet> read(InputStream inputStream){
        ArrayList<CardSet> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            long i = 0;
            while ((csvLine = reader.readLine()) != null) {
                if(i == 0) {
                    i++;
                    continue;
                }
                try {
                    resultList.add(new CardSet(csvLine.split(",")));
                } catch (Exception ex) {

                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultList;
    }
}
