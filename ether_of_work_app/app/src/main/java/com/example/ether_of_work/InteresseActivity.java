package com.example.ether_of_work;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ether_of_work.RetrofitConfigs.RetrofitConfig;
import com.example.ether_of_work.dto.Erro;
import com.example.ether_of_work.entities.Candidato;
import com.example.ether_of_work.entities.Interessado;
import com.example.ether_of_work.entities.Vaga;
import com.example.ether_of_work.utils.JsonUtil;
import com.example.ether_of_work.utils.MascaraUtil;
import com.google.android.material.snackbar.Snackbar;

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
        EdgeToEdge.enable(this, SystemBarStyle.dark(Color.TRANSPARENT), SystemBarStyle.dark(Color.TRANSPARENT));
        setContentView(R.layout.activity_interesse);

        // ime --> o teclado não cobre os campos
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        edtEmail = findViewById(R.id.edtEmail);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtFormacao = findViewById(R.id.edtFormacao);
        btnEnviar = findViewById(R.id.btnEnviar);

        // formata enquanto o usuário digita: 000.000.000-00 e (00) 00000-0000
        MascaraUtil.cpf(edtCpf);
        MascaraUtil.telefone(edtTelefone);

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

    // mostra no topo a vaga para a qual o interesse será enviado
    private void preencherResumo() {
        TextView tvVagaCargo = findViewById(R.id.tvVagaCargo);
        TextView tvVagaEmpresa = findViewById(R.id.tvVagaEmpresa);

        String cargo = vaga.getCargo() == null ? "" : vaga.getCargo().trim();
        String empresa = vaga.nomeEmpresa();
        String local = vaga.montarLocal();

        String detalhe = empresa;
        if (!local.isEmpty()) {
            detalhe = detalhe.isEmpty() ? local : detalhe + " | " + local;
        }

        tvVagaCargo.setText(cargo.isEmpty() ? "Vaga sem cargo" : cargo);
        tvVagaEmpresa.setText(detalhe);
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
            btnEnviar.setText("Enviando...");

            Call<ResponseBody> call = RetrofitConfig.getInstance().getEtherService().addInteressado(interessado);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    liberarBotao();
                    String mensagem = JsonUtil.mensagem(JsonUtil.corpo(response));

                    if (response.isSuccessful()) {
                        // o Toast continua na tela mesmo depois do finish()
                        Toast.makeText(InteresseActivity.this, "Interesse registrado com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                    } else if (response.code() == 404) {
                        avisar("Rota não encontrada no servidor: " + RetrofitConfig.BASE_URL + "add-interessado");
                    } else if (mensagem.isEmpty()) {
                        avisar("Não foi possível registrar o interesse (código " + response.code() + ").");
                    } else {
                        avisar(mensagem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    liberarBotao();
                    Snackbar.make(findViewById(R.id.main), "Falha na conexão com o servidor: " + t.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Tentar novamente", v -> enviar())
                            .show();
                }
            });
        }
    }

    private void liberarBotao() {
        btnEnviar.setEnabled(true);
        btnEnviar.setText("Enviar interesse");
    }

    private String texto(EditText campo) {
        return campo.getText().toString().trim();
    }

    private void avisar(String mensagem) {
        Snackbar.make(findViewById(R.id.main), mensagem, Snackbar.LENGTH_LONG).show();
    }
}
