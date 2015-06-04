package de.micmun.android.romancalculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Main Activity of the program.
 * The Program is a calculator for arabian and roman numbers.
 *
 * @author Michael Munzert
 * @version 1.0, 05.08.2012
 */
public class MainActivity extends ActionBarActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   }
}
