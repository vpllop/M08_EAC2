package cat.valen.eac2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class inserir_llibre extends AppCompatActivity implements View.OnClickListener {

    DBInterface bd;
    Button btnInserir;
    Button btnCancelar;
    EditText editTitol, editAutor, editEditorial, editPreu;
    Spinner EditTipus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_inserir_llibre );
        // Referenciem els botons i els widgets dels layouts
        btnInserir = (Button) findViewById(R.id.btnAfegirLlibre);
        btnInserir.setOnClickListener(this);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        editTitol = (EditText) findViewById(R.id.editTitol);
        editAutor = (EditText) findViewById(R.id.editAutor);
        editEditorial = (EditText) findViewById(R.id.editEditorial);
        EditTipus = (Spinner) findViewById(R.id.spinner);
        editPreu = (EditText) findViewById(R.id.editPreu);
    }
    // Sobreescriu el metode onClick de la clase View.OnClickListener
    @Override
    public void onClick(View v) {
        // Segons el boto seleccionat defineix una acció
        if(v == btnInserir)
        {
            String myTitol = editTitol.getText().toString();
            String myAutor =  editAutor.getText().toString();
            String myEditorial = editEditorial.getText().toString();
            String myTipus = EditTipus.getSelectedItem().toString();
            int myPreu = (editPreu.getText().length() > 0) ? Integer.parseInt(editPreu.getText().toString()) :0;

            if (!myTitol.isEmpty() || !myAutor.isEmpty()) {
                //Obrim la base dedades
                bd = new DBInterface(this);
                bd.obre();

                //Inserim el contacte.
                if(bd.insereixLlibre(editTitol.getText().toString(), editAutor.getText().toString(),
                        editEditorial.getText().toString(),EditTipus.getSelectedItem().toString(),
                        Integer.parseInt(editPreu.getText().toString()))  != -1)
                    Toast.makeText(this, "Afegit correctament", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Error a l'afegir", Toast.LENGTH_SHORT).show();
                bd.tanca();
                finish();
            } else {
                Toast.makeText(this, "Tots els camps ha d'estar omplerts i el preu numeric", Toast.LENGTH_SHORT).show();
            }

        }

        if(v == btnCancelar)
        {
            finish();
        }
    }
}
