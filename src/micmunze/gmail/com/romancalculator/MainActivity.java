package micmunze.gmail.com.romancalculator;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main Activity of the program.
 * The Program is a calculator for arabian and roman numbers.
 *
 * @author Michael Munzert
 * @version 1.0, 05.08.2012
 */
public class MainActivity 
extends Activity 
implements View.OnClickListener, OnLongClickListener {
   private static final String TAG = "RomanCalculator";
   private boolean isArabian = true; // direction to calculate
   private TextView label1 = null; // describe input field
   private TextView label2 = null; // describe result field
   private TextView result = null; // result to show
   private EditText input = null; // Input number
   
   /**
    * @see android.app.Activity#onCreate(android.os.Bundle)
    */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.layout_main);
      
      label1 = (TextView)findViewById(R.id.t1);
      label2 = (TextView)findViewById(R.id.t2);
      result = (TextView)findViewById(R.id.calc_number);
      input = (EditText)findViewById(R.id.input_number);
      
      ImageButton mBtn = (ImageButton)findViewById(R.id.btn_calc);
      mBtn.setOnClickListener(this);
      mBtn.setOnLongClickListener(this);
      
      fillData(); // Fills the GridView
   }
   
   /**
    * Fills the data of the grid view with the arabian and roman base numbers.
    */
   private void fillData() {
      // arabian and roman numbers
      String[] numbers = {"1", "I", "5", "V", "10", "X", "50", "L", "100", "C",
                          "500", "D", "1000", "M"};
      ListArrayAdapter adapter = new ListArrayAdapter(this, numbers);
      GridView gv = (GridView)findViewById(R.id.list_numbers);
      gv.setAdapter(adapter);
   }
   
   /**
    * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
    */
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
   }
   
   /**
    * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
    */
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case R.id.menu_switch: // switch between the calculation direction
            isArabian = !isArabian;
            if (isArabian) { // arabian to roman
               label1.setText(R.string.t1);
               label2.setText(R.string.t2);
               input.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else { // roman to arabian
               label1.setText(R.string.t2);
               label2.setText(R.string.t1);
               input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            }
            input.setText("");
            result.setText("");
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }
   
   /**
    * @see android.view.View.OnClickListener#onClick(android.view.View)
    */
   @Override
   public void onClick(View v) {
      if (isArabian) { // arabian to roman
         String a = input.getText().toString();
         int arabian = -1;
         try {
            arabian = Integer.parseInt(a);
            String roman = getRoman(arabian);
            result.setText(roman);
         } catch (NumberFormatException e) { // wrong number
            Toast t = Toast.makeText(this, e.getLocalizedMessage(), 
                                     Toast.LENGTH_LONG);
            t.setGravity(Gravity.TOP, 20, 20);
            t.show();
         } catch (IllegalArgumentException e) { // error calculation
            Toast t = Toast.makeText(this, e.getLocalizedMessage(), 
                                     Toast.LENGTH_LONG);
            t.setGravity(Gravity.TOP, 20, 20);
            t.show();
         }
      } else { // roman to arabian
         String roman = input.getText().toString();
         try {
            int arabian = getArabian(roman);
            String a = "" + arabian;
            result.setText(a);
         } catch (IllegalArgumentException e) { // invalid roman number
            Toast t = Toast.makeText(this, e.getLocalizedMessage(), 
                                     Toast.LENGTH_LONG);
            t.setGravity(Gravity.TOP, 20, 20);
            t.show();
         }
      }
   }
   
   /**
    * Returns the arabian number of the roman number.
    * 
    * @param  r
    *         roman number.
    * @return arabian number.
    * @throws IllegalArgumentException
    *         if the roman number is not valid.
    */
   private int getArabian(String r) throws IllegalArgumentException {
      int j;
      char[] romaneZiffern = {'i', 'v', 'x', 'l', 'c', 'd', 'm'};
      char[] romaneZiffernUP = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
      int[] ziffernWert = {1, 5, 10, 50, 100, 500, 1000};
      int[] hilfe = new int[10];
      
      if (r.charAt(0) == '0') return 0;
      
      int stellen;
      for (stellen = 0; stellen < r.length(); stellen++) {
         for (j = 0; j < 7; j++)
            if (r.charAt(stellen) == romaneZiffern[j] ||
            r.charAt(stellen) == romaneZiffernUP[j]) {
               hilfe[stellen] = ziffernWert[j];
               break;
            }
         if (j == 7) {
            String msg = getResources().getString(R.string.msg_roman);
            throw new IllegalArgumentException(msg + r);
         }
      }
      
      for (j=1; j < stellen; j++)
         if (hilfe[j] > hilfe[j-1]) {
            hilfe[j] -= hilfe[j-1];
            hilfe[j-1] = 0;
         }
      
      int arabian = 0;
      for (j=0; j < stellen; j++) arabian += hilfe[j];
      
      return arabian;
   }
   
   /**
    * Returns the roman number of an arabian number.
    * 
    * @param  a
    *         arabian number.
    * @return roman number.
    * @throws IllegalArgumentException
    *         if the number is smaller or equal than 0 or greater or equal than
    *         4000.
    */
   private String getRoman(int a) throws IllegalArgumentException {
      String[] roemischeZiffern = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", 
                                   "X", "IX", "V", "IV", "I"};
      int[] ziffernWert = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 
                           1};
      int zahl, i = 0;
      String roman = "";
      
      if (a <= 0 || a >= 4000) {
         String msg = getResources().getString(R.string.msg_arabian);
         throw new IllegalArgumentException(msg);
      }
      
      zahl = a;
      while (zahl > 0) {
         if (zahl/ziffernWert[i] >= 1) {
            roman += roemischeZiffern[i];
            zahl -= ziffernWert[i];
         } else {
            i++;
         }
      }
      
      return roman;
   }
   
   /**
    * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
    */
   @Override
   public boolean onLongClick(View v) {
      switch (v.getId()) {
         case R.id.btn_calc:
            // Show Tooltip for button
            Rect r = new Rect();
            v.getGlobalVisibleRect(r);
            int x = r.left;
            int y = r.top;
            Toast t = Toast.makeText(this, R.string.btn_calc_txt, 
                                     Toast.LENGTH_LONG);
            t.setGravity(Gravity.TOP, x, y);
            t.show();
            Log.d(TAG, "x = " + x + ", y = " + y);
            return true;
      }
      return false;
   }
}
