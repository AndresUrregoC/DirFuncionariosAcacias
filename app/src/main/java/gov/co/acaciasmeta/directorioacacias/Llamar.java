package gov.co.acaciasmeta.directorioacacias;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Llamar extends Activity {

	private ListView lvNumeros;
	private Bundle bd;
	private String telefono;
	private String celular;
	
	
	private ArrayAdapter<String> adaptador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_llamar);
		
		lvNumeros = (ListView) findViewById(R.id.lvNumeros);
		bd = getIntent().getExtras();
		
		telefono = bd.getString("telefono");
		celular = bd.getString("celular");
		
		
		
	    String lisNumeros[] = {telefono, celular}; 
		adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lisNumeros);
		lvNumeros.setAdapter(adaptador);
		
		lvNumeros.setOnItemClickListener(new OnItemClickListener() {
			

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				
				//telefono = bd.getString("telefono");
				//celular = bd.getString("celular");
				
				if(position==0){
					
					try{
						Intent intent = new Intent(Intent.ACTION_CALL);
						telefono = "tel:"+bd.getString("telefono").trim();
						intent.setData(Uri.parse(telefono));
						startActivity(intent);
				    }
				    catch(ActivityNotFoundException ex){
				            ex.toString();
				            
				    }
									
					
				}
				
				if(position==1){
					
					try{
						Intent intent = new Intent(Intent.ACTION_CALL);
						celular = "tel:"+bd.getString("celular").trim();
						intent.setData(Uri.parse(celular));
						startActivity(intent);
				    }
				    catch(ActivityNotFoundException ex){
				            ex.toString();
				            
				    }
					
				}
				
			}
			
			
		});
	
		
	}




}
