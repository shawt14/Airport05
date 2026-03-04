package com.example.airport05;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class DetalheVooActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhe_voo);

        Voo voo = (Voo) getIntent().getSerializableExtra(("VOO_OBJ"));
        int posicao = getIntent().getIntExtra("POSICAO", -1);

        TextView txtOrigem = findViewById(R.id.detalheOrigem);
        txtOrigem.setText((voo.GetOrigem() + " (" + voo.GetNum_Voo() + ")"));

        TextView txtInfo = findViewById(R.id.detalheInfo);
        txtInfo.setText("Partida: " + voo.GetPartida() + "  Previsto: " + voo.GetChegada_Prevista() + "  Real: " + voo.GetChegada_Final() + "\nData: " + voo.GetData());

        SeekBar seekBar = findViewById(R.id.seekBarProgresso);
        seekBar.setProgress(calcularProgresso(voo.GetPartida(), voo.GetChegada_Prevista()));

        findViewById(R.id.btnOK).setOnClickListener(v -> finish());

        findViewById(R.id.btnEliminar).setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("POS_ELIMINAR", posicao);
            setResult(RESULT_OK, resultIntent);
            finish();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private int calcularProgresso(String partida, String chegada) {
        try {
            String[] p = partida.split(":");
            String[] c = chegada.split(":");
            int minPartida = Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
            int minChegada = Integer.parseInt(c[0]) * 60 + Integer.parseInt(c[1]);
            if (minChegada <= minPartida) return 0;
            Calendar agora = Calendar.getInstance();
            int minAgora = agora.get(Calendar.HOUR_OF_DAY) * 60 + agora.get(Calendar.MINUTE);
            int progresso = (int) ((minAgora - minPartida) * 100L / (minChegada - minPartida));
            return Math.max(0, Math.min(100, progresso));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}