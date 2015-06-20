package gov.co.acaciasmeta.directorioacacias;

import gov.co.acaciasmeta.clases.DbAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class VerFuncionarios extends Activity {
    
   private DbAdapter dbAdapter;
   private Cursor cursor; 
   // Modo del formulario
   private int modo ;
   // Identificador del registro que se edita cuando la opcion es MODIFICAR
   private long id ;

   // Elementos de la vista

   private TextView nombre;
   private TextView secretaria;
   private TextView oficina;
   private TextView cargo;
   private TextView modalidad;
   private TextView email;
   private TextView twitter;
   private TextView facebook;
   private TextView youtube;
   private TextView paginaweb;
   private TextView celular;
   private TextView whatsapp;
   private TextView tel_fijo;
   private TextView extension;
   private TextView linea;
   private TextView atencion;
   private TextView publico;
   private TextView tramite;
   
    
   private Button boton_guardar;
   private Button boton_llamar;
  
 
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.fragment_ver_funcionarios);
      
      
      Intent intent = getIntent();
      Bundle extra = intent.getExtras();
      
      if (extra == null) return;
       
      //
      // Obtenemos los elementos de la vista
      //
      nombre = (TextView) findViewById(R.id.nombre);
      secretaria = (TextView) findViewById(R.id.secretaria);
      oficina = (TextView) findViewById(R.id.oficina);
      cargo = (TextView) findViewById(R.id.cargo);
      modalidad = (TextView) findViewById(R.id.modalidad);
      email = (TextView) findViewById(R.id.email);
      twitter = (TextView) findViewById(R.id.twitter);
      facebook = (TextView) findViewById(R.id.facebook);
      youtube = (TextView) findViewById(R.id.youtube);
      paginaweb = (TextView) findViewById(R.id.paginaweb);
      celular = (TextView) findViewById(R.id.celular);
      whatsapp = (TextView) findViewById(R.id.whatsapp);
      tel_fijo = (TextView) findViewById(R.id.tel_fijo);
      extension = (TextView) findViewById(R.id.extension);
      linea = (TextView) findViewById(R.id.linea);
      atencion = (TextView) findViewById(R.id.atencion); 
      publico = (TextView) findViewById(R.id.publico);
      tramite = (TextView) findViewById(R.id.tramite);
      
      
      boton_guardar = (Button) findViewById(R.id.boton_guardar);
      boton_llamar = (Button) findViewById(R.id.boton_llamar);
     
       
      
      //Lanzamos la informacion
      boton_guardar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i=new Intent(getApplicationContext(), AgregarContacto.class);
			i.putExtra("nombre", cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_NOMBRE)));
			i.putExtra("correo", cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_EMAIL)));
			i.putExtra("telefono", cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_CELULAR)));
			startActivity(i);
		}
	});
      
      
      boton_llamar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			Intent i = new Intent(getApplicationContext(), Llamar.class);
			i.putExtra("telefono", cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_TELEFONO)));
			i.putExtra("celular", cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_CELULAR)));
			startActivity(i);
		}
	});
      
      
      //
      // Creamos el adaptador  
      //
      dbAdapter = new DbAdapter(this);
      dbAdapter.abrir();
       
      //
      // Obtenemos el identificador del registro si viene indicado
      //
      if (extra.containsKey(DbAdapter.C_COLUMNA_ID))
      {
         id = extra.getLong(DbAdapter.C_COLUMNA_ID);
         consultar(id);
      }
       
      //
      // Establecemos el modo del formulario
      //
      establecerModo(extra.getInt(Busqueda.C_MODO));       
       
   }
    
   private void establecerModo(int m)
   {
      this.modo = m ;
       
      if (modo == Busqueda.C_VISUALIZAR)
      {
         this.setTitle(nombre.getText().toString());
      }
     
   }
    
   private void consultar(long id) {
     
      // Consultamos el funcionario por el identificador
      
      cursor = dbAdapter.getRegistro(id);
       
      nombre.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_NOMBRE))+
    		  cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_APELLIDO)));
      secretaria.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_SECRETARIA)));
      oficina.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_OFICINA)));
      cargo.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_CARGO)));
      modalidad.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_MODALIDAD)));
      email.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_EMAIL)));
      twitter.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_TWITTER)));
      facebook.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_FACEBOOK)));
      youtube.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_YOUTUBE)));
      paginaweb.setText(Html.fromHtml("<a href=\"http://"+
    		  cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_PAGINAWEB))
    		  +"\">"+ cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_PAGINAWEB))
    		  +"</a>"
    		  ));
      paginaweb.setMovementMethod(LinkMovementMethod.getInstance());
      celular.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_CELULAR)));

      whatsapp.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_WHATSAPP)));
      tel_fijo.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_TELEFONO)));
      extension.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_EXTENSION)));
      linea.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_LINEA)));
      atencion.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_ATENCION)));
      publico.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_PUBLICO)));
      tramite.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.C_COLUMNA_TRAMITE)));

   }
    
}