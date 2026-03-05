package com.example.airport05;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetalheVooActivity extends AppCompatActivity {

    private static final int REQUEST_EDITAR = 10;

    private Voo voo;
    private int posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhe_voo);

        voo = (Voo) getIntent().getSerializableExtra("VOO_OBJ");
        posicao = getIntent().getIntExtra("POSICAO", -1);

        atualizarVista();

        findViewById(R.id.btnOK).setOnClickListener(v -> finish());

        findViewById(R.id.btnEditar).setOnClickListener(v -> {
            Intent intent = new Intent(DetalheVooActivity.this, EditarVooActivity.class);
            intent.putExtra("VOO_OBJ", voo);
            intent.putExtra("POSICAO", posicao);
            startActivityForResult(intent, REQUEST_EDITAR);
        });

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

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode == REQUEST_EDITAR && resultcode == RESULT_OK && data != null && data.hasExtra("VOO_EDITADO")) {
            voo = (Voo) data.getSerializableExtra("VOO_EDITADO");
            atualizarVista();
            // Propagate the update so MainActivity refreshes its list when this activity finishes
            Intent resultIntent = new Intent();
            resultIntent.putExtra("VOO_EDITADO", voo);
            resultIntent.putExtra("POSICAO", posicao);
            setResult(RESULT_OK, resultIntent);
        }
    }

    private void atualizarVista() {
        TextView txtOrigem = findViewById(R.id.detalheOrigem);
        txtOrigem.setText(voo.GetOrigem() + " -> " + voo.GetDestino() + " (" + voo.GetNum_Voo() + ")");

        TextView txtInfo = findViewById(R.id.detalheInfo);
        txtInfo.setText("Tipo: " + voo.GetTipo() + "\nPartida: " + voo.GetPartida() + "  Previsto: " + voo.GetChegada_Prevista() + "  Real: " + voo.GetChegada_Final() + "\nData: " + voo.GetData() + "\nCompanhia: " + voo.GetCompanhia() + "\nTerminal: " + voo.GetTerminal());
    }
}
