package cat.valen.eac2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class modificar_llibre extends AppCompatActivity implements View.OnClickListener{

    DBInterface bd;
    Button btnModificar;
    Button btnCancelar;
    EditText editTitol, editAutor, editEditorial, editPreu;
    Spinner EditTipus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_modificar_llibre );

        // Referenciem els botons i els widgets dels layouts
        btnModificar = (Button) findViewById(R.id.btnModificarLlibre);
        btnModificar.setOnClickListener(this);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        editTitol = (EditText) findViewById(R.id.editTitol);
        editAutor = (EditText) findViewById(R.id.editAutor);
        editEditorial = (EditText) findViewById(R.id.editEditorial);
        EditTipus = (Spinner) findViewById(R.id.spinner);
        editPreu = (EditText) findViewById(R.id.editPreu);

        //Obenim el Intent que ha iniciat l’activitat i extraiem el string
        Intent intent = getIntent();
        //Obtenció del bundle enviat
        Bundle extras = intent.getExtras();
        //Obtenció del nom i guardat en un string
        editTitol.setText( extras.getString("TITOL"));
        editAutor.setText( extras.getString("AUTOR"));
        editEditorial.setText( extras.getString("EDITORIAL"));
        //EditTipus.setSelection( Integer.parseInt(  extras.getString("TIPUS")));
        editPreu.setText( String.valueOf( extras.getInt("PREU")));

    }

    @Override
    public void onClick(View v) {
        // Actualitzem el registre de la BDs del Llibre
        if(v == btnModificar)
        {
            //Obrim la base dedades
            bd = new DBInterface(this);
            bd.obre();

            //Inserim el contacte.
            if(bd.actualitzarLlibre(editTitol.getText().toString(), editAutor.getText().toString(),
                    editEditorial.getText().toString(),EditTipus.getSelectedItem().toString(),
                    Integer.parseInt(editPreu.getText().toString())))

                Toast.makeText(this, "Actualitzat correctament", Toast.LENGTH_SHORT).show();

            else
                Toast.makeText(this, "Error a l'afegir", Toast.LENGTH_SHORT).show();
            bd.tanca();
            finish();
        }

        if(v == btnCancelar)
        {
            finish();
        }
    }
}
