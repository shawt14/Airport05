package com.example.airport05;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VooFragment.OnVooSelectedListener {

    private static final int REQUEST_DETALHES = 1;
    private static final int REQUEST_ADICIONAR = 2;

    private ArrayList<Voo> lista_voos;
    private VooPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lista_voos = new ArrayList<>();
        inicializar();

        ViewPager viewPager = findViewById(R.id.viewpager);
        pagerAdapter = new VooPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

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

    public ArrayList<Voo> getListaVoos() {
        return lista_voos;
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode == REQUEST_DETALHES && resultcode == RESULT_OK && data != null && data.hasExtra("POS_ELIMINAR")) {
            int pos = data.getIntExtra("POS_ELIMINAR", -1);
            if (pos != -1) {
                lista_voos.remove(pos);
                refreshFragments();
            }
        } else if (requestcode == REQUEST_ADICIONAR && resultcode == RESULT_OK && data != null && data.hasExtra("NOVO_VOO")) {
            Voo novoVoo = (Voo) data.getSerializableExtra("NOVO_VOO");
            if (novoVoo != null) {
                lista_voos.add(novoVoo);
                refreshFragments();
            }
        }
    }

    private void refreshFragments() {
        pagerAdapter.getChegadasFragment().refresh();
        pagerAdapter.getPartidasFragment().refresh();
    }

    @Override
    public void onVooSelected(Voo voo, int posicaoCompleta) {
        Intent intent = new Intent(this, DetalheVooActivity.class);
        intent.putExtra("VOO_OBJ", voo);
        intent.putExtra("POSICAO", posicaoCompleta);
        startActivityForResult(intent, REQUEST_DETALHES);
    }

    private void inicializar() {
        lista_voos.add(new Voo("Londres", "Lisboa", "TP124", "07:00", "09:30", "09:45", "26/02/2026", "TAP Air Portugal", "Terminal 1", "Chegada"));
        lista_voos.add(new Voo("Paris", "Porto", "TP457", "08:15", "10:00", "10:10", "26/02/2026", "TAP Air Portugal", "Terminal 2", "Chegada"));
        lista_voos.add(new Voo("Madrid", "Faro", "VY790", "06:30", "08:45", "", "26/02/2026", "Vueling", "Terminal 1", "Chegada"));
        lista_voos.add(new Voo("Lisboa", "Londres", "TP123", "08:00", "10:30", "10:45", "26/02/2026", "TAP Air Portugal", "Terminal 1", "Partida"));
        lista_voos.add(new Voo("Porto", "Paris", "TP456", "09:15", "11:00", "11:10", "26/02/2026", "TAP Air Portugal", "Terminal 2", "Partida"));
        lista_voos.add(new Voo("Faro", "Madrid", "TP789", "07:30", "09:45", "10:00", "26/02/2026", "Ryanair", "Terminal 1", "Partida"));
    }
}
