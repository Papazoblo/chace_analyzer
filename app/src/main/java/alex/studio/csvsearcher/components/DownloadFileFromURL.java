package alex.studio.csvsearcher.components;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import alex.studio.csvsearcher.ui.StartActivity;

public class DownloadFileFromURL extends AsyncTask<String, String, String> {

    private StartActivity context;
    private String urlCsvFolder;

    public DownloadFileFromURL(StartActivity context, String urlFolder) {
        this.context = context;
        this.urlCsvFolder = urlFolder;
    }

    /**
     * Before starting background thread Show Progress Bar Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        context.showDialog();
    }

    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {

            File file = new File(urlCsvFolder);
            if (!file.exists()) {
                file.mkdirs();
            }

            URL url = new URL(f_url[0]);
            URLConnection connection = url.openConnection();
            connection.setReadTimeout(60000);
            connection.setConnectTimeout(30000);
            connection.connect();

            int lengthOfFile = connection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            OutputStream output = new FileOutputStream(urlCsvFolder + "/Chance.csv");

            byte[] data = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lengthOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    /**
     * Updating progress bar
     * */
    protected void onProgressUpdate(String... progress) {

    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(String file_url) {
        context.hideDialog();
    }

}
