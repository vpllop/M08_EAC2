package cat.valen.eac2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class detall_llibre extends AppCompatActivity {

    DBInterface bd;
    Adaptador mAdapter;
    TextView txtTitol, txtAutor, txtEditorial,txtTipus, txtPreu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detall_llibre );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        txtTitol = (TextView) findViewById(R.id.dtxtTitol);
        txtAutor = (TextView) findViewById(R.id.dtxtAutor);
        txtEditorial = (TextView) findViewById(R.id.dtxtEditorial);
        txtTipus = (TextView) findViewById(R.id.dtxtTapa);
        txtPreu = (TextView) findViewById(R.id.dtxtPreu);

        Intent intent = getIntent();
        if (null != intent) {
            //Obtenció del bundle enviat
            Bundle extras = intent.getExtras();
            //Obtenció del nom i guardat en un string
            String test = extras.getString("TITOL");
            txtTitol.setText( extras.getString("TITOL"));
            txtAutor.setText( extras.getString("AUTOR"));
            txtEditorial.setText( extras.getString("EDITORIAL"));
            txtTipus.setText(  extras.getString("TIPUS"));
            txtPreu.setText( String.valueOf(  extras.getInt("PREU")));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_detall, menu );

       /* MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
*/
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
        final TextView titolTxt = (TextView) findViewById(R.id.dtxtTitol);
        final String titol = titolTxt.getText().toString();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_eliminar) {
            //Obrim la base dedades
            bd = new DBInterface(this);
            bd.obre();
             AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( this);
            // set title
            alertDialogBuilder.setTitle("Eliminar");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Eliminar Llibre?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity

                            //Inserim el contacte.
                            if (bd.esborraLlibre( titol ))

                                //Toast.makeText(this, "Afegit correctament", Toast.LENGTH_SHORT).show();
                            //else
                                //Toast.makeText(this, "Error a l'afegir", Toast.LENGTH_SHORT).show();

                            bd.tanca();
                            finish();

                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return super.onOptionsItemSelected(item);
        }

        //Action quan es fa click al icone de detalls de la barra per modificar el llibre
        if (id == R.id.action_detall) {
            Intent i = new Intent( this,modificar_llibre.class );
            //Obrim la base dedades
            bd = new DBInterface(this);
            bd.obre();
            Cursor c = bd.obtenirLlibre(titol  );

            Bundle dades = new Bundle(  );
            dades.putString( "TITOL",c.getString(0) );
            dades.putString( "AUTOR",c.getString(1) );
            dades.putString( "EDITORIAL",c.getString(2) );
            dades.putString( "TIPUS",c.getString(3) );
            dades.putInt( "PREU",Integer.parseInt(  c.getString(4)) );

            i.putExtras( dades );
            startActivity( i );
            return super.onOptionsItemSelected(item);
        }
       /*if (id == R.id.btnTornar) {
            finish();
            return true;
        }*/

        return super.onOptionsItemSelected( item );
    }


}
