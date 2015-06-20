package gov.co.acaciasmeta.clases;

import gov.co.acaciasmeta.bd.baseDatos;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;


@SuppressLint("NewApi")
public class Conexion {
	
	private static final String TAG = "conexion";		//Etiqueta de depuracion
	private String URL="127.0.0.1";				//URL de la pagina donde se alojara la Base de datos
	private String data;							//Variable que guarda la respuesta de la Base de datos
	private Context contexto;  
	ConnectivityManager cm;
 
	
	@SuppressLint("NewApi")
	public Conexion(Context contexto, ConnectivityManager cm) {
		
		this.cm = cm;
		this.contexto = contexto;   
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Permite ejecutar la Actividad en versiones mayores a 3
	    StrictMode.setThreadPolicy(policy); 
	}
	
	/**
	 * isOnline
	 * Funcion que se encarga de comprobar si se tiene acceso a Internet
	 * @return si esta conectado o no
	 */
	public boolean isOnline() {
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null &&netInfo.isConnectedOrConnecting()) {
			Log.i(TAG,"Con acceso a Internet");
			cargarBD();
			return true;
		}else{
			return false;
		}
		
	}
	
	
	/**
	 * cargarBD
	 * Se encarga de consultar la Base de Datos que se encuentra en el Servidor

	 */
	private void cargarBD() {
		JSONObject ja=null;
		baseDatos bd=new baseDatos(contexto, "directorio3");		  
		 
		try {
			data=httpGetData("http://servicedatosabiertoscolombia.cloudapp.net/v1/Alcaldia_de_Acacias/agendaalcaldia?$format=json");
			if(data.length()>1)
				ja=new JSONObject(data);
				Log.e(TAG,bd.actualizarDatosFuncionarios(ja)+"");
				Cursor consulta=bd.consultarFuncionarios();
				Log.e("Consulta", ""+ja);
				consulta.moveToFirst();
				//Log.e(TAG,"Esta es la consulta "+consulta.getString(1));
		} catch (JSONException e) {  
			
			Log.e(TAG, e.toString());
			Toast.makeText(contexto, "Error recuperando la informacion del servidor, verifique su conexion a internet y vuelva a intentarlo.", Toast.LENGTH_SHORT).show();
			
		}
		try{
			//Toast.makeText(contexto, ja.getString(1), Toast.LENGTH_SHORT).show();
			for(int i=0;i<ja.length();i++){
				//ja.getJSONObject(i);
			}
		} catch (Exception e) {  
			
		}
		
	}
	
	/**
	 * httpGetData
	 * Toma la URL y convierte los datos que recibe en un string
	 * @param mURL
	 * @return
	 */
	public String httpGetData(String mURL) {
        String response="";
        mURL=mURL.replace(" ", "%20");
        //Log.i("LocAndroid Response HTTP Threas","Ejecutando get 0: "+mURL);
        HttpClient httpclient = new DefaultHttpClient();
          
        //Log.i("LocAndroid Response HTTP Thread","Ejecutando get 1");
    	HttpGet httppost = new HttpGet(mURL);
        //Log.i("LocAndroid Response HTTP Thread","Ejecutando get 2");
        
        try {
	        //Log.i("LocAndroid Response HTTP","Ejecutando get");
	        // Execute HTTP Post Request
	        ResponseHandler<String> responseHandler=new BasicResponseHandler();
	        response = httpclient.execute(httppost,responseHandler);
	        //Log.i("LocAndroid Response HTTP",response);
    	} catch (ClientProtocolException e) {
	        //Log.i("LocAndroid Response HTTP ERROR 1",e.getMessage());
    	} catch (IOException e) {
    		//Log.i("LocAndroid Response HTTP ERROR 2",e.getMessage());
    	}
        return response;
    }  
}
