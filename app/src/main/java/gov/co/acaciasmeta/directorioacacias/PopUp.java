package gov.co.acaciasmeta.directorioacacias;

import gov.co.acaciasmeta.adaptadores.cursorAdapter;
import gov.co.acaciasmeta.bd.baseDatos;
import gov.co.acaciasmeta.clases.DbAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PopUp extends Activity {

	private cursorAdapter cAdapter;
	private ListView lvFuncionarios;
	public static final String C_MODO = "modo";
	public static final int C_VISUALIZAR = 551;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pop_up);

		baseDatos dbPropia = new baseDatos(getApplicationContext(),
				"directorio3");
		SQLiteDatabase db = dbPropia.getWritableDatabase();
		
		lvFuncionarios = (ListView) findViewById(R.id.lvFuncionarios);

		String oficina = getIntent().getExtras().getString("oficina");
		Cursor cursor = db.rawQuery(
				"SELECT * FROM directorio3 WHERE hip_oficina='"
						+ oficina + "'", null);
		
		 cAdapter = new cursorAdapter(this, cursor);
	     lvFuncionarios.setAdapter(cAdapter);	
	     
	     lvFuncionarios.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int posicion,
					long id) {
				// TODO Auto-generated method stub
				visualizar(id);
			}
		});
		
	}
	
	 void visualizar(long id)
	   {
	      // Llamamos a la Actividad verFuncionarios indicando el modo visualizacion y el identificador del registro
	      Intent i = new Intent(PopUp.this, VerFuncionarios.class);
	      i.putExtra(C_MODO, C_VISUALIZAR);
	      i.putExtra(DbAdapter.C_COLUMNA_ID, id);
	 
	      startActivityForResult(i, C_VISUALIZAR);
	   }
	 
	

	
}
