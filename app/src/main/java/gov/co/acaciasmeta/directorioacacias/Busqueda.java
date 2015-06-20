package gov.co.acaciasmeta.directorioacacias;


import gov.co.acaciasmeta.adaptadores.cursorAdapter;
import gov.co.acaciasmeta.clases.DbAdapter;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
 
public class Busqueda extends ListActivity implements OnClickListener{
 
	private Spinner sp;

	public static final String C_MODO = "modo";
	public static final int C_VISUALIZAR = 551;
	private static final String LOGTAG = "LogsAndroid";

	private DbAdapter dbAdapter;
	private Cursor cursor;
	private cursorAdapter hipotecaAdapter;
	private ListView lista;
	private EditText txtPalabraBus;
	private Button bus;
	private ListActivity base = this;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.fragment_busqueda);
       
		lista = (ListView) findViewById(android.R.id.list);
		bus = (Button) findViewById(R.id.n1);
		txtPalabraBus = (EditText) findViewById(R.id.palabra);
		sp = (Spinner) findViewById(R.id.spinner1);

		String valores[] = { "Selecciona una opción", "Nombre", "Cargo",
				"Secretaria", "Servicio y/o Trámite" };
		sp.setAdapter(new ArrayAdapter<String>(this, R.layout.textview_spinner,
				valores));
		sp.setSelection(getIntent().getIntExtra("numLista", 0));
		dbAdapter = new DbAdapter(this);
		dbAdapter.abrir();

		consultar();

		// Log.e(LOGTAG,dbAdapter.consultarLista() );
		registerForContextMenu(this.getListView());
		// bus.setOnKeyListener(this);

		bus.setOnClickListener(this);

		txtPalabraBus.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				// TODO Auto-generated method stub
				String palabra = txtPalabraBus.getText().toString();

				Cursor c = dbAdapter.consultarEtiqueta(palabra);

				if (c != null) {
					cursor = c;
					startManagingCursor(cursor);
					hipotecaAdapter = new cursorAdapter(base, cursor);
					lista.setAdapter(hipotecaAdapter);
				}

			}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
			  cursor = dbAdapter.getCursor();
		      startManagingCursor(cursor);
		      hipotecaAdapter = new cursorAdapter(Busqueda.this, cursor);
		      lista.setAdapter(hipotecaAdapter);
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			cursor = dbAdapter.getCursor();
		      startManagingCursor(cursor);
		      hipotecaAdapter = new cursorAdapter(Busqueda.this, cursor);
		      lista.setAdapter(hipotecaAdapter);
		}
	});	
    }
    
	@Override
	public void onBackPressed() {
	
		Intent t = new Intent(this,MainActivity.class);
		startActivity(t);
		t.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
	}


  private void consultar()
   {
      cursor = dbAdapter.getCursor();
      startManagingCursor(cursor);
      hipotecaAdapter = new cursorAdapter(this, cursor);
      lista.setAdapter(hipotecaAdapter);
   }
 

   void visualizar(long id)
   {
      // Llamamos a la Actividad HipotecaFormulario indicando el modo visualizaci�n y el identificador del registro
      Intent i = new Intent(Busqueda.this, VerFuncionarios.class);
      i.putExtra(C_MODO, C_VISUALIZAR);
      i.putExtra(DbAdapter.C_COLUMNA_ID, id);
 
      startActivityForResult(i, C_VISUALIZAR);
   }
 
   @Override
   protected void onListItemClick(ListView l, View v, int position, long id)
   {
      super.onListItemClick(l, v, position, id);
 
     visualizar(id);
     
   }
   
   
   @Override
   public boolean onContextItemSelected(MenuItem item) {
       
      AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      Intent i;
       
      switch(item.getItemId())
      {
        
          
         case C_VISUALIZAR:
            visualizar(info.id);
            return true;

       }
       return super.onContextItemSelected(item);
   }


	
	@Override
	public void onClick(View v) {

		if (v == bus) {

			if (txtPalabraBus.getText().toString().equals("")) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txtPalabraBus.getWindowToken(), 0);

				Toast.makeText(this, "Campos Vacíos", Toast.LENGTH_SHORT)
						.show();
			} else {

				if (sp.getSelectedItemPosition() == 1) {

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(txtPalabraBus.getWindowToken(),
							0);

					String palabra = txtPalabraBus.getText().toString();

					Cursor c = dbAdapter.consultarNombre(palabra);

					if (c != null) {
						cursor = c;
						startManagingCursor(cursor);
						hipotecaAdapter = new cursorAdapter(this,
								cursor);
						lista.setAdapter(hipotecaAdapter);

					}

				} else if (sp.getSelectedItemPosition() == 2) {

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(txtPalabraBus.getWindowToken(),
							0);

					String palabra = txtPalabraBus.getText().toString();

					Cursor c = dbAdapter.consultarCargo(palabra);

					if (c != null) {
						cursor = c;
						startManagingCursor(cursor);
						hipotecaAdapter = new cursorAdapter(this,
								cursor);
						lista.setAdapter(hipotecaAdapter);

					}

				} else if (sp.getSelectedItemPosition() == 3) {

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(txtPalabraBus.getWindowToken(),
							0);

					String palabra = txtPalabraBus.getText().toString();

					Cursor c = dbAdapter.consultarSecretaria(palabra);

					if (c != null)

					{
						cursor = c;
						startManagingCursor(cursor);
						hipotecaAdapter = new cursorAdapter(this,
								cursor);
						lista.setAdapter(hipotecaAdapter);
					}

				} else if (sp.getSelectedItemPosition() == 4) {

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(txtPalabraBus.getWindowToken(),
							0);

					String palabra = txtPalabraBus.getText().toString();

					Cursor c = dbAdapter.consultarTramite(palabra);

					if (c != null)

					{
						cursor = c;
						startManagingCursor(cursor);
						hipotecaAdapter = new cursorAdapter(this,
								cursor);
						lista.setAdapter(hipotecaAdapter);
					}

				} else {

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(txtPalabraBus.getWindowToken(),
							0);

					String palabra = txtPalabraBus.getText().toString();

					Cursor c = dbAdapter.consultarEtiqueta(palabra);

					if (c != null) {
						cursor = c;
						startManagingCursor(cursor);
						hipotecaAdapter = new cursorAdapter(this,
								cursor);
						lista.setAdapter(hipotecaAdapter);

					}
				}
			}
		}

 }

}