package cat.valen.eac2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;


public class Adaptador extends RecyclerView.Adapter<Adaptador.ElMeuViewHolder>  implements Filterable {
    //private ArrayList<String> items;
    private ArrayList<Llibre> mllibres;
    private ArrayList<Llibre> mFilteredList;
    private Context context;

    //Creem el constructor
    public Adaptador(Context context, ArrayList<Llibre> Llibres) {
        this.context = context;
        this.mllibres= Llibres;
        this.mFilteredList = Llibres;
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
        return mFilteredList.size();
    }

    //Carreguem els widgets amb les dades (l'invoca el layout manager)
    @Override
    public void onBindViewHolder(ElMeuViewHolder viewHolder, int position) {
        /* *
         * position conté la posició de l'element actual a la llista. També l'utilitzarem
         * com a índex per a recòrrer les dades
         * */
        viewHolder.vTitle.setText(mFilteredList.get(position).getTitol());
        viewHolder.vAutor.setText(mFilteredList.get(position).getAutor());
    }
    // Definim el filtre quan es fa la busqueda dels llibres al contingut del RecylcerVIew
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilteredList = mllibres;
                } else {

                    ArrayList<Llibre> filteredList = new ArrayList<>();
                    for (Llibre llibre : mllibres) {
                        if (llibre.getTitol().toLowerCase().contains(charString)) {
                            filteredList.add(llibre);
                        }
                    }
                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Llibre>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //Definim el nostre ViewHolder, és a dir, un element de la llista en qüestió
    public static class ElMeuViewHolder extends RecyclerView.ViewHolder {
        //Widgets del viewHolder
        protected TextView vTitle;
        protected TextView vAutor;

        public ElMeuViewHolder(View v) {
            super(v);
            //El referenciem al layout
            vTitle = (TextView) v.findViewById(R.id.title);
            vAutor = (TextView) v.findViewById(R.id.autor);
        }

    }

    public void setLlibres(ArrayList<Llibre> Llibres) {
        this.mllibres= Llibres;

    }
}
