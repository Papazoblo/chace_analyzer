package alex.studio.csvsearcher.components;

import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.dto.CardSet;

import static alex.studio.csvsearcher.components.CsvParser.read;

public class CsvComponent {

    private DirectoryReader directoryReader;

    public CsvComponent(Context context) {
        directoryReader = new DirectoryReader(context);
    }

    public ArrayList<CardSet> start(String dir) throws FileNotFoundException {
        InputStream inputStream = directoryReader.readDirectory(dir);

        if(inputStream != null) {
            return read(inputStream);
        } else {
            return new ArrayList<>();
        }
    }
}
