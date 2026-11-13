package com.example.ether_of_work;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ether_of_work.entities.Vaga;
import com.example.ether_of_work.utils.JsonUtil;

import java.util.Locale;

public class VagaDetalheActivity extends AppCompatActivity {

    public static final String EXTRA_VAGA = "vaga_json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this, SystemBarStyle.dark(Color.TRANSPARENT), SystemBarStyle.dark(Color.TRANSPARENT));
        setContentView(R.layout.activity_vaga_detalhe);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        Vaga vaga = lerVaga();
        if (vaga == null) {
            Toast.makeText(this, "Não foi possível abrir a vaga.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            preencher(vaga);
            findViewById(R.id.btnInteresse).setOnClickListener(v -> abrirInteresse(vaga));
        }
    }

    private Vaga lerVaga() {
        Vaga vaga = null;
        if (getIntent().hasExtra(EXTRA_VAGA)) {
            vaga = JsonUtil.vaga(getIntent().getStringExtra(EXTRA_VAGA));
        }
        return vaga;
    }

    private void preencher(Vaga vaga) {
        TextView tvCargo = findViewById(R.id.tvCargo);
        TextView tvRegistro = findViewById(R.id.tvRegistro);
        TextView tvEmpresa = findViewById(R.id.tvEmpresa);
        TextView tvRemuneracao = findViewById(R.id.tvRemuneracao);
        TextView tvFormacao = findViewById(R.id.tvFormacao);
        TextView tvPreRequisitos = findViewById(R.id.tvPreRequisitos);
        TextView tvConhecimentos = findViewById(R.id.tvConhecimentos);

        String empresa = vaga.nomeEmpresa();

        tvCargo.setText(valor(vaga.getCargo()));
        tvRegistro.setText("#" + valor(vaga.getRegistro()));
        tvEmpresa.setText(empresa.isEmpty() ? "Empresa não informada" : empresa);
        tvRemuneracao.setText(valor(vaga.getRemuneracao()));
        tvFormacao.setText(valor(vaga.getFormacao()));
        tvPreRequisitos.setText(valor(vaga.getPreRequisitos()));
        tvConhecimentos.setText(valor(vaga.getConhecimentosRequeridos()));

        etiqueta(findViewById(R.id.tvTipo), vaga.tipoEmpresa());
        etiqueta(findViewById(R.id.tvRegime), valor(vaga.getRegime()).toUpperCase(Locale.ROOT));
        etiqueta(findViewById(R.id.tvJornada), valor(vaga.getJornadaTrabalho()));
        etiqueta(findViewById(R.id.tvLocal), vaga.montarLocal());
    }

    private void abrirInteresse(Vaga vaga) {
        Intent intent = new Intent(this, InteresseActivity.class);
        intent.putExtra(InteresseActivity.EXTRA_VAGA, JsonUtil.json(vaga));
        startActivity(intent);
    }

    private void etiqueta(TextView tv, String texto) {
        tv.setText(texto);
        tv.setVisibility(texto.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private String valor(String texto) {
        return texto == null ? "" : texto.trim();
    }
}
