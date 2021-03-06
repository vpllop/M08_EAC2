package cat.valen.eac2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
// https://developer.android.com/training/data-storage/sqlite
// adb shell , sqlite3 data/data/cat.valen.DBllibres/databases/llibres.db

public class DBInterface {
    //Constants
    public static final String CLAU_TITOL = "titol";
    public static final String CLAU_AUTOR = "autor";
    public static final String CLAU_EDITORIAL = "editorial";
    public static final String CLAU_TIPUS_TAPA = "tipus_tapa";
    public static final String CLAU_PREU = "preu";

    public static final String TAG = "DBInterface";

    public static final String BD_NOM = "BDLlibres";
    public static final String BD_TAULA = "llibres";
    public static final int VERSIO = 1;

    public static final String BD_CREATE =
            "create table " + BD_TAULA + "( " + CLAU_TITOL + " text not null, " +
                    CLAU_AUTOR + " text not null, " + CLAU_EDITORIAL + " text not null, " +
                    CLAU_TIPUS_TAPA + " text not null, " + CLAU_PREU + " integer)";

    private final Context context;
    private AjudaBD ajuda;
    private SQLiteDatabase bd;

    public DBInterface(Context con)
    {
        this.context = con;
        ajuda = new AjudaBD(context);
    }

    //Obre la BD

    public DBInterface obre() throws SQLException {
        bd = ajuda.getWritableDatabase();
        return this;
    }

//Tanca la BD

    public void tanca() {
        ajuda.close();
    }

    //Insereix un contacte

    public long insereixLlibre(String titol, String autor, String editorial, String tapa,int preu)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CLAU_TITOL, titol);
        initialValues.put(CLAU_AUTOR, autor);
        initialValues.put(CLAU_EDITORIAL, editorial);
        initialValues.put(CLAU_TIPUS_TAPA, tapa);
        initialValues.put(CLAU_PREU, preu);

        return bd.insert(BD_TAULA ,null, initialValues);
    }

    //Esborra un contacte

    public boolean esborraLlibre(String idTitol)
    {
        return bd.delete(BD_TAULA, CLAU_TITOL + " = '" + idTitol + "'", null) > 0;
    }

    //Retorna un contacte

    public Cursor obtenirLlibre(String idTitol) throws SQLException {
        Cursor mCursor = bd.query(true, BD_TAULA, new String[] {CLAU_TITOL, CLAU_AUTOR, CLAU_EDITORIAL,CLAU_TIPUS_TAPA,CLAU_PREU},
                CLAU_TITOL + " like '" + idTitol + "%'", null, null, null, null, null);

        if(mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    //Retorna tots els contactes

    public Cursor obtenirTotsElsLlibres()
    {
        return bd.query(BD_TAULA, new String[] {CLAU_TITOL, CLAU_AUTOR,CLAU_EDITORIAL,CLAU_TIPUS_TAPA,CLAU_PREU}, null, null, null, null, null);
    }

    //Modifica un Llibre

    public boolean actualitzarLlibre(String titol, String autor, String editorial,String tipus, int preu) {
        ContentValues args = new ContentValues();
        args.put(CLAU_TITOL, titol);
        args.put(CLAU_AUTOR, autor);
        args.put(CLAU_EDITORIAL, editorial);
        args.put(CLAU_TIPUS_TAPA, tipus);
        args.put(CLAU_PREU, preu);
        return bd.update(BD_TAULA, args, CLAU_TITOL + " = '" + titol + "'", null) > 0;
    }
}
