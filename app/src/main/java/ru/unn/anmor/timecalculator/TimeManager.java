package ru.unn.anmor.timecalculator;

import android.databinding.*;

/**
 * Created by Hryasch on 21.09.2017.
 */

public class TimeManager extends BaseObservable
{
    private int mStartHour = 0;
    private int mStartMinutes = 0;
    private int mStartSeconds = 0;

    private int mFinishHour = 23;
    private int mFinishMinutes = 59;
    private int mFinishSeconds = 59;

    public void SetHours(int startHour, int endHour)
    {
        mStartHour = startHour;
        mFinishHour = endHour;
        notifyPropertyChanged(BR.timeDifference);
    }

    public void SetMins(int startMin, int endMin)
    {
        mStartMinutes = startMin;
        mFinishMinutes = endMin;
        notifyPropertyChanged(BR.timeDifference);
    }

    public void SetSec(int startSec, int endSec)
    {
        mStartSeconds = startSec;
        mFinishSeconds = endSec;
        notifyPropertyChanged(BR.timeDifference);
    }

    public void SetStartTime (int startSec, int startMin, int startHour)
    {
        mStartSeconds = startSec;
        mStartMinutes = startMin;
        mStartHour = startHour;
        notifyPropertyChanged(BR.timeDifference);
    }

    @Bindable
    public int getTimeDifference()
    {

        int stSeconds = mStartHour * 3600 + mStartMinutes * 60 + mStartSeconds;
        int finSeconds = mFinishHour * 3600 + mFinishMinutes * 60 + mFinishSeconds;

        return (finSeconds - stSeconds);
    }
}
