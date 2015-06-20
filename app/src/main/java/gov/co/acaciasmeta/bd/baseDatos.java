package gov.co.acaciasmeta.bd;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class baseDatos extends SQLiteOpenHelper {
	
	private static int version = 1;
	private static CursorFactory factory = null;
	public static String TAG="baseDatos";
	
	public baseDatos(Context context, String name) {
		super(context, name, factory, version);
	}	   
	   
	@Override
	public void onCreate(SQLiteDatabase db) {
		

		  db.execSQL( "CREATE TABLE directorio3(" +
		             " _id INTEGER PRIMARY KEY," +
		             " hip_nombre TEXT NOT NULL, " +
		             "  apellido TEXT, "+
		             " hip_secretaria TEXT, " +
		             " hip_fecha_ingreso TEXT, " +
		             " hip_oficina TEXT," + 
		             " hip_direccion TEXT," + // nuevo
		             " hip_cargo TEXT," +
		             " hip_profesion TEXT," +  //nuevo
		             " hip_funcion TEXT," +    //nuevo
		             " hip_modalidad TEXT," +
		             " hip_email TEXT," +           
		             " hip_twitter TEXT," +
		             " hip_facebook TEXT," +
		             " hip_youtube TEXT," +
		             " hip_paginaweb TEXT," +
		             " hip_etiqueta TEXT," +
		             " hip_telefono TEXT," +
		             " hip_extension TEXT," +
		             " hip_linea TEXT," +
		             " hip_trami_servi TEXT," +
		             " hip_atencion TEXT," +
		             " hip_publico TEXT," +           
		             " hip_whatsapp TEXT," +
		             " hip_celular TEXT)" );
	    	}
	
	
	
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	 
	 
	 
/****
 * consuta los funcionarios existentes
 * 
 * 
 * 	
 * @return ArrayListas<Funcionario> 
 */
	public Cursor consultarFuncionarios(){
    
    
	SQLiteDatabase db = this.getWritableDatabase();
	String[] campos = new String[] {"*"};
	Cursor c = db.query("directorio3", campos, null, null, null, null, null);
	
    
	return c;
	}
	
	
	
	   
	/**
	 * actualizar los nuevo datos
	 * 
	 * @param lista ArrayList<Funcionario> lista de los funcionarios
	 * @return boolean si fue exitoso o no 
	 */
	public boolean actualizarDatosFuncionarios(JSONObject lista){
		boolean salida=true;
	
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete FROM directorio3");
		
			String miSql="";

			try {


				for(int i = 0; i < lista.length(); i++){

					JSONArray jsonArray = lista.getJSONArray("d");

					Log.e("jsonarray", "datos d:" + jsonArray);

						for (int h= 0; h < jsonArray.length(); h++){

							JSONObject fila=jsonArray.getJSONObject(h);

							Log.e("json", "dentro de d:" +fila);


								miSql = "INSERT INTO directorio3 (_id,hip_nombre,            " +
										"apellido, " +
										" hip_secretaria , " +
										" hip_fecha_ingreso , " +
										" hip_oficina ," +
										" hip_direccion ," + // nuevo
										" hip_cargo ," +
										" hip_profesion ," +  //nuevo
										" hip_funcion ," +    //nuevo
										" hip_modalidad ," +
										" hip_email ," +
										" hip_twitter ," +
										" hip_facebook ," +
										" hip_youtube ," +
										" hip_paginaweb ," +
										" hip_etiqueta ," +
										" hip_telefono ," +
										" hip_extension ," +
										" hip_linea ," +
										" hip_trami_servi ," +
										" hip_atencion ," +
										" hip_publico ," +
										" hip_whatsapp ," +
										" hip_celular " +
										" ) VALUES ("
										+" '"+fila.getString("idfuncionario")+"',"
										+" '"+fila.getString("nombrecompleto")+"',"
										+" '"+fila.getString("apellidocompleto")+"',"
										+" '"+fila.getString("nombredependencia")+"',"
										+" '"+fila.getString("fechaingresofuncionarios")+"',"
										+" '"+fila.getString("oficina")+"',"
										+" '"+fila.getString("direccion")+"',"
										+" '"+fila.getString("cargo")+"',"
										+" '"+fila.getString("perfilfuncionarios")+"',"
										+" '"+fila.getString("funciones")+"',"
										+" '"+fila.getString("modalidad")+"',"
										+" '"+fila.getString("mailinstitucional")+"',"
										+" '"+fila.getString("twitterinstitucional")+"',"
										+" '"+fila.getString("facebookinstitucional")+"',"
										+" '"+fila.getString("youtubeinstitucional")+"',"
										+" '"+fila.getString("paginawebinstitucional")+"',"
										+" '"+fila.getString("etiqueta")+"',"
										+" '"+fila.getString("telinstitucional")+"',"
										+" '"+fila.getString("extension")+"',"
										+" '018000900',"
										+" '"+fila.getString("tramitesyserviciosdafp")+"',"
										+" '"+fila.getString("horarioatencion")+"',"
										+" '"+fila.getString("atencionpublico")+"',"
										+" '"+fila.getString("whatsaappinstitucional")+"',"
										+" '"+fila.getString("celinstitucional")+"')";

							if(!miSql.equals(""))
								db.execSQL(miSql);
							Log.e(TAG, "...... sele .. " + miSql);

							}


						}



			} catch (JSONException e) {
				// TODO Auto-generated catch block
				salida=false;
				e.printStackTrace();
			}
			
			


		
		db.close();
		
		return salida;
	} 
	

}
