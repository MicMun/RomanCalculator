/**
 * TwoColArrayAdapter.java
 *
 * Copyright 2012 by Michael Munzert
 */
package micmunze.gmail.com.romancalculator;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * TwoColArrayAdapter ist ein Adapter f&uuml;r zwei Arrays in zwei Spalten.
 * Man kann zwei Arrays &uuml;bergeben, diese werden in zwei TextViews 
 * angezeigt.
 *
 * @author Michael Munzert
 * @version 1.0, 07.08.2012
 */
public class ListArrayAdapter
extends BaseAdapter
{
   private String[] list; // Listen mit Werten
   private Context mContext; // Context
   
   public ListArrayAdapter(Context ctx, String[] l) {
      super();
      mContext = ctx;
      list =l;
   }
   
   /**
    * @see android.widget.Adapter#getCount()
    */
   @Override
   public int getCount() {
      return list.length;
   }
   
   /**
    * @see android.widget.Adapter#getItem(int)
    */
   @Override
   public Object getItem(int arg0) {
      return null;
   }
   
   /**
    * @see android.widget.Adapter#getItemId(int)
    */
   @Override
   public long getItemId(int arg0) {
      return 0;
   }
   
   /**
    * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
    */
   @Override
   public View getView(int arg0, View arg1, ViewGroup arg2) {
      TextView tv;
      
      if (arg1 == null) {
         tv = new TextView(mContext);
      } else {
         tv = (TextView)arg1;
      }
      
      tv.setText(list[arg0]);
      tv.setGravity(Gravity.CENTER_HORIZONTAL);
      tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
      
      return tv;
   }
   
}
