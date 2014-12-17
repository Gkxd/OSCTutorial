package com.dhua.osctutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.net.*;
import java.util.*;

import com.illposed.osc.*;

public class MainActivity extends Activity {

  /* These two variables hold the IP address and port number.
   * You should change them to the appropriate address and port.
   */
  private String myIP = "12.34.56.78";
  private int myPort = 12345;

  // This is used to send messages
  private OSCPortOut oscPortOut;

  // This thread will contain all the code that pertains to OSC
  private Thread oscThread = new Thread() {
    @Override
    public void run() {
      /* The first part of the run() method initializes the OSCPortOut for sending messages.
       *
       * For more advanced apps, where you want to change the address during runtime, you will want
       * to have this section in a different thread, but since we won't be changing addresses here,
       * we only have to initialize the address once.
       */

      try {
        // Connect to some IP address and port
        oscPortOut = new OSCPortOut(InetAddress.getByName(myIP), myPort);
      } catch(UnknownHostException e) {
        // Error handling when your IP isn't found
        return;
      } catch(Exception e) {
        // Error handling for any other errors
        return;
      }


      /* The second part of the run() method loops infinitely and sends messages every 500
       * milliseconds.
       */
      while (true) {
        if (oscPortOut != null) {
          // Creating the message
          Object[] thingsToSend = new Object[3];
          thingsToSend[0] = "Hello World";
          thingsToSend[1] = 12345;
          thingsToSend[2] = 1.2345;

          /* The version of JavaOSC from the Maven Repository is slightly different from the one
           * from the download link on the main website at the time of writing this tutorial.
           *
           * The Maven Repository version (used here), takes a Collection, which is why we need
           * Arrays.asList(thingsToSend).
           *
           * If you're using the downloadable version for some reason, you should switch the
           * commented and uncommented lines for message below
           */
          OSCMessage message = new OSCMessage(myIP, Arrays.asList(thingsToSend));
          // OSCMessage message = new OSCMessage(myIP, thingsToSend);


          /* NOTE: Since this version of JavaOSC uses Collections, we can actually use ArrayLists,
           * or any other class that implements the Collection interface. The following code is
           * valid for this version.
           *
           * The benefit of using an ArrayList is that you don't have to know how much information
           * you are sending ahead of time. You can add things to the end of an ArrayList, but not
           * to an Array.
           *
           * If you want to use this code with the downloadable version, you should switch the
           * commented and uncommented lines for message2
           */
          ArrayList<Object> moreThingsToSend = new ArrayList<Object>();
          moreThingsToSend.add("Hello World2");
          moreThingsToSend.add(123456);
          moreThingsToSend.add(12.345);

          OSCMessage message2 = new OSCMessage(myIP, moreThingsToSend);
          //OSCMessage message2 = new OSCMessage(myIP, moreThingsToSend.toArray());

          try {
            // Send the messages
            oscPortOut.send(message);
            oscPortOut.send(message2);

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

    // Start the thread that sends messages
    oscThread.start();
  }

  // Any code below this line was generated

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
