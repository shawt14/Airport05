package com.example.airport05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Voo> {
    public ItemAdapter(Context context, List<Voo> voos){
        super(context, 0, voos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Voo voo = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item, parent, false);
        }

        TextView tvNumber = convertView.findViewById(R.id.tv_flight_number);
        TextView tvOrigin = convertView.findViewById(R.id.tv_origin);
        TextView tvCompany = convertView.findViewById(R.id.tv_company);
        TextView tvTerminal = convertView.findViewById(R.id.tv_terminal);
        TextView tvTimes = convertView.findViewById(R.id.tv_times);

        tvNumber.setText(voo.GetNum_Voo());
        tvOrigin.setText(voo.GetOrigem());
        tvCompany.setText(voo.GetCompanhia());
        tvTerminal.setText(voo.GetTerminal());

        String timeInfo = "Previsto: " + voo.GetChegada_Prevista();

        if(voo.GetChegada_Final() != null)
        {
            timeInfo += "\nReal: " + voo.GetChegada_Final();
        }
        tvTimes.setText(timeInfo);

        return convertView;
    }
}
