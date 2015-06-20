package gov.co.acaciasmeta.adaptadores;

import gov.co.acaciasmeta.clases.DbAdapter;
import gov.co.acaciasmeta.directorioacacias.MainActivity.Adaptador;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
 
public class cursorAdapter extends CursorAdapter
{
 
   private DbAdapter dbAdapter = null ;
   Context context;
   public cursorAdapter(Context context, Cursor c)
   {
      super(context, c);
      dbAdapter = new DbAdapter(context);  
      dbAdapter.abrir();
   }
 
   @Override
   public void bindView(View view, Context context, Cursor cursor)
   {
	    
      TextView tv = (TextView) view ;
      tv.setTextColor(Color.BLACK);
      if(context.getResources().getConfiguration().screenLayout == 268435475 || context.getResources().getConfiguration().screenLayout > 268435475) {
    	  tv.setTextSize(26);
      }
      tv.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_NOMBRE))+ " "+
    		  cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_APELLIDO)));
   }
 
   @Override
   public View newView(Context context, Cursor cursor, ViewGroup parent)
   {
      final LayoutInflater inflater = LayoutInflater.from(context);
      final View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
 
      return view;
   }
}