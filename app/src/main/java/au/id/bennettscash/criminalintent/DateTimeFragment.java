package au.id.bennettscash.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by chris on 5/03/15.
 */
public class DateTimeFragment extends DialogFragment {
    public static final String EXTRA_DATETIME = "au.id.bennettscash.criminalintent.datetime";

    public static final String DIALOG_DATE = "date";
    public static final String DIALOG_TIME = "time";
    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_TIME = 1;

    private Date mDate;

    private Button dateButton;
    private Button timeButton;

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_DATETIME, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    public static DateTimeFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATETIME, date);

        DateTimeFragment fragment = new DateTimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(EXTRA_DATETIME);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_datetime, null);

        dateButton = (Button)v.findViewById(R.id.datetime_date_button);
        timeButton = (Button)v.findViewById(R.id.datetime_time_button);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mDate);
                dialog.setTargetFragment(DateTimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mDate);
                dialog.setTargetFragment(DateTimeFragment.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("date and time")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    public void onActivityResult(int request, int result, Intent i) {
        if (result != Activity.RESULT_OK) return;

        Calendar oldDate = Calendar.getInstance();
        oldDate.setTime(mDate);

        if (request == REQUEST_DATE) {
            Date date = (Date) i.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(date);

            oldDate.set(Calendar.DAY_OF_MONTH, newDate.get(Calendar.DAY_OF_MONTH));
            oldDate.set(Calendar.MONTH, newDate.get(Calendar.MONTH));
            oldDate.set(Calendar.YEAR, newDate.get(Calendar.YEAR));
        } else if (request == REQUEST_TIME) {
            Date date = (Date) i.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(date);

            oldDate.set(Calendar.HOUR_OF_DAY, newDate.get(Calendar.HOUR_OF_DAY));
            oldDate.set(Calendar.MINUTE, newDate.get(Calendar.MINUTE));
        }

        mDate = oldDate.getTime();
    }
}
