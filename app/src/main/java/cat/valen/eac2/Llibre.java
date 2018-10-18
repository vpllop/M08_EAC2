package cat.valen.eac2;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Objects;

public class Llibre {
    String titol;
    String autor;
    String editorial;
    String tipus;
    int preu;



    public Llibre() {

    }

    public Llibre(String titol, String autor, String editorial, String tipus, int preu) {
        this.titol = titol;
        this.autor = autor;
        this.editorial = editorial;
        this.tipus = tipus;
        this.preu = preu;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public int getPreu() {
        return preu;
    }

    public void setPreu(int preu) {
        this.preu = preu;
    }

    public static ArrayList<Llibre> getLlibres(Context con) {
        DBInterface bd;
        ArrayList<Llibre> llibres = new ArrayList<>(  );
        //Obrim la base dedades
        bd = new DBInterface (con);
        bd.obre();

        //Crida la BD per obtenir tots els contactes
        Cursor c = bd.obtenirTotsElsLlibres();
        //Per cada registre de la BDs afegim un llibre a la Llista
        c.moveToFirst();
        for(int i = 1; i <= c.getCount(); i++){
            Llibre llibre = new Llibre();
            llibre.setTitol( c.getString(0) );
            llibre.setAutor( c.getString(1) );
            llibre.setEditorial( c.getString(2) );
            llibre.setTipus( c.getString(3) );
            llibre.setPreu( Integer.parseInt(  c.getString(4)) );

            llibres.add( llibre );
            c.moveToNext();
        }


        //Tanquem la BD
        bd.tanca();
        return llibres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Llibre llibre = (Llibre) o;
        return Objects.equals( titol, llibre.titol );
    }

    @Override
    public int hashCode() {

        return Objects.hash( titol );
    }
}
