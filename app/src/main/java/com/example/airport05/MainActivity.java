package com.example.airport05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Voo> lista_voos;
    private ListView listview;
    private ItemAdapter adapter;

    private static final int REQUEST_DETALHES = 1;
    private static final int REQUEST_ADICIONAR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lista_voos = new ArrayList<>();
        inicializar();

        adapter = new ItemAdapter(this, lista_voos);

        listview = findViewById(R.id.flight_list_view);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Voo vooSelecionado = lista_voos.get(position);

                Intent intent = new Intent(MainActivity.this, DetalheVooActivity.class);

                intent.putExtra("VOO_OBJ", vooSelecionado);
                intent.putExtra("POSICAO", position);
                startActivityForResult(intent, REQUEST_DETALHES);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabAdicionarVoo);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdicionarVooActivity.class);
            startActivityForResult(intent, REQUEST_ADICIONAR);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode == REQUEST_DETALHES && resultcode == RESULT_OK && data != null && data.hasExtra("POS_ELIMINAR")) {
            int pos  = data.getIntExtra("POS_ELIMINAR", -1);
            if(pos != -1)
            {
                lista_voos.remove(pos);
                adapter.notifyDataSetChanged();
            }
        } else if (requestcode == REQUEST_ADICIONAR && resultcode == RESULT_OK && data != null && data.hasExtra("NOVO_VOO")) {
            Voo novoVoo = (Voo) data.getSerializableExtra("NOVO_VOO");
            if (novoVoo != null) {
                lista_voos.add(novoVoo);
                adapter.notifyDataSetChanged();
            }
        }
    }
    private void inicializar() {
        lista_voos.add(new Voo("Lisboa", "Londres", "TP123", "08:00", "10:30", "10:45", "26/02/2026", "TAP Air Portugal", "Terminal 1"));
        lista_voos.add(new Voo("Porto", "Paris", "TP456", "09:15", "11:00", "11:10", "26/02/2026", "TAP Air Portugal", "Terminal 2"));
        lista_voos.add(new Voo("Faro", "Madrid", "TP789", "07:30", "09:45", "10:00", "26/02/2026", "Ryanair", "Terminal 1"));
    }
}