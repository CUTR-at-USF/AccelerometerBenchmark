package fpl.timers.reu;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AndroidTimerTestActivity extends Activity {

  public static String TAG = "TimerTest";

  /** Called when the activity is first created. */

  Timer timer1 = new Timer();
  MyTimerTask1 firsttask;
  Timer timer2 = new Timer();
  MyTimerTask2 secondtask;

  private final Handler handler1 = new Handler();
  private final Handler handler2 = new Handler();

  private boolean activeAccel = false;
  private boolean on = false;

  Button startTimer;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    startTimer = (Button) findViewById(R.id.btnStartTimer);

    startTimer.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        // Executes when the button is clicked
        Log.d(TAG, "Clicked button");
        startTimer();
      }
    });
  }
  
  @Override
  protected void onDestroy() {
    //Cancel all timers
    firsttask.cancel();
    firsttask = null;
    timer1.purge();
    timer1.cancel();
    timer1=null;
    
    secondtask.cancel();
    secondtask = null;
    timer2.purge();
    timer2.cancel();
    timer2=null;
    
    super.onDestroy();
  }

  /**
   * Registers the first timer
   */
  private void startTimer() {
    firsttask = new MyTimerTask1();
    timer1.schedule(firsttask, 3000, 3000);
    Log.d(TAG, "On first run - scheduled timer1");
  }

  /**
   * This method is called from both timers to control the logic of turning off
   * one timer and turning on the other
   */
  private void checkTimers() {
    // TODO Auto-generated method stub
    if (on) {
      timer2.cancel();
      Log.d(TAG, "canceled timer2");
      Log.d(TAG, "On Value=" + on);
      // Scheduling wait time until the next timer triggers, which will turn the
      // accel. on
      timer1 = new Timer();
      firsttask = new MyTimerTask1();
      timer1.schedule(firsttask, 3000, 3000);
      Log.d(TAG, "scheduled timer1");
      on = false;
    } else {
      timer1.cancel();
      Log.d(TAG, "canceled timer1");
      Log.d(TAG, "On Value=" + on);
      // Scheduling on time until the next timer triggers, which will shut the
      // accel. off
      timer2 = new Timer();
      secondtask = new MyTimerTask2();
      timer2.schedule(secondtask, 5000, 5000);
      Log.d(TAG, "scheduled timer2");
      on = true;
    }
  }

  private class MyTimerTask1 extends TimerTask {

    @Override
    public void run() {
      // The first timer to trigger
      handler1.post(new Runnable() {
        public void run() {

          Log.d(TAG, "Running TimerTask1");

          // back on system thread
          if (activeAccel == false) {

            Log.d(TAG, "Accelerometer is inactive - Turn On");

            // TODO - accelerometer would be turned on here

            activeAccel = true;

          }// end if
          checkTimers();
        }// end of internal run
      });// end of handler.post
    }

  };// end of firsttask
  private class MyTimerTask2 extends TimerTask {

    @Override
    public void run() {
      handler2.post(new Runnable() {
        public void run() {

          Log.d(TAG, "Running TimerTask2");

          // back on system thread
          if (activeAccel == true) {

            Log.d(TAG, "Accelerometer is active - Turn Off");
            // battery();

            // TODO - accelerometer would be turned off here

            activeAccel = false;
          }// end if

          checkTimers();
        }// end of internal run
      });// end of handler.post
    }// end of run
    
  };// end of secondtask

}