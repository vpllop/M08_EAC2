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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBInterface bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );


        //Preparem la font de les dades
        ArrayList<String> dades = new ArrayList<String>();
        //Referenciem el RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rView);

        //afegim l'adaptador amb les dades i el LinearLayoutManager que pintarà les dades
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adaptador adapter = new Adaptador(this, dades);
        recyclerView.setAdapter(adapter);

        //Podem utilitzar animacions sobre la llista
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Obrim la base dedades
        bd = new DBInterface(this);
        bd.obre();

        //Crida la BD per obtenir tots els contactes
        Cursor c = bd.obtenirTotsElsContactes();

        //Movem el cursor a la primera posició
        if (c.moveToFirst()) {
            do {
        //Mostrem contactes...
                MostraLlibre(c);
        //... mentre puguem passar al següent contacte
            } while (c.moveToNext());
        }


        Toast.makeText(this, "Tots els contactes mostrats", Toast.LENGTH_SHORT).show();

        //Carreguem de dades
        c.moveToFirst();
        for(int i = 1; i <= c.getCount(); i++){
            dades.add(new String("Dada " + i));
            dades.add(new String( "Titol: "+ c.getString(0) ));
            dades.add(new String( "Autor: "+ c.getString(1) ));
            c.moveToNext();
        }

        //Després de cada modificació a la font de les dades, hem de notificar-ho a l'adaptador
        adapter.notifyDataSetChanged();
        //Tanquem la BD
        bd.tanca();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_main, menu );

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Configure the search info and add any event listeners...

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
}
