package fpl.timers.reu;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class AndroidTimerTestActivity extends Activity {
    /** Called when the activity is first created. */
	
	Timer timer1 = new Timer();
	Timer timer2 = new Timer();
	
	
	private final Handler handler1 = new Handler();
	private final Handler handler2 = new Handler();
	
	private boolean activeAccel = false;
	private boolean on = false;
	
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);       
       
        checkTimers();
        
    }
    
    
    	
    private void checkTimers() {
		// TODO Auto-generated method stub
		String str;
    	if(!on)
    	{
    		timer2.cancel();
    		str = new Boolean(on).toString();
    		Log.d("On Value",str);
    		Timer timer1 = new Timer();
    		timer1.schedule(firsttask,3000,3000);
    		on = true;
    		
    	
    	}else{
    		
    		timer1.cancel();
    		str = new Boolean(on).toString();
    		Log.d("On Value",str);
    		Timer timer2 = new Timer();
    		timer2.schedule(secondtask, 5000,5000);
    		on = false;
    		
    	}
	}



	private TimerTask firsttask = new TimerTask(){

    	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
						
			handler1.post(new Runnable() {
				
				public void run() {
					
					Log.d("Location", "firsttask");
					
					//back on system thread
					if (activeAccel == false) {

						Log.d("Satus", "Accelerometer is inactive - Turn On");

						activeAccel=true;					

					}//end if
					checkTimers();
				}//end of internal run
			
				
			});//end of handler.post
			
					
			
		}
    	
    };//end of firsttask
    private TimerTask secondtask = new TimerTask() {
    	
    	
		@Override
		public void run() {

  			
  		
  		
			handler2.post(new Runnable() {
				
				public void run() {
					
					Log.e("Location", "secondtask");
					
					//back on system thread
					if (activeAccel== true) {

						Log.e("Status", "Accelerometer is active - Turn Off");
						// battery();
						
						activeAccel=false;

					}//end if
					
					checkTimers();
					
				}//end of internal run
				
			});//end of handler.post
			
		}//end of run
		
		
    };//end of secondtask
    
}