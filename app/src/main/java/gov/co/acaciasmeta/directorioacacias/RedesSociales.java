package gov.co.acaciasmeta.directorioacacias;

import gov.co.acaciasmeta.clases.Conexion;
import gov.co.acaciasmeta.clases.ViewClient;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class RedesSociales extends Activity {


	private WebView wb;
	private Bundle b;
	private String pal;
	private TextView tvConex;
	private ConnectivityManager cm;
	private Conexion conex;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_redes_sociales);

		
		wb = (WebView) findViewById(R.id.webView1);
		tvConex = (TextView) findViewById(R.id.tvConexionWeb);
		b = getIntent().getExtras();
		pal = b.getString("facebook");
	    cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
	    conex = new Conexion(RedesSociales.this, cm);
		
		wb.loadUrl(pal);
		wb.setWebViewClient(new ViewClient());
		wb.getSettings().setJavaScriptEnabled(true);
		wb.getSettings().setLoadWithOverviewMode(true);
		wb.getSettings().setUseWideViewPort(true);
		
		if(conex.isOnline() == false){
			tvConex.setVisibility(View.VISIBLE);
			tvConex.setGravity(Gravity.CENTER_HORIZONTAL);
			wb.setVisibility(View.GONE);
		}else {
			wb.setVisibility(View.VISIBLE);
		}
			
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (this.wb.canGoBack()) {
			this.wb.goBack();
		} else {
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish();
			super.onBackPressed();
		}
	}


}
