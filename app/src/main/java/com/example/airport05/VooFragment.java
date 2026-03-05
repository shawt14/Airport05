package com.example.airport05;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class VooFragment extends Fragment {

    private static final String ARG_TIPO = "tipo";

    public interface OnVooSelectedListener {
        void onVooSelected(Voo voo, int posicaoCompleta);
    }

    private String tipo;
    private ArrayList<Voo> listaCompleta;
    private ArrayList<Voo> listaFiltrada;
    private ItemAdapter adapter;
    private OnVooSelectedListener listener;

    public static VooFragment newInstance(String tipo) {
        VooFragment fragment = new VooFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TIPO, tipo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listaCompleta = ((MainActivity) context).getListaVoos();
        listener = (OnVooSelectedListener) context;
        tipo = getArguments().getString(ARG_TIPO);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voo_list, container, false);

        listaFiltrada = new ArrayList<>();
        buildFilteredList();

        adapter = new ItemAdapter(getContext(), listaFiltrada);

        ListView listView = view.findViewById(R.id.fragment_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, v, position, id) -> {
            Voo vooSelecionado = listaFiltrada.get(position);
            int posicaoCompleta = listaCompleta.indexOf(vooSelecionado);
            listener.onVooSelected(vooSelecionado, posicaoCompleta);
        });

        return view;
    }

    private void buildFilteredList() {
        listaFiltrada.clear();
        for (Voo v : listaCompleta) {
            if (tipo.equals(v.GetTipo())) {
                listaFiltrada.add(v);
            }
        }
        if ("Partida".equals(tipo)) {
            listaFiltrada.sort((a, b) -> {
                String ta = a.GetPartida() != null ? a.GetPartida() : "";
                String tb = b.GetPartida() != null ? b.GetPartida() : "";
                return ta.compareTo(tb);
            });
        } else {
            listaFiltrada.sort((a, b) -> {
                String ta = a.GetChegada_Prevista() != null ? a.GetChegada_Prevista() : "";
                String tb = b.GetChegada_Prevista() != null ? b.GetChegada_Prevista() : "";
                return ta.compareTo(tb);
            });
        }
    }

    public void refresh() {
        if (listaFiltrada != null && adapter != null) {
            buildFilteredList();
            adapter.notifyDataSetChanged();
        }
    }
}
