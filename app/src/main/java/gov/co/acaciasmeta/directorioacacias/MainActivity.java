package gov.co.acaciasmeta.directorioacacias;

import gov.co.acaciasmeta.adaptadores.ItemAdapter;
import gov.co.acaciasmeta.adaptadores.cursorAdapter;
import gov.co.acaciasmeta.bd.baseDatos;
import gov.co.acaciasmeta.clases.Conexion;
import gov.co.acaciasmeta.clases.Item;

import java.util.ArrayList;
import java.util.Vector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnChildClickListener, OnItemClickListener{

	private ViewPager vp;
	private vpAdapter miAdapter;	
	private ConnectivityManager cm;
	private Conexion conex;
	public static Vector<String> padres=new Vector<String>();
	public static Vector<Vector> abuelo=new Vector<Vector>();
	
	 private DrawerLayout mDrawer;  
	 private ListView mDrawerOptions;
	 private ActionBarDrawerToggle mToggle;
	private SwipeRefreshLayout swipeLayout;
	Context context;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        llenarBD();
        
        mDrawerOptions = (ListView) findViewById(R.id.left_drawer);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        
               
        //mDrawerOptions.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values));
        mDrawerOptions.setOnItemClickListener(this);
		
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		conex = new Conexion(MainActivity.this,cm);
                               
        ArrayList<Item> items = new ArrayList<Item>();
        
        items.add(new Item("",R.drawable.facebook));        
        items.add(new Item("",R.drawable.twitter));        
        items.add(new Item("",R.drawable.youtube));         
        items.add(new Item("",R.drawable.chrome));
   
        // Sets the data behind this ListView
        this.mDrawerOptions.setAdapter(new ItemAdapter(this, items));
       
        
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this,             /* host Activity */
                mDrawer,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            	getSupportActionBar().setTitle("Alcaldía");
               // getSupportActionBar().setIcon(R.drawable.ic_drawer2);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            	getSupportActionBar().setTitle("Síguenos");
            	//getSupportActionBar().setIcon(R.drawable.ic_drawer);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawer.setDrawerListener(mToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
       // getSupportActionBar().setIcon(R.drawable.ic_drawer2);
        	

		vp = (ViewPager) findViewById(R.id.viewpager);
		miAdapter = new vpAdapter();
		vp.setAdapter(miAdapter);
	    
	   }
	
    

	@Override
   	public void onBackPressed() {
   			
   		new AlertDialog.Builder(this)
   		.setTitle("Alcaldía")
   		.setMessage("   ¿Deseas cerrar la aplicación?")
   		.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
   			

   			@Override
   			public void onClick(DialogInterface dialog, int which) {
   				finish();
   			}
   		})
   		
   		.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
   			
   			@Override
   			public void onClick(DialogInterface dialog, int which) {
   				
   			}
   		}).show();
   		
   	}

   	@Override
   	    protected void onPostCreate(Bundle savedInstanceState) {
   	        super.onPostCreate(savedInstanceState);
   	        // Sync the toggle state after onRestoreInstanceState has occurred.
   	        mToggle.syncState();
   	    }
   	 
   	 @Override
   	    public void onConfigurationChanged(Configuration newConfig) {
   	        super.onConfigurationChanged(newConfig);
   	        mToggle.onConfigurationChanged(newConfig);
   	    }
   	
   	
   	@Override
   		public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
   		  		    
   		    if(i==0){
   		    	
   		    	String face = "https://www.facebook.com/AlcaldiaAcacias?fref=ts";
   		    	
   		    	Intent ifa = new Intent(MainActivity.this,RedesSociales.class);
   		    	ifa.putExtra("facebook",face);
   		    	startActivity(ifa);
   		    	finish();
   		    	
   		    }
   		    
   		    if(i==1){
   		    	
   		    	
   		    	String twi = "https://twitter.com/alcaldiaAcacias";
   		    	
   		    	Intent ifa = new Intent(MainActivity.this,RedesSociales.class);
   		    	ifa.putExtra("facebook",twi);
   		    	startActivity(ifa);
   		    	finish();
   		    	
   		    }
   		    
   		    if(i==2){
   		    	
   		    	String you = "http://www.youtube.com/user/AlcaldiaAcacias";
   		    	
   		    	Intent ifa = new Intent(MainActivity.this,RedesSociales.class);
   		    	ifa.putExtra("facebook", you);
   		    	startActivity(ifa);
   		    	finish();
   		    }
   		    
   		    
   		    	if(i==3){
   		    	
   		    	String chr = "http://www.acacias-meta.gov.co";
   		    	
   		    	Intent ifa = new Intent(MainActivity.this,RedesSociales.class);
   		    	ifa.putExtra("facebook", chr);
   		    	startActivity(ifa);
   		    	finish();
   		    }
   		    
   		}
   	

   	@Override
   	public boolean onCreateOptionsMenu(Menu menu) {
   		// TODO Auto-generated method stub

   		//super.onCreateOptionsMenu(menu);
   	    getMenuInflater().inflate(R.menu.main, menu);
        return true;
   	}
   	

   	@Override
   	public boolean onOptionsItemSelected(MenuItem item) {
   		// TODO Auto-generated method stub

   		 if (mToggle.onOptionsItemSelected(item)) {
   	          return true;
   	        }
   		
   		switch (item.getItemId()) {
   			
   		case R.id.acerca:
               
   			Intent i = new Intent (MainActivity.this, AcercaDe.class);
   			startActivity(i);
   			
   			break;
   	           			
   			
   		case android.R.id.home:
   			
   		  if (mDrawer.isDrawerOpen(mDrawerOptions)){
   		               
   			  mDrawer.closeDrawers();
   			
   		  }else{
   				mDrawer.openDrawer(mDrawerOptions);
   		  }
   		  return true;
   		}
   		return super.onOptionsItemSelected(item);
   		
   	}
   
    private void acercaMenu() {
    		
   		new AlertDialog.Builder(this)
   		.setTitle("Acerca")
   		.setIcon(R.drawable.marca)
   		.setMessage(R.drawable.acercade)
   		//.setMessage("Versión: 1.0\n\nPorque usar las TIC para facilitarle la vida al ciudadano...\nEs la Decisión Correcta.\n\nProyecto Aplicaciones móviles para Gobierno en Línea.\n\nDesarrollado por: Fundación Tsamani 'Para el desarrollo de una ciudad del conocimiento'\n\nDesarrollador:Andrés Urrego\nProyecto Talento Digital-Min TIC-Inandina\nTwitter: @AndresUrregoC\nSkype: andres.urrego.castro1\nFacebook: Andrés Urrego C\nCelular: 3208780677")
   		.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
   			

   			@Override
   			public void onClick(DialogInterface dialog, int which) {
   				// TODO Auto-generated method stub
   				
   			}
   		}).show();
   		
   	}
     
  
   	
   	public boolean isOnline() {
   		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
   		NetworkInfo netInfo = cm.getActiveNetworkInfo();
   		if (netInfo != null &&netInfo.isConnectedOrConnecting()) {
   			return true;
   		}else{
   		return false;
   		}
   		   		
   	}

   	

   	private class vpAdapter extends PagerAdapter implements SwipeRefreshLayout.OnRefreshListener{

   		@Override
   		public int getCount() {
   			// TODO Auto-generated method stub
   			return 2;
   		}

   		@Override
   		public boolean isViewFromObject(View view, Object object) {
   			// TODO Auto-generated method stub
   			return view == ((LinearLayout) object);
   		}

   		@Override
   		public void destroyItem(ViewGroup container, int position, Object object) {
   			// TODO Auto-generated method stub
   			((ViewPager) container).removeView((LinearLayout) object);
   		}

   		@Override
   		public void finishUpdate(ViewGroup container) {
   			

   		}
   		   		   		
   		@Override 
   		public void onRefresh() {
   		    new Handler().postDelayed(new Runnable() {
   		        @Override public void run() {
   		            swipeLayout.setRefreshing(false);
   		            isOnline();
   		            

   	   				if(isOnline()==false){
   	   				    		TextView tvMostrarConexion = (TextView) findViewById(R.id.tvConexion);
   	   				    		tvMostrarConexion.setGravity(Gravity.CENTER_HORIZONTAL);
   	   				    		tvMostrarConexion.setVisibility(View.VISIBLE);
   	   				      }else{
   	   				        TextView tvMostrarConexion = (TextView) findViewById(R.id.tvConexion);
  				    		tvMostrarConexion.setGravity(Gravity.CENTER_HORIZONTAL);
  				    		tvMostrarConexion.setVisibility(View.GONE);
  				    	
   	   				      }
   		        }
   		    }, 3000);
   		}
   		
   		public void sendMessage(String t){
   		
   		}
   		
   	    @SuppressWarnings("deprecation")
   		@Override
   		public Object instantiateItem(ViewGroup container, int position) {
   			// TODO Auto-generated method stub

   			LayoutInflater inflater = (LayoutInflater) container.getContext()
   					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   			View v = null;
   			switch (position) {

   			case 0:

   				v = inflater.inflate(R.layout.fragment_principal, null);
                   //ImageView im = (ImageView) v.findViewById(R.id.imageView1);//esta es la nueva imagen
   				
   				ImageView ivNombre= (ImageView) v.findViewById(R.id.ivNombre);
   				ImageView ivCargo= (ImageView) v.findViewById(R.id.ivCargo);
   				ImageView ivServicio= (ImageView) v.findViewById(R.id.ivServicio);
   				ImageView ivSecretaria= (ImageView) v.findViewById(R.id.ivSecretaria);
   				ImageView ivFooter = (ImageView) v.findViewById(R.id.imageView5);
   				
   			  swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
   		      //swipeLayout.setOnRefreshListener(this);
   		      /*swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
   		            android.R.color.holo_green_light, 
   		            android.R.color.holo_orange_light, 
   		            android.R.color.holo_red_light);*/
   		   if (getResources().getConfiguration().screenLayout == 268435475 || getResources().getConfiguration().screenLayout > 268435475 ) {
   			  ivFooter.setPadding(10, 140, 10, 20);
  	   			}
   		    
   						
				if (isOnline() == false) {
					TextView tvMostrarConexion = (TextView) v.findViewById(R.id.tvConexion);
					tvMostrarConexion.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
					tvMostrarConexion.setVisibility(View.VISIBLE);
				} else {
					TextView tvMostrarConexion = (TextView) v.findViewById(R.id.tvConexion);
					tvMostrarConexion.setGravity(Gravity.CENTER_HORIZONTAL);
					tvMostrarConexion.setVisibility(View.GONE);
				}
   				
				
                 
   				ivNombre.setOnClickListener(new OnClickListener() {
   					
   					@Override
   					public void onClick(View v) {
   						pasarActividad(1);
   					}

   					
   				});
   				
   				ivCargo.setOnClickListener(new OnClickListener() {
   					
   					@Override
   					public void onClick(View v) {
   						pasarActividad(2);
   					}

   					
   				});
   				
   				ivSecretaria.setOnClickListener(new OnClickListener() {
   					
   					@Override
   					public void onClick(View v) {
   						pasarActividad(3);
   					}

   					
   				});
   				
   				ivServicio.setOnClickListener(new OnClickListener() {
   					
   					@Override
   					public void onClick(View v) {
   						pasarActividad(4);
   					}

   					
   				});
   				
   				
   				ImageButton ibBuscar = (ImageButton) v.findViewById(R.id.ibBuscar2);
   				

   				ibBuscar.setOnClickListener(new OnClickListener() {

   					@Override
   					public void onClick(View v) {
   						// TODO Auto-generated method stub
   						
   						Intent bu = new Intent (MainActivity.this, Busqueda.class);
   						startActivity(bu);
   					    finish();

   					}
   				});

   				break;

   			case 1:
                   
   				v = inflater.inflate(R.layout.secretarias, null);
   				ExpandableListView elv = (ExpandableListView) v
   						.findViewById(R.id.exl);
   				elv.setAdapter(new Adaptador(MainActivity.this));
   				elv.setOnChildClickListener(MainActivity.this);

   				break;
   				
   			

   			}

   			((ViewPager) container).addView(v, 0);
   			return v;

   		}

   		@Override
   		public Parcelable saveState() {
   			// TODO Auto-generated method stub
   			return null;
   		}

   		@Override
   		public void startUpdate(ViewGroup container) {
   			// TODO Auto-generated method stub

   		}

   		private void pasarActividad(int l) {
   			Intent intent=new Intent(getApplicationContext(), Busqueda.class);
   			intent.putExtra("numLista", l);
   			startActivity(intent);
   			finish();
   		}
   		
   	}

   	public class Adaptador extends BaseExpandableListAdapter {

   		Context contexto;
//   		String[] padre = {"Administrativa y Financiera","Fomento", "Infraestructura", "Educaci�n", "Despacho", "Planeaci�n", "Gobierno", "Contrataci�n", "Protecci�n", "Jur�dica", "Salud"};
   	 
//   		String[][] hijos = {
//
//   		        { "-Mart�n Dario Vega    CIO", "-Didier Castro   Ingeniero TI ","-Ernesto Pineda     Jefe Sistemas", "-Leidy Tatiana L�pez Urrego","-Luis Enrique Amaya Porras","-Ra�l Herrera Castro","-Hemel Eslava Mosquera","-Juan de Jesus Huintaco Rozo","-Omar Fernando Castro Borja", "-Armando Cabrera Calder�n","-Maria Ines Suarez Palacios","-Teresa de Jesus Sandoval Rodr�guez","-Cecilia Melo Garc�a","-Maria Nelly Saray C�spedes","-Carmen Elisa P�ez Pach�n","-Blanca Cecilia Pinilla Melo","-Zonia Esperanza Mora Arias","-Eulalia Mar�n Franco","-Luz Mila Cubillos Silvestre", "-Olga Luc�a Olarte Morales","-Nelcy Roc�o Bedoya Caicedo","-Lilia Garz�n", "-Marlene Acosta Lara", "-Maria Eugenia Reina p�ez", "-Victor Julio Alvarez Cascante", "-Leidy Yhasmin Noguera Meneses", "-Jaidy Elaika Nieto", "-Lucy Yolanda Guerrero Reyes" },
//   		        {"-Silvio Eduardo Calder�n Rodr�guez","-Wilmer Mena Pe�a","-Ana Ligia Nieto Gonz�lez", "-Marcela Patricia Bonilla Gutierrez"},
//   				{"-Humberto Alvarez Romero","-Hernando Cague�o Cabrera","-Carlos Arturo Ortiz Rojas","-Jaideir Hern�n Romero Bland�n", "-Bernardino Romero Rinc�n", "-John Jairo Restrepo Ladino","-Armando Gomez", "-Mart�n Antonio P�rez Romero", "-Eudolina Romero P�rez", "-Rafael Fernando Parra Tolosa","-Jimmy Andr�s Torres Castro"},
//   				{"-Jairo Humberto Lozano Hern�ndez", "-Omar Hern�n Guarnizo Clavijo", "-Ximena Sarai Pastas Bustos","-Hilda Janeth Rozo Gutierrez","-Ang�lica Mar�a Correal Rico"},
//   				{"-Arcenio Vargas �lvarez", "-Rosa Mar�a D�az Maldonado", "-Melba Yulieth Correal Baquero","-Mar�a Cristina Gutierrez Candamil","-Luz Neida Pinz�n Baracaldo"},
//   				{"-William Fern�ndo Rivera Pinz�n", "-Doris Manrique Cabrera","-Diana Matilde Hern�ndez Rojas","-Heidy Cristina Baquero Parrado", "-Gerardo Ayala Castillo", "-Camilo Javier Pineda Arevalo",  "-Omar Alejandro Rodr�guez Pardo", "-Jhon Henry Orjuela Gamba"},
//   				{"-Nixon Frey P�rez Montero", "-Esperanza Ca�on Moreno", "-Beatriz Rojas �lvarez", "-Naydu Infante Sierra","-Roberto Baquero Jimenez","-Germ�n Quevedo Gutierrez", "-Sandra Milena Abello Ramos","-Luz Myriam Rios Gutierrez","-Nelson Rodr�guez Valencia"},
//   				{"-Myriam Celis Figueroa","-Gustavo Adolfo salazar Saddy","-Oscar Eduardo Arias Tabares"},
//   				{"-Luz Marina Bolivar Calder�n"},
//   				{"-Elsy Evangelina Valenzuela Mar�n","-Roger Alexander Acero Rojas"},
//   				{"-Adriana Mar�a Hern�ndez Parada","-Jairo Humberto Vidales Mendez", "-Nelson Gilberto Gutierrez Vaca","-Lida Aliria Rozo Ruiz", "-Elizabeth Mora Arias", "-Digna Mercedes Rodr�guez Castillo", "-Martha Elena Pinz�n Forero", "-Marilu Ayala Martinez", "-Sandra Patricia Quevedo Castro"}
//
//   		};

   		public Adaptador(Context context) {
   			// TODO Auto-generated constructor stub

   			this.contexto = context;

   		}

   		@Override
   		public Object getChild(int arg0, int arg1) {
   			// TODO Auto-generated method stub
   			return null;
   		}

   		@Override
   		public long getChildId(int groupPosition, int childPosition) {
   			// TODO Auto-generated method stub
   			return 0;
   		}
   		
   		public boolean Pantalla() {
   		 if (getResources().getConfiguration().screenLayout == 268435475) {
   	   		return true;	
   	   	}else
   		 return false;
   	 }


   		@Override
   		public View getChildView(int groupPosition, int childPosition,
   				boolean isLastChild, View convertView, ViewGroup parent) {
   			// TODO Auto-generated method stub
   			
   			TextView t = new TextView(contexto);
   			t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
   			t.setPadding(70, 0, 0, 0);
   			t.setText((MainActivity.abuelo.get(groupPosition)).get(childPosition)+"");
   			t.setTextColor(Color.DKGRAY);
   			if (getResources().getConfiguration().screenLayout == 268435475 || getResources().getConfiguration().screenLayout > 268435475 ) {
   	   			t.setTextSize(30);
   	   			}else{
   	   			t.setTextSize(20);	
   	   			}

   			return t;

   		}

   		

		@Override
   		public int getChildrenCount(int groupPosition) {
   			// TODO Auto-generated method stub
   			return MainActivity.abuelo.get(groupPosition).size();
   		}

   		@Override
   		public Object getGroup(int groupPosition) {
   			// TODO Auto-generated method stub
   			return groupPosition;
   		}

   		@Override
   		public int getGroupCount() {
   			// TODO Auto-generated method stub
   			return MainActivity.padres.size();
   		}

   		@Override
   		public long getGroupId(int groupPosition) {
   			// TODO Auto-generated method stub
   			return groupPosition;
   		}

   		@Override
   		public View getGroupView(int groupPosition, boolean isExpanded,
   				View convertView, ViewGroup parent) {
   			// TODO Auto-generated method stub
   			
   			TextView t = new TextView(contexto);
   			t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
   			t.setPadding(50, 0, 0, 0);
   			t.setText(MainActivity.padres.get(groupPosition));
   			t.setTextColor(Color.rgb(0, 0, 0));
   			if (getResources().getConfiguration().screenLayout == 268435475 || getResources().getConfiguration().screenLayout > 268435475 ) {
   			t.setTextSize(36);
   			}else{
   			t.setTextSize(25);	
   			}

   			return t;
   		}

   		@Override
   		public boolean hasStableIds() {
   			// TODO Auto-generated method stub
   			return false;
   		}

   		@Override
   		public boolean isChildSelectable(int groupPosition, int childPosition) {
   			// TODO Auto-generated method stub
   			return true;  
   		}

   	}

   	@Override
   	public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
   			int arg3, long arg4) {
   		// TODO Auto-generated method stub
   		
   		String oficina = (String) MainActivity.abuelo.get(arg2).get(arg3);
   		Intent i = new Intent(MainActivity.this, PopUp.class);
   		i.putExtra("oficina", oficina);
   		startActivity(i);
   		
   		return false;
   	}
   	public void llenarBD() {
			baseDatos dbPropia = new baseDatos(getApplicationContext(), "directorio3");
	      	SQLiteDatabase db = dbPropia.getWritableDatabase();
	      		      	 
	      	Cursor cursor=db.rawQuery("SELECT hip_secretaria FROM directorio3  ", null);
	      	
   	     if (cursor.moveToFirst()){
   	      	Log.e("Aqui","Si tenemos algo ");
       		do{
       			String padreSustituto=cursor.getString(0);
				//Log.e("padresustitu", padreSustituto+"");
       			if(!padres.contains(padreSustituto)){

       				padres.add(padreSustituto);
       			}
       			Vector<String> hijo=new Vector<String>();
       			Cursor cursorHijo=db.rawQuery("SELECT hip_oficina FROM directorio3 WHERE hip_secretaria='"+padreSustituto+"'", null);
       			if(cursorHijo.moveToFirst()){
       				do{
       					String hijoSustituto=cursorHijo.getString(0);

                        if(!hijo.contains(hijoSustituto)){
                            hijo.add(hijoSustituto);
                        }
                        //Log.e("hijos", hijoSustituto + "");
       				}while(cursorHijo.moveToNext());
       				
       				if(!abuelo.contains(hijo)){
       					abuelo.add(hijo);
       				}
       				
       			}else{
       	       		Toast.makeText(getApplicationContext(), "No se han podido recuperar los datos", Toast.LENGTH_LONG).show();
       	       	}
                Log.e("hijos", hijo + "");
                Log.e("Abuelo ", abuelo+"");
 			  }
            while(cursor.moveToNext());
       	}else{
       		Toast.makeText(getApplicationContext(), "No se han podido recuperar los datos", Toast.LENGTH_LONG).show();
       	}


	}
   	
  
    
    
    
    
}
