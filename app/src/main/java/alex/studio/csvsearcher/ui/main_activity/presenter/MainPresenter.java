package alex.studio.csvsearcher.ui.main_activity.presenter;

import static alex.studio.csvsearcher.utils.DateUtils.toDate;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alex.studio.csvsearcher.R;
import alex.studio.csvsearcher.components.CsvComponent;
import alex.studio.csvsearcher.components.StorageManager;
import alex.studio.csvsearcher.dto.CardSet;
import alex.studio.csvsearcher.enums.Properties;
import alex.studio.csvsearcher.functions.Consumer;
import alex.studio.csvsearcher.ui.main_activity.Main;

public abstract class MainPresenter<A extends AppCompatActivity> implements Main.Presenter {

    protected Main.View view;

    private StorageManager storageManager;
    protected ArrayList<CardSet> listCard = new ArrayList<>();

    @Override
    public void setView(Main.View view) {
        this.view = view;
        this.storageManager = new StorageManager(view.getContext());
    }

    @Override
    public void launchSearch() {

        if (!view.isCardValid()) {
            return;
        }

        view.changeVisibleBlockWait(true);
        initReadFile(obj -> launchAlgorithm());
    }

    @Override
    public void initReadFile(Consumer<CardSet> afterEnd) {
        new Thread(() -> {
            if (!listCard.isEmpty()) {
                listCard.clear();
            }
            CsvComponent csvComponent = new CsvComponent(view.getContext());
            try {
                listCard = csvComponent.start(storageManager.read(Properties.CSV_FOLDER));
                if (listCard == null || listCard.isEmpty()) {
                    listCard = new ArrayList<>();
                    Context context = view.getContext();
                    ((A) context).runOnUiThread(() -> Toast.makeText(context, context.getResources()
                            .getString(R.string.csv_not_found), Toast.LENGTH_SHORT)
                            .show());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (afterEnd != null) {
                afterEnd.accept(listCard.get(0));
            }
        }).start();
    }

    protected List<CardSet> getWorkList(int countRowCards) {
        Integer count = view.getActiveCountGames();
        List<CardSet> workListCard;

        if (count != null) {
            if (count >= listCard.size() || count <= countRowCards) {
                workListCard = listCard;
            } else {
                workListCard = listCard.subList(0, count);
            }
        } else {
            workListCard = new ArrayList<>(listCard);
        }
        return workListCard;
    }

    protected static boolean equalsDates(String[] datesEnter, String[] datesCard) {

        for (String date : datesCard) {
            if (!equalsDate(datesEnter, date)) {
                return false;
            }
        }
        return true;
    }

    private static boolean equalsDate(String[] datesEnter, String date) {

        Date curDate = toDate(date);

        if (!datesEnter[0].isEmpty() && !datesEnter[1].isEmpty()) {
            return toDate(datesEnter[0]).getTime() <= curDate.getTime() &&
                    toDate(datesEnter[1]).getTime() >= curDate.getTime();
        } else if (datesEnter[0].isEmpty()) {
            return toDate(datesEnter[1]).getTime() >= curDate.getTime();
        } else {
            return toDate(datesEnter[0]).getTime() <= curDate.getTime();
        }
    }
}
