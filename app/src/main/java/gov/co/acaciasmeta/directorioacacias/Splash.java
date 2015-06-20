package gov.co.acaciasmeta.directorioacacias;

/**
 * Splash
 * Ventan de Inicio con el logo de la alcaldia
 * @author andres urrego
 *
 */

import gov.co.acaciasmeta.clases.Conexion;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

public class Splash extends Activity {

	private static final String TAG = "Splash";	//Etiqueta de depuracion
	public static final int segundos = 2000;   //Tiempo que dura la Actividad en mostrarse
	ConnectivityManager cm;
	Conexion conex;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_splash);
		
		
		cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Permite ejecutar la Actividad en versiones mayores a 3
        StrictMode.setThreadPolicy(policy);  
        conex= new Conexion(this, cm);
        conex.isOnline();							//Probar si tiene Internet
		Handler handler = new Handler();			//Creacion de un hilo de ejecucion
		handler.postDelayed(iniciar(), segundos);	//Tiempo de ejecucion del hilo
		
	 }

	/**
	 * iniciar
	 * Creacion de un hilo que al terminar de cargar la base de datos
	 * espera 2 segundos
	 * @return res
	 */
	private Runnable iniciar(){
		Runnable res = new Runnable(){
			public void run(){
				Intent i = new Intent(Splash.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		};
		return res;
		
	}



}
