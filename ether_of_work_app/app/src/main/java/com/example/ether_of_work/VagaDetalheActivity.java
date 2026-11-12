package com.example.ether_of_work;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ether_of_work.entities.Vaga;
import com.example.ether_of_work.utils.JsonUtil;

import java.util.Locale;

public class VagaDetalheActivity extends AppCompatActivity {

    public static final String EXTRA_VAGA = "vaga_json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaga_detalhe);

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        Vaga vaga = lerVaga();
        if (vaga == null) {
            Toast.makeText(this, "Não foi possível abrir a vaga.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            preencher(vaga);
            findViewById(R.id.btnInteresse).setOnClickListener(v -> abrirInteresse(vaga));
            findViewById(R.id.btnEditar).setOnClickListener(v -> abrirEdicao(vaga));
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
        TextView tvEmpresa = findViewById(R.id.tvEmpresa);
        TextView tvRegime = findViewById(R.id.tvRegime);
        TextView tvLocal = findViewById(R.id.tvLocal);
        TextView tvRemuneracao = findViewById(R.id.tvRemuneracao);
        TextView tvJornada = findViewById(R.id.tvJornada);
        TextView tvFormacao = findViewById(R.id.tvFormacao);
        TextView tvPreRequisitos = findViewById(R.id.tvPreRequisitos);
        TextView tvConhecimentos = findViewById(R.id.tvConhecimentos);

        String empresa = "";
        if (vaga.getEmpresa() != null && vaga.getEmpresa().getNomeFantasia() != null) {
            empresa = vaga.getEmpresa().getNomeFantasia();
        }

        tvCargo.setText(valor(vaga.getCargo()));
        tvEmpresa.setText(empresa);
        tvRegime.setText(valor(vaga.getRegime()).toUpperCase(Locale.ROOT));
        tvLocal.setText(montarLocal(vaga));
        tvRemuneracao.setText(valor(vaga.getRemuneracao()));
        tvJornada.setText(valor(vaga.getJornadaTrabalho()));
        tvFormacao.setText(valor(vaga.getFormacao()));
        tvPreRequisitos.setText(valor(vaga.getPreRequisitos()));
        tvConhecimentos.setText(valor(vaga.getConhecimentosRequeridos()));
    }

    private String montarLocal(Vaga vaga) {
        String cidade = valor(vaga.getCidade());
        String estado = valor(vaga.getEstado()).toUpperCase(Locale.ROOT);
        String local = "";
        if (!cidade.isEmpty() && !estado.isEmpty()) {
            local = cidade + " - " + estado;
        } else if (!cidade.isEmpty()) {
            local = cidade;
        } else if (!estado.isEmpty()) {
            local = estado;
        }
        return local;
    }

    private void abrirInteresse(Vaga vaga) {
        Intent intent = new Intent(this, InteresseActivity.class);
        intent.putExtra(InteresseActivity.EXTRA_VAGA, JsonUtil.json(vaga));
        startActivity(intent);
    }

    private void abrirEdicao(Vaga vaga) {
        Intent intent = new Intent(this, VagaFormActivity.class);
        intent.putExtra(VagaFormActivity.EXTRA_VAGA, JsonUtil.json(vaga));
        startActivity(intent);
        finish();
    }

    private String valor(String texto) {
        return texto == null ? "" : texto;
    }
}
