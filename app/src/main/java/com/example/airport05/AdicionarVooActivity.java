package com.example.airport05;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdicionarVooActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_voo);

        EditText etOrigem = findViewById(R.id.etOrigem);
        EditText etNumVoo = findViewById(R.id.etNumVoo);
        EditText etPartida = findViewById(R.id.etPartida);
        EditText etChegadaPrevista = findViewById(R.id.etChegadaPrevista);
        EditText etChegadaFinal = findViewById(R.id.etChegadaFinal);
        EditText etData = findViewById(R.id.etData);
        EditText etCompanhia = findViewById(R.id.etCompanhia);
        EditText etTerminal = findViewById(R.id.etTerminal);

        Button btnAdicionar = findViewById(R.id.btnAdicionar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        btnAdicionar.setOnClickListener(v -> {
            String origem = etOrigem.getText().toString().trim();
            String numVoo = etNumVoo.getText().toString().trim();
            String partida = etPartida.getText().toString().trim();
            String chegadaPrevista = etChegadaPrevista.getText().toString().trim();
            String chegadaFinal = etChegadaFinal.getText().toString().trim();
            String data = etData.getText().toString().trim();
            String companhia = etCompanhia.getText().toString().trim();
            String terminal = etTerminal.getText().toString().trim();

            if (origem.isEmpty() || numVoo.isEmpty()) {
                Toast.makeText(this, "Origem e número do voo são obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            Voo novoVoo = new Voo(origem, numVoo, partida, chegadaPrevista, chegadaFinal, data, companhia, terminal);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("NOVO_VOO", novoVoo);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
