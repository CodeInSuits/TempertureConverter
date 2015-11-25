package com.example.jace.temperatureconverter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Switch aSwitch;
    private TextView modeDisplay;
    private TextView dataDisplay;
    private RelativeLayout relativeLayout;
    private Button fetcher;
    private boolean toggleState = false;

    DecimalFormat round = new DecimalFormat("0.0");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.background);
        aSwitch = (Switch) findViewById(R.id.switch1);
        modeDisplay = (TextView) findViewById(R.id.textView2);
        dataDisplay = (TextView) findViewById(R.id.textView3);
        editText = (EditText) findViewById(R.id.editText);
        fetcher = (Button) findViewById(R.id.button);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    modeDisplay.setText("Display temperature in C");
                    toggleState = isChecked;
                }
                else{
                    modeDisplay.setText("Display temperature in F");
                    toggleState = isChecked;
                }
            }
        });
        fetcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                hideKeyboard();
                if(input.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter a temperature",Toast.LENGTH_SHORT).show();
                    dataDisplay.setText("Input not received!");
                }
                else if(toggleState){
                    double val = Double.parseDouble(input);
                    if(val<0.0){
                        relativeLayout.setBackgroundColor(Color.BLUE);
                    }
                    else if(val>=0.0&&val<40){
                        relativeLayout.setBackgroundColor(Color.GREEN);
                    }
                    else{
                        relativeLayout.setBackgroundColor(Color.RED);
                    }
                    dataDisplay.setText(String.valueOf(round.format(celsiusConverter(val))));
                }
                else{
                    double val = Double.parseDouble(input);
                    dataDisplay.setText(String.valueOf(round.format(fahremheitConverter(val))));
                    if(val<0.0){
                        relativeLayout.setBackgroundColor(Color.BLUE);
                    }
                    else if(val>=0.0&&val<40){
                        relativeLayout.setBackgroundColor(Color.GREEN);
                    }
                    else{
                        relativeLayout.setBackgroundColor(Color.RED);
                    }

                }

            }
        });
    }
    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void showKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static double celsiusConverter(double val){
        return (val * 9/5) + 32;
    }

    public static double fahremheitConverter(double val){
        return (val-32) * (5/9);
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
