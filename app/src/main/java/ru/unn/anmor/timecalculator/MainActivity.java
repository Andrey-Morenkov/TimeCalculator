package ru.unn.anmor.timecalculator;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import ru.unn.anmor.timecalculator.*;
import ru.unn.anmor.timecalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    private TimeManager _Time = new TimeManager();
    private TimeGrabber mNowTimeBackground;

    public View.OnFocusChangeListener listener;
    public static Handler mainHandler;
    public static final int HOUR_CHANGED = 777;
    public static final int MIN_CHANGED = 888;
    public static final int SEC_CHANGED = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listener = new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                int sh = Integer.parseInt(((EditText) findViewById(R.id.tSHour)).getText().toString());
                int sm = Integer.parseInt(((EditText) findViewById(R.id.tSMins)).getText().toString());
                int ss = Integer.parseInt(((EditText) findViewById(R.id.tSSec)).getText().toString());

                int eh = Integer.parseInt(((EditText) findViewById(R.id.tFHours)).getText().toString());
                int em = Integer.parseInt(((EditText) findViewById(R.id.tFMins)).getText().toString());
                int es = Integer.parseInt(((EditText) findViewById(R.id.tFSec)).getText().toString());

                Log.d ("****!****", "sh = " + sh + " sm = " + sm + " ss = " + ss + " eh = " + eh + " em = " + em + " es = " + es);

                _Time.SetHours(sh, eh);
                _Time.SetMins(sm, em);
                _Time.SetSec(ss, es);
            }
        };

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setTime(_Time);

        findViewById(R.id.tSHour).setOnFocusChangeListener(listener);
        findViewById(R.id.tSMins).setOnFocusChangeListener(listener);
        findViewById(R.id.tSSec).setOnFocusChangeListener(listener);

        findViewById(R.id.tFHours).setOnFocusChangeListener(listener);
        findViewById(R.id.tFMins).setOnFocusChangeListener(listener);
        findViewById(R.id.tFSec).setOnFocusChangeListener(listener);

        mNowTimeBackground = new TimeGrabber(_Time);

        CheckBox useCurrTime = (CheckBox) findViewById(R.id.chUseCurrTime);
        useCurrTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    mNowTimeBackground.start();
                    findViewById(R.id.tSHour).setEnabled(false);
                    findViewById(R.id.tSMins).setEnabled(false);
                    findViewById(R.id.tSSec).setEnabled(false);
                }
                else
                {
                    mNowTimeBackground.safeStop();
                    findViewById(R.id.tSHour).setEnabled(true);
                    findViewById(R.id.tSMins).setEnabled(true);
                    findViewById(R.id.tSSec).setEnabled(true);
                }
            }
        });

        mainHandler = new Handler()
        {
            public void handleMessage(android.os.Message _msg)
            {
                Log.d("!!!!!!!!", "Arrived what = " + _msg.what + " arg1 = " + _msg.arg1 + " arg2 = " + _msg.arg2);
                switch (_msg.what)
                {
                    case HOUR_CHANGED:
                    {
                        ((EditText) findViewById(R.id.tSHour)).setText(String.valueOf(_msg.arg1));
                        break;
                    }
                    case MIN_CHANGED:
                    {
                        ((EditText) findViewById(R.id.tSMins)).setText(String.valueOf(_msg.arg1));
                        break;
                    }
                    case SEC_CHANGED:
                    {
                        ((EditText) findViewById(R.id.tSSec)).setText(String.valueOf(_msg.arg1));
                        break;
                    }

                }
            };
        };

    }


}
