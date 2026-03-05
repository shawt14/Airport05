package com.example.airport05;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class EditarVooActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_voo);

        Voo voo = (Voo) getIntent().getSerializableExtra("VOO_OBJ");
        int posicao = getIntent().getIntExtra("POSICAO", -1);

        EditText etOrigem = findViewById(R.id.etOrigem);
        EditText etDestino = findViewById(R.id.etDestino);
        EditText etNumVoo = findViewById(R.id.etNumVoo);
        EditText etPartida = findViewById(R.id.etPartida);
        EditText etChegadaPrevista = findViewById(R.id.etChegadaPrevista);
        EditText etChegadaFinal = findViewById(R.id.etChegadaFinal);
        EditText etData = findViewById(R.id.etData);
        EditText etCompanhia = findViewById(R.id.etCompanhia);
        EditText etTerminal = findViewById(R.id.etTerminal);
        RadioGroup rgTipo = findViewById(R.id.rgTipo);
        Button btnAdicionar = findViewById(R.id.btnAdicionar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        // Pre-fill all fields with existing flight data
        if (voo != null) {
            etOrigem.setText(voo.GetOrigem());
            etDestino.setText(voo.GetDestino());
            etNumVoo.setText(voo.GetNum_Voo());
            etPartida.setText(voo.GetPartida());
            etChegadaPrevista.setText(voo.GetChegada_Prevista());
            etChegadaFinal.setText(voo.GetChegada_Final());
            etData.setText(voo.GetData());
            etCompanhia.setText(voo.GetCompanhia());
            etTerminal.setText(voo.GetTerminal());
            if ("Chegada".equals(voo.GetTipo())) {
                rgTipo.check(R.id.rbChegada);
            } else {
                rgTipo.check(R.id.rbPartida);
            }
        }

        btnAdicionar.setText(R.string.save_label);

        btnAdicionar.setOnClickListener(v -> {
            String origem = etOrigem.getText().toString().trim();
            String destino = etDestino.getText().toString().trim();
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

            boolean valid = true;

            if (partida.isEmpty() || !isValidTime(partida)) {
                etPartida.setError("Hora inválida (ex: 08:00)");
                valid = false;
            }

            if (chegadaPrevista.isEmpty() || !isValidTime(chegadaPrevista)) {
                etChegadaPrevista.setError("Hora inválida (ex: 10:30)");
                valid = false;
            }

            if (!chegadaFinal.isEmpty() && !isValidTime(chegadaFinal)) {
                etChegadaFinal.setError("Hora inválida (ex: 10:45)");
                valid = false;
            }

            if (data.isEmpty() || !isValidDate(data)) {
                etData.setError("Data inválida (ex: 26/02/2026)");
                valid = false;
            }

            int selectedTipoId = rgTipo.getCheckedRadioButtonId();
            if (selectedTipoId == -1) {
                Toast.makeText(this, "Selecione o tipo de movimento (Chegada ou Partida)", Toast.LENGTH_SHORT).show();
                valid = false;
            }

            if (!valid) return;

            String tipo = (selectedTipoId == R.id.rbChegada) ? "Chegada" : "Partida";

            Voo vooEditado = new Voo(origem, destino, numVoo, partida, chegadaPrevista, chegadaFinal, data, companhia, terminal, tipo);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("VOO_EDITADO", vooEditado);
            resultIntent.putExtra("POSICAO", posicao);
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

    private boolean isValidTime(String value) {
        if (!value.matches("\\d{2}:\\d{2}")) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        sdf.setLenient(false);
        try {
            sdf.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isValidDate(String value) {
        if (!value.matches("\\d{2}/\\d{2}/\\d{4}")) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        sdf.setLenient(false);
        try {
            sdf.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
