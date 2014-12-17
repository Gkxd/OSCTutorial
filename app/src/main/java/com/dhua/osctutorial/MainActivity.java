package com.dhua.osctutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.net.*;
import java.util.*;

import com.illposed.osc.*;

public class MainActivity extends Activity {

  private Thread oscThread = new Thread() {
    private String myIP = "12.34.56.78";
    private int myPort = 12345;

    private OSCPortOut oscPortOut;

    @Override
    public void run() {
      // Connect to some IP address and port
      InetAddress address;
      try {
        address = InetAddress.getByName(myIP);
        oscPortOut = new OSCPortOut(address, myPort);
      } catch(UnknownHostException e) {
        // Error handling when your IP isn't found
        return;
      } catch(Exception e) {
        // Error handling for any other errors
        return;
      }


      while (true) {
        if (oscPortOut != null) {
          // Create the message
          Object[] thingsToSend = new Object[3];
          thingsToSend[0] = "Hello World";
          thingsToSend[1] = 12345;
          thingsToSend[2] = 1.2345;

          OSCMessage message = new OSCMessage(myIP, Arrays.asList(thingsToSend));

          try {
            // Send the message
            oscPortOut.send(message);

            // Pause for half a second
            sleep(500);
          } catch (Exception e) {
            // Error handling for some error
          }
        }
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    oscThread.start();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
