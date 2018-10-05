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

    @Override
    public void onClick(View v) {
        if(v == btnInserir)
        {
            //Obrim la base dedades
            bd = new DBInterface(this);
            bd.obre();

            //Inserim el contacte.
            if(bd.insereixContacte(editTitol.getText().toString(), editAutor.getText().toString(),
                    editEditorial.getText().toString(),EditTipus.getSelectedItem().toString(),
                    Integer.parseInt(editPreu.getText().toString()))  != -1)
                Toast.makeText(this, "Afegit correctament", Toast.LENGTH_SHORT).show();
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
