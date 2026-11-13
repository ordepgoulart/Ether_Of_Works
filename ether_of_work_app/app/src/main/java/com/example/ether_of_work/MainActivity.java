package com.example.ether_of_work;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ether_of_work.RetrofitConfigs.RetrofitConfig;
import com.example.ether_of_work.components.VagaAdapter;
import com.example.ether_of_work.entities.Vaga;
import com.example.ether_of_work.utils.JsonUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edtBusca;
    private ImageButton btnAtualizar;
    private TextView tvStatus;
    private TextView tvContador;
    private ListView lvVagas;
    private LinearLayout layoutEmptyState;
    private TextView tvEmptyTitulo;
    private TextView tvEmptyTexto;

    private VagaAdapter vagaAdapter;
    private final List<Vaga> todas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ícones claros na barra de status, por causa do fundo azul
        EdgeToEdge.enable(this, SystemBarStyle.dark(Color.TRANSPARENT), SystemBarStyle.dark(Color.TRANSPARENT));
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtBusca = findViewById(R.id.edtBusca);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        tvStatus = findViewById(R.id.tvStatus);
        tvContador = findViewById(R.id.tvContador);
        lvVagas = findViewById(R.id.lvVagas);
        layoutEmptyState = findViewById(R.id.layoutEmptyState);
        tvEmptyTitulo = findViewById(R.id.tvEmptyTitulo);
        tvEmptyTexto = findViewById(R.id.tvEmptyTexto);

        setupList();

        edtBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                aplicarFiltro();
            }
        });

        btnAtualizar.setOnClickListener(v -> carregarVagas());
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarVagas();
    }

    private void setupList() {
        vagaAdapter = new VagaAdapter(this, new ArrayList<>());
        lvVagas.setAdapter(vagaAdapter);

        lvVagas.setOnItemClickListener((parent, view, position, id) -> {
            Vaga vaga = vagaAdapter.getItem(position);
            if (vaga != null) {
                Intent intent = new Intent(this, VagaDetalheActivity.class);
                intent.putExtra(VagaDetalheActivity.EXTRA_VAGA, JsonUtil.json(vaga));
                startActivity(intent);
            }
        });
    }

    private void carregarVagas() {
        btnAtualizar.setEnabled(false);
        tvStatus.setText("Buscando vagas no servidor...");

        Call<ResponseBody> call = RetrofitConfig.getInstance().getEtherService().getAllVagas();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                btnAtualizar.setEnabled(true);
                String corpo = JsonUtil.corpo(response);

                if (response.isSuccessful()) {
                    todas.clear();
                    todas.addAll(JsonUtil.vagas(corpo));
                    aplicarFiltro();
                } else {
                    tvStatus.setText("Erro ao obter as vagas do servidor (código " + response.code() + ")");
                    Snackbar.make(findViewById(R.id.main), "Falha ao carregar a lista de vagas", Snackbar.LENGTH_LONG)
                            .setAction("Repetir", v -> carregarVagas())
                            .show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                btnAtualizar.setEnabled(true);
                tvStatus.setText("Falha na conexão com o servidor");
                mostrarVazio("Sem conexão com o servidor",
                        "Verifique se a web api está rodando em " + RetrofitConfig.BASE_URL);

                Snackbar.make(findViewById(R.id.main), "Erro na requisição: " + t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Tentar novamente", v -> carregarVagas())
                        .show();
            }
        });
    }

    private void aplicarFiltro() {
        String termo = edtBusca.getText().toString();
        List<Vaga> filtradas = new ArrayList<>();
        for (Vaga vaga : todas) {
            if (vaga.contem(termo)) {
                filtradas.add(vaga);
            }
        }
        vagaAdapter.setVagas(filtradas);
        tvContador.setText(String.valueOf(filtradas.size()));

        if (todas.isEmpty()) {
            tvStatus.setText("Nenhuma vaga cadastrada no momento");
            mostrarVazio("Nenhuma vaga por aqui", "Ainda não há vagas no panteão. Volte mais tarde.");
        } else if (filtradas.isEmpty()) {
            tvStatus.setText("Nenhuma vaga encontrada para a pesquisa");
            mostrarVazio("Nenhuma vaga encontrada", "Pesquise por outro cargo, empresa ou cidade.");
        } else {
            tvStatus.setText(filtradas.size() + " vaga(s) encontrada(s)");
            layoutEmptyState.setVisibility(View.GONE);
            lvVagas.setVisibility(View.VISIBLE);
        }
    }

    private void mostrarVazio(String titulo, String texto) {
        tvEmptyTitulo.setText(titulo);
        tvEmptyTexto.setText(texto);
        layoutEmptyState.setVisibility(View.VISIBLE);
        lvVagas.setVisibility(View.GONE);
    }
}
