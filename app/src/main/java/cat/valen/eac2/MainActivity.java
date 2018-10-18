package cat.valen.eac2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DBInterface bd;
    RecyclerView recyclerView;
    Adaptador mAdapter;
    ArrayList<Llibre> mllibres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Crida al metode de la clase mare per guarda info de l'activity
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        // Carrega la barra d'eines
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        bd = new DBInterface(this);

        //Referenciem el RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rView);
        // Enllacem les dades al adaptador per se mostrades
        //afegim l'adaptador amb les dades i el LinearLayoutManager que pintarà les dades
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*mAdapter = new Adaptador(this, mllibres  );
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();*/
        //Iniciem una objecte Llista per carregar les dades de la BDs
        mllibres = Llibre.getLlibres( this );

        if (mAdapter != null) // it works second time and later
            mAdapter.notifyDataSetChanged();
        else { // it works first time
            mAdapter = new Adaptador(this, mllibres  );
            recyclerView.setAdapter(mAdapter);
        }

        //Podem utilitzar animacions sobre la llista
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // Buscador
        //setupSearch();
        // Afegim un Listener al widget recyclerView al fer onClick sobre algun element
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                       // get position
                        //int pos = adapter.getItemId( position );
                        String clickedDataItem = mllibres.get(position).getTitol();
                        Toast.makeText(view.getContext(), "You clicked " + clickedDataItem, Toast.LENGTH_SHORT).show();
                        // check if item still exists
                       /* if(pos != RecyclerView.NO_POSITION){
                            String clickedDataItem = dades.get(pos);
                            Toast.makeText(view.getContext(), "You clicked " + clickedDataItem, Toast.LENGTH_SHORT).show();
                        }*/

                        Intent intent = new Intent(MainActivity.this, detall_llibre.class);
                        //Obrim la base dedades
                        bd.obre();
                        Cursor c = bd.obtenirLlibre(clickedDataItem  );

                        Bundle dades = new Bundle(  );
                        dades.putString( "TITOL",c.getString(0) );
                        dades.putString( "AUTOR",c.getString(1) );
                        dades.putString( "EDITORIAL",c.getString(2) );
                        dades.putString( "TIPUS",c.getString(3) );
                        dades.putInt( "PREU",Integer.parseInt(  c.getString(4)) );

                        intent.putExtras( dades );
                        startActivity(intent);
                        //startActivity( new Intent(MainActivity.this,detall_llibre.class ) );
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        //Després de cada modificació a la font de les dades, hem de notificar-ho a l'adaptador
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Iniciem una objecte Llista per carregar les dades de la BDs
        mllibres = Llibre.getLlibres( this );
        mAdapter.notifyDataSetChanged();
        mAdapter = new Adaptador(this, mllibres  );
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_main, menu );

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Configure the search info and add any event listeners...
        search(searchView);
        return super.onCreateOptionsMenu(menu);

        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*When the user selects one of the app bar items, the system calls your
        activity's onOptionsItemSelected() callback method, and passes a MenuItem object to indicate which item was clicked.
        In your implementation of onOptionsItemSelected(), call the MenuItem.getItemId() method to determine which item was pressed.
        The ID returned matches the value you declared in the corresponding <item> element's android:id attribute.
        */
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_afegir) {

            startActivity( new Intent( this,inserir_llibre.class ) );
            return super.onOptionsItemSelected(item);
        }

        if (id == R.id.btnCancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    public void MostraLlibre(Cursor c)
    {
        Toast.makeText(this, "Titol: "+ c.getString(0) + "\n" + "Autor: "+ c.getString(1) , Toast.LENGTH_SHORT).show();
    }



    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

}
