package cat.valen.eac2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ElMeuViewHolder>  {
    private ArrayList<String> items;
    private Context context;

    //Creem el constructor
    public Adaptador(Context context, ArrayList<String> items) {
        this.context = context;
        this.items= items;
    }
    //Crea noves files (l'invoca el layout manager). Aquí fem referència al layout fila.xml
    @Override
    public Adaptador.ElMeuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fila, null);
        // create ViewHolder
        ElMeuViewHolder viewHolder = new ElMeuViewHolder(itemLayoutView);
        return viewHolder;
    }

    //Retorna la quantitat de les dades
    @Override
    public int getItemCount() {
        return items.size();
    }

    //Carreguem els widgets amb les dades (l'invoca el layout manager)
    @Override
    public void onBindViewHolder(ElMeuViewHolder viewHolder, int position) {
        /* *
         * position conté la posició de l'element actual a la llista. També l'utilitzarem
         * com a índex per a recòrrer les dades
         * */
        viewHolder.vTitle.setText(items.get(position));
    }
    //Definim el nostre ViewHolder, és a dir, un element de la llista en qüestió
    public static class ElMeuViewHolder extends RecyclerView.ViewHolder {
        //Només conté un TextView
        protected TextView vTitle;
        public ElMeuViewHolder(View v) {
            super(v);
            //El referenciem al layout
            vTitle = (TextView) v.findViewById(R.id.title);
        }
    }
}
