package au.id.bennettscash.criminalintent;

import java.util.UUID;

/**
 * Created by chris on 31/01/15.
 */
public class Crime {
    private UUID mId;
    private String mTitle;

    public Crime() {
        // Generate UUID
        mId = UUID.randomUUID();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

}
