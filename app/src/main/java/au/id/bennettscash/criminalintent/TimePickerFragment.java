package au.id.bennettscash.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by chris on 27/02/15.
 */
public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_TIME = "au.id.bennettscash.criminalintent.time";

    private Date mDate;
    private TimePicker mPicker;

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date)getArguments().getSerializable(EXTRA_TIME);

        // Calendar
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        mPicker = (TimePicker)v.findViewById(R.id.dialog_time_picker);
        mPicker.setCurrentHour(c.get(c.HOUR_OF_DAY));
        mPicker.setCurrentMinute(c.get(c.MINUTE));
        mPicker.setIs24HourView(true);
        mPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.setTime(mDate);
                c.set(c.HOUR_OF_DAY, hourOfDay);
                c.set(c.MINUTE, minute);
                mDate = c.getTime();

                getArguments().putSerializable(EXTRA_TIME, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Time of crime")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Change mDate here because onTimeChanged is never called
                                Calendar c = Calendar.getInstance();
                                c.setTime(mDate);
                                c.set(c.HOUR_OF_DAY, mPicker.getCurrentHour());
                                c.set(c.MINUTE, mPicker.getCurrentMinute());
                                mDate = c.getTime();
                                getArguments().putSerializable(EXTRA_TIME, mDate);
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }
}
