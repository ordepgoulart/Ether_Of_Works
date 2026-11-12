package com.example.ether_of_work;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ether_of_work.RetrofitConfigs.RetrofitConfig;
import com.example.ether_of_work.components.VagaAdapter;
import com.example.ether_of_work.entities.Vaga;
import com.example.ether_of_work.utils.JsonUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements VagaAdapter.Acoes {

    private final List<Vaga> todas = new ArrayList<>();

    private VagaAdapter adapter;
    private TextView tvStatus;
    private LinearLayout layoutEmptyState;
    private EditText edtBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvStatus = findViewById(R.id.tvStatus);
        layoutEmptyState = findViewById(R.id.layoutEmptyState);
        edtBusca = findViewById(R.id.edtBusca);

        adapter = new VagaAdapter(this);

        RecyclerView recyclerVagas = findViewById(R.id.recyclerVagas);
        recyclerVagas.setLayoutManager(new LinearLayoutManager(this));
        recyclerVagas.setAdapter(adapter);

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

        findViewById(R.id.fabNova).setOnClickListener(v -> abrirFormulario(null));
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarVagas();
    }

    private void carregarVagas() {
        tvStatus.setText("Carregando vagas...");
        RetrofitConfig.getInstance().getEtherService().getAllVagas().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                String corpo = JsonUtil.corpo(response.body());
                if (response.isSuccessful()) {
                    List<Vaga> vagas = JsonUtil.vagas(corpo);
                    todas.clear();
                    todas.addAll(vagas);
                    aplicarFiltro();
                    if (vagas.isEmpty()) {
                        String mensagem = JsonUtil.mensagem(corpo);
                        tvStatus.setText(mensagem.isEmpty() ? "Nenhuma vaga cadastrada." : mensagem);
                        layoutEmptyState.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvStatus.setText("Erro ao carregar vagas");
                    mostrarMensagem(JsonUtil.mensagem(corpo));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                tvStatus.setText("Falha na conexão com o servidor");
                mostrarMensagem("Verifique se o backend está rodando e se a URL base está correta.");
            }
        });
    }

    private void aplicarFiltro() {
        String termo = edtBusca.getText().toString().trim().toLowerCase(Locale.ROOT);
        List<Vaga> filtradas = new ArrayList<>();
        for (Vaga vaga : todas) {
            if (corresponde(vaga, termo)) {
                filtradas.add(vaga);
            }
        }
        adapter.setVagas(filtradas);
        layoutEmptyState.setVisibility(filtradas.isEmpty() ? View.VISIBLE : View.GONE);
        if (!todas.isEmpty()) {
            tvStatus.setText(filtradas.size() + " vaga(s) encontrada(s)");
        }
    }

    private boolean corresponde(Vaga vaga, String termo) {
        String cargo = vaga.getCargo() == null ? "" : vaga.getCargo().toLowerCase(Locale.ROOT);
        String empresa = "";
        if (vaga.getEmpresa() != null && vaga.getEmpresa().getNomeFantasia() != null) {
            empresa = vaga.getEmpresa().getNomeFantasia().toLowerCase(Locale.ROOT);
        }
        String cidade = vaga.getCidade() == null ? "" : vaga.getCidade().toLowerCase(Locale.ROOT);
        String estado = vaga.getEstado() == null ? "" : vaga.getEstado().toLowerCase(Locale.ROOT);
        return termo.isEmpty()
                || cargo.contains(termo)
                || empresa.contains(termo)
                || cidade.contains(termo)
                || estado.contains(termo);
    }

    @Override
    public void aoSelecionar(Vaga vaga) {
        Intent intent = new Intent(this, VagaDetalheActivity.class);
        intent.putExtra(VagaDetalheActivity.EXTRA_VAGA, JsonUtil.json(vaga));
        startActivity(intent);
    }

    @Override
    public void aoInteressar(Vaga vaga) {
        Intent intent = new Intent(this, InteresseActivity.class);
        intent.putExtra(InteresseActivity.EXTRA_VAGA, JsonUtil.json(vaga));
        startActivity(intent);
    }

    @Override
    public void aoEditar(Vaga vaga) {
        abrirFormulario(vaga);
    }

    @Override
    public void aoExcluir(Vaga vaga) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir vaga")
                .setMessage("Deseja excluir a vaga '" + vaga.getCargo() + "'?")
                .setPositiveButton("Excluir", (dialog, which) -> confirmarExclusao(vaga))
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void abrirFormulario(Vaga vaga) {
        Intent intent = new Intent(this, VagaFormActivity.class);
        if (vaga != null) {
            intent.putExtra(VagaFormActivity.EXTRA_VAGA, JsonUtil.json(vaga));
        }
        startActivity(intent);
    }

    private void confirmarExclusao(Vaga vaga) {
        RetrofitConfig.getInstance().getEtherService().deleteVaga(vaga.getRegistro()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                String mensagem = JsonUtil.mensagem(JsonUtil.corpo(response.body()));
                if (response.isSuccessful()) {
                    mostrarMensagem(mensagem.isEmpty() ? "Vaga excluída com sucesso." : mensagem);
                    carregarVagas();
                } else {
                    mostrarMensagem(mensagem.isEmpty() ? "Não foi possível excluir a vaga." : mensagem);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mostrarMensagem("Falha na conexão com o servidor.");
            }
        });
    }

    private void mostrarMensagem(String mensagem) {
        Snackbar.make(findViewById(R.id.main), mensagem, Snackbar.LENGTH_LONG).show();
    }
}
