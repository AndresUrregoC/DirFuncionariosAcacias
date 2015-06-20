package gov.co.acaciasmeta.clases;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ViewClient extends WebViewClient {
	
	//Esta clase es para cargar lo de las redes sociales en nuestra 
	//aplicacion sin tener que salir de ella
	public boolean shouldOverrideUrlLading(WebView v, String url){
		
		v.loadUrl(url);
		
		return true;
	}

}
