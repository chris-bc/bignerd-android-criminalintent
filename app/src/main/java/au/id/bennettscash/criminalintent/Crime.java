package au.id.bennettscash.criminalintent;

import android.text.format.DateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by chris on 31/01/15.
 */
public class Crime {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_SUSPECT = "suspect";
    private static final String JSON_PHONE = "phone";

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private Photo mPhoto;
    private String mSuspect;
    private String mSuspectPhone;

    @Override
    public String toString() {
        return getTitle();
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public Photo getPhoto() { return mPhoto; }

    public void setPhoto(Photo p) { mPhoto = p; }

    public Crime() {
        // Generate UUID
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_TITLE))
            mTitle = json.getString(JSON_TITLE);
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getLong(JSON_DATE));
        if (json.has(JSON_PHOTO)) {
            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
        }
        if (json.has(JSON_SUSPECT)) {
            mSuspect = json.getString(JSON_SUSPECT);
        }
        if (json.has(JSON_PHONE)) {
            mSuspectPhone = json.getString(JSON_PHONE);
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate.getTime());
        if (mPhoto != null) {
            json.put(JSON_PHOTO, mPhoto.toJSON());
        }
        json.put(JSON_SUSPECT, mSuspect);
        json.put(JSON_PHONE, mSuspectPhone);
        return json;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSuspect() { return mSuspect; }

    public void setSuspect (String s) { mSuspect = s; }

    public void setSuspectPhone (String s) { mSuspectPhone = s;}

    public String getSuspectPhone() { return mSuspectPhone; }

    public UUID getId() {
        return mId;
    }

    public CharSequence getDateString() {
        return DateFormat.format("EEEE, MMM d, yyyy", mDate);
    }

    public CharSequence getTimeString() {
        return DateFormat.format("h:m a", mDate);
    }

    public CharSequence getDateTimeString() { return getDateString() + " " + getTimeString(); }

}
