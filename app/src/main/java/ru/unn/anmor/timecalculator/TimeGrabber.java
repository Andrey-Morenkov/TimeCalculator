package ru.unn.anmor.timecalculator;
import android.os.Message;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static ru.unn.anmor.timecalculator.MainActivity.*;

/**
 * Created by Hryasch on 21.09.2017.
 */

public class TimeGrabber extends Thread
{
    private TimeManager mTimeManager;
    private boolean isExit = false;
    private Message msg;

    TimeGrabber (TimeManager _parentTimeManager)
    {
        mTimeManager = _parentTimeManager;
    }

    public void safeStop()
    {
        isExit = true;
    }

    @Override
    public void run()
    {
        msg = new Message();
        while (!isExit)
        {
            Calendar rightNow = Calendar.getInstance();

            int nowSec  = rightNow.get(Calendar.SECOND);
            int nowMin  = rightNow.get(Calendar.MINUTE);
            int nowHour = rightNow.get(Calendar.HOUR);

            Log.d ("!!!!", " sec = " + nowSec + " min = " + nowMin + " sec= " + nowSec);

            msg = mainHandler.obtainMessage(SEC_CHANGED, nowSec, 0);

            Log.d ("!!!!", "what = " + msg.what + " arg1= " + msg.arg1 + " arg2= " + msg.arg2);

            mainHandler.sendMessage(msg);

            msg = mainHandler.obtainMessage(MIN_CHANGED, nowMin, 0);
            mainHandler.sendMessage(msg);

            msg = mainHandler.obtainMessage(HOUR_CHANGED, nowHour, 0);
            mainHandler.sendMessage(msg);

            mTimeManager.SetStartTime(nowSec, nowMin, nowHour);

            try
            {
                sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        isExit = false;
    }
}
