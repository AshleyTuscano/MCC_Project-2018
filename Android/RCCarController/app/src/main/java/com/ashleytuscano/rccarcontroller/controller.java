package com.ashleytuscano.rccarcontroller;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class controller extends AppCompatActivity {

    Button leftF, leftO, leftB, rightF, rightO, rightB, btnDis;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the Controller
        setContentView(R.layout.activity_controller);

        //call the widgtes
        leftF = (Button)findViewById(R.id.leftF);
        leftO = (Button)findViewById(R.id.leftO);
        leftB = (Button)findViewById(R.id.leftB);
        rightF = (Button)findViewById(R.id.rightF);
        rightO = (Button)findViewById(R.id.rightO);
        rightB = (Button)findViewById(R.id.rightB);
        btnDis = (Button)findViewById(R.id.dis);

        new ConnectBT().execute(); //Call the class to connect

        //commands to be sent to bluetooth
        leftF.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v)
            {
                leftMotorForward();        //method to move left motor forward
            }
        });

        leftO.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v)
            {
                leftMotorOff();       //method to turn off left motor
            }
        });

        leftB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v)
            {
                leftMotorBackward();       //method to move left motor backward
            }
        });

        rightF.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v)
            {
                rightMotorForward();       //method to move right motor forward
            }
        });

        rightO.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v)
            {
                rightMotorOff();       //method to turn off right motor
            }
        });

        rightB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v)
            {
                rightMotorBackward();       //method to move right motor backward
            }
        });



        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });

    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void leftMotorForward()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("LF".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void leftMotorOff()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("LO".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void leftMotorBackward()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("LB".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void rightMotorForward()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("RF".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void rightMotorOff()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("RO".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void rightMotorBackward()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("RB".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
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

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(controller.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
        @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

}
