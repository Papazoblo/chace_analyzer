package alex.studio.csvsearcher.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewUtils {

    public static boolean isAnyVisible(View... views) {
        for(View view : views) {
            if(isVisible(view)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public static boolean toGone(View view) {
        view.setVisibility(View.GONE);
        return false;
    }

    public static boolean toVisible(View view) {
        view.setVisibility(View.VISIBLE);
        return true;
    }

    public static boolean changeVisible(View view) {
        if(isVisible(view)) {
            return toGone(view);
        } else {
            return toVisible(view);
        }
    }

    public static boolean isEmpty(TextView editText) {
        return editText.getText().toString().isEmpty();
    }

    public static boolean isNotEmpty(TextView... editTexts) {
        for (TextView editText : editTexts) {
            if(isEmpty(editText)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(TextView... editTexts) {
        for (TextView editText : editTexts) {
            if(!isEmpty(editText)) {
                return false;
            }
        }
        return true;
    }

    public static String getTextFrom(TextView text) {
        return text.getText().toString();
    }

    public static boolean isEmpty(ImageView... imageViews) {
        for (ImageView imageView : imageViews) {
            if(imageView.getDrawable() != null) {
                return false;
            }
        }
        return true;
    }
}
