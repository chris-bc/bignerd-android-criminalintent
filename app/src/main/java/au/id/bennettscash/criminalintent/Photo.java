package au.id.bennettscash.criminalintent;

import android.media.ExifInterface;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by chris on 10/06/15.
 */
public class Photo {
    private static final String JSON_FILENAME = "filename";

    private String mFilename;
    private Boolean isLandscape = null;

    /** Create a Photo representing an existing file on disk */
    public Photo(String filename) {
        mFilename = filename;
    }

    public Photo(JSONObject json) throws JSONException {
        mFilename = json.getString(JSON_FILENAME);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME, mFilename);
        return json;
    }

    public String getFilename() {
        return mFilename;
    }

    public Boolean isLandscape() {
        if (isLandscape == null) {

            // Initialise it
            try {
                ExifInterface exif = new ExifInterface(mFilename);
                int o = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                Log.d("TEST", "Orientation is " + o);
                isLandscape = (o == ExifInterface.ORIENTATION_NORMAL);
            } catch (IOException e) {
                Log.e("PHOTO", "Unable to get EXIF info: " + e.toString());
            }
        }

        return isLandscape;
    }
}
