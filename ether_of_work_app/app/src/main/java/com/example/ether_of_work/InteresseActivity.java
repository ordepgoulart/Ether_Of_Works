package com.example.ether_of_work;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ether_of_work.RetrofitConfigs.RetrofitConfig;
import com.example.ether_of_work.dto.Erro;
import com.example.ether_of_work.entities.Candidato;
import com.example.ether_of_work.entities.Interessado;
import com.example.ether_of_work.entities.Vaga;
import com.example.ether_of_work.utils.JsonUtil;

import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InteresseActivity extends AppCompatActivity {

    public static final String EXTRA_VAGA = "vaga_json";

    private Vaga vaga;

    private EditText edtNome;
    private EditText edtCpf;
    private EditText edtEmail;
    private EditText edtTelefone;
    private EditText edtFormacao;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interesse);

        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        edtEmail = findViewById(R.id.edtEmail);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtFormacao = findViewById(R.id.edtFormacao);
        btnEnviar = findViewById(R.id.btnEnviar);

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        vaga = lerVaga();
        if (vaga == null) {
            Toast.makeText(this, "Não foi possível abrir a vaga.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            preencherResumo();
            btnEnviar.setOnClickListener(v -> enviar());
        }
    }

    private Vaga lerVaga() {
        Vaga vagaLida = null;
        if (getIntent().hasExtra(EXTRA_VAGA)) {
            vagaLida = JsonUtil.vaga(getIntent().getStringExtra(EXTRA_VAGA));
        }
        return vagaLida;
    }

    private void preencherResumo() {
        TextView tvVagaResumo = findViewById(R.id.tvVagaResumo);
        String cargo = valor(vaga.getCargo());
        String empresa = "";
        if (vaga.getEmpresa() != null && vaga.getEmpresa().getNomeFantasia() != null) {
            empresa = vaga.getEmpresa().getNomeFantasia();
        }
        String local = montarLocal();
        String resumo = cargo;
        if (!empresa.isEmpty()) {
            resumo = resumo + " - " + empresa;
        }
        if (!local.isEmpty()) {
            resumo = resumo + " (" + local + ")";
        }
        tvVagaResumo.setText(resumo);
    }

    private String montarLocal() {
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

    private void enviar() {
        Candidato candidato = new Candidato(
                texto(edtNome),
                texto(edtCpf),
                texto(edtEmail),
                texto(edtTelefone),
                texto(edtFormacao)
        );
        Interessado interessado = new Interessado(vaga, candidato);
        Erro erro = interessado.validar();

        if (!erro.isSucesso()) {
            avisar(erro.getMensagem());
        } else {
            btnEnviar.setEnabled(false);
            RetrofitConfig.getInstance().getEtherService().addInteressado(interessado).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    btnEnviar.setEnabled(true);
                    String mensagem = JsonUtil.mensagem(JsonUtil.corpo(response.body()));
                    if (response.isSuccessful()) {
                        avisar(mensagem.isEmpty() ? "Interesse registrado com sucesso." : mensagem);
                        finish();
                    } else {
                        avisar(mensagem.isEmpty() ? "Não foi possível registrar o interesse." : mensagem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    btnEnviar.setEnabled(true);
                    avisar("Falha na conexão com o servidor.");
                }
            });
        }
    }

    private String texto(EditText campo) {
        return campo.getText().toString().trim();
    }

    private String valor(String texto) {
        return texto == null ? "" : texto;
    }

    private void avisar(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }
}
