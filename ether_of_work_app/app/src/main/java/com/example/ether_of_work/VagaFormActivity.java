package com.example.ether_of_work;

import android.os.Bundle;
import android.text.InputFilter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ether_of_work.RetrofitConfigs.RetrofitConfig;
import com.example.ether_of_work.Services.EtherService;
import com.example.ether_of_work.dto.Erro;
import com.example.ether_of_work.entities.Cargo;
import com.example.ether_of_work.entities.Empresa;
import com.example.ether_of_work.entities.Vaga;
import com.example.ether_of_work.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VagaFormActivity extends AppCompatActivity {

    public static final String EXTRA_VAGA = "vaga_json";

    private final List<Empresa> empresas = new ArrayList<>();
    private final List<Cargo> cargos = new ArrayList<>();

    private Vaga vagaEdicao;

    private TextView tvTitulo;
    private EditText edtRegistro;
    private EditText edtCidade;
    private EditText edtEstado;
    private EditText edtJornada;
    private EditText edtRemuneracao;
    private EditText edtFormacao;
    private EditText edtPreRequisitos;
    private EditText edtConhecimentos;
    private Spinner spEmpresa;
    private Spinner spCargo;
    private Spinner spRegime;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaga_form);

        tvTitulo = findViewById(R.id.tvTitulo);
        edtRegistro = findViewById(R.id.edtRegistro);
        edtCidade = findViewById(R.id.edtCidade);
        edtEstado = findViewById(R.id.edtEstado);
        edtJornada = findViewById(R.id.edtJornada);
        edtRemuneracao = findViewById(R.id.edtRemuneracao);
        edtFormacao = findViewById(R.id.edtFormacao);
        edtPreRequisitos = findViewById(R.id.edtPreRequisitos);
        edtConhecimentos = findViewById(R.id.edtConhecimentos);
        spEmpresa = findViewById(R.id.spEmpresa);
        spCargo = findViewById(R.id.spCargo);
        spRegime = findViewById(R.id.spRegime);
        btnSalvar = findViewById(R.id.btnSalvar);

        vagaEdicao = lerVaga();

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());
        btnSalvar.setOnClickListener(v -> salvar());

        configurarRegime();
        configurarEstado();

        if (vagaEdicao != null) {
            tvTitulo.setText("Editar vaga");
            edtRegistro.setEnabled(false);
            preencherCampos();
        }

        carregarEmpresas();
    }

    private Vaga lerVaga() {
        Vaga vaga = null;
        if (getIntent().hasExtra(EXTRA_VAGA)) {
            vaga = JsonUtil.vaga(getIntent().getStringExtra(EXTRA_VAGA));
        }
        return vaga;
    }

    private void configurarRegime() {
        List<String> regimes = new ArrayList<>();
        regimes.add("CLT");
        regimes.add("PJ");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, regimes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRegime.setAdapter(adapter);

        if (vagaEdicao != null && vagaEdicao.getRegime() != null) {
            String regime = vagaEdicao.getRegime().trim().toUpperCase(Locale.ROOT);
            int posicao = regimes.indexOf(regime);
            if (posicao >= 0) {
                spRegime.setSelection(posicao);
            }
        }
    }

    private void configurarEstado() {
        InputFilter[] filtros = new InputFilter[]{
                new InputFilter.LengthFilter(2),
                (source, start, end, dest, dstart, dend) -> source.toString().toUpperCase(Locale.ROOT)
        };
        edtEstado.setFilters(filtros);
    }

    private void preencherCampos() {
        edtRegistro.setText(valor(vagaEdicao.getRegistro()));
        edtCidade.setText(valor(vagaEdicao.getCidade()));
        edtEstado.setText(valor(vagaEdicao.getEstado()).toUpperCase(Locale.ROOT));
        edtJornada.setText(valor(vagaEdicao.getJornadaTrabalho()));
        edtRemuneracao.setText(valor(vagaEdicao.getRemuneracao()));
        edtFormacao.setText(valor(vagaEdicao.getFormacao()));
        edtPreRequisitos.setText(valor(vagaEdicao.getPreRequisitos()));
        edtConhecimentos.setText(valor(vagaEdicao.getConhecimentosRequeridos()));
    }

    private void carregarEmpresas() {
        RetrofitConfig.getInstance().getEtherService().getAllEmpresas().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                String corpo = JsonUtil.corpo(response.body());
                if (response.isSuccessful()) {
                    empresas.clear();
                    empresas.addAll(JsonUtil.empresas(corpo));
                    preencherEmpresas();
                    if (empresas.isEmpty()) {
                        avisar("Nenhuma empresa cadastrada no sistema.");
                    }
                } else {
                    avisar(JsonUtil.mensagem(corpo));
                }
                carregarCargos();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                avisar("Não foi possível carregar as empresas.");
                carregarCargos();
            }
        });
    }

    private void preencherEmpresas() {
        List<String> nomes = new ArrayList<>();
        for (Empresa empresa : empresas) {
            String nome = empresa.getNomeFantasia() == null ? "" : empresa.getNomeFantasia().trim();
            nomes.add(nome);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmpresa.setAdapter(adapter);

        if (vagaEdicao != null && vagaEdicao.getEmpresa() != null) {
            int posicao = nomes.indexOf(vagaEdicao.getEmpresa().getNomeFantasia());
            if (posicao >= 0) {
                spEmpresa.setSelection(posicao);
            }
        }
    }

    private void carregarCargos() {
        RetrofitConfig.getInstance().getEtherService().getAllCargos().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                String corpo = JsonUtil.corpo(response.body());
                if (response.isSuccessful()) {
                    cargos.clear();
                    cargos.addAll(JsonUtil.cargos(corpo));
                    preencherCargos();
                    if (cargos.isEmpty()) {
                        avisar("Nenhum cargo cadastrado no sistema.");
                    }
                } else {
                    avisar(JsonUtil.mensagem(corpo));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                avisar("Não foi possível carregar os cargos.");
            }
        });
    }

    private void preencherCargos() {
        List<String> nomes = new ArrayList<>();
        for (Cargo cargo : cargos) {
            String nome = cargo.getNome() == null ? "" : cargo.getNome().trim();
            nomes.add(nome);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCargo.setAdapter(adapter);

        if (vagaEdicao != null && vagaEdicao.getCargo() != null) {
            int posicao = nomes.indexOf(vagaEdicao.getCargo());
            if (posicao >= 0) {
                spCargo.setSelection(posicao);
            }
        }
    }

    private void salvar() {
        Vaga vaga = montarVaga();
        Erro erro = vaga.validar();

        if (!erro.isSucesso()) {
            avisar(erro.getMensagem());
        } else {
            btnSalvar.setEnabled(false);
            EtherService service = RetrofitConfig.getInstance().getEtherService();
            Call<ResponseBody> chamada = vagaEdicao == null ? service.addVaga(vaga) : service.updateVaga(vaga);

            chamada.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    btnSalvar.setEnabled(true);
                    String mensagem = JsonUtil.mensagem(JsonUtil.corpo(response.body()));
                    if (response.isSuccessful()) {
                        avisar(mensagem.isEmpty() ? "Vaga salva com sucesso." : mensagem);
                        finish();
                    } else {
                        avisar(mensagem.isEmpty() ? "Não foi possível salvar a vaga." : mensagem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    btnSalvar.setEnabled(true);
                    avisar("Falha na conexão com o servidor.");
                }
            });
        }
    }

    private Vaga montarVaga() {
        Vaga vaga = new Vaga();
        vaga.setRegistro(texto(edtRegistro));
        vaga.setCargo(cargoSelecionado());
        vaga.setCidade(texto(edtCidade));
        vaga.setEstado(texto(edtEstado).toUpperCase(Locale.ROOT));
        vaga.setPreRequisitos(texto(edtPreRequisitos));
        vaga.setFormacao(texto(edtFormacao));
        vaga.setConhecimentosRequeridos(texto(edtConhecimentos));
        vaga.setRegime(regimeSelecionado());
        vaga.setJornadaTrabalho(texto(edtJornada));
        vaga.setRemuneracao(texto(edtRemuneracao));
        vaga.setEmpresa(empresaSelecionada());
        if (vagaEdicao != null) {
            vaga.setId(vagaEdicao.getId() != null ? vagaEdicao.getId() : vagaEdicao.getIdMongo());
            vaga.setIdMongo(vagaEdicao.getIdMongo());
        }
        return vaga;
    }

    private Empresa empresaSelecionada() {
        int posicao = spEmpresa.getSelectedItemPosition();
        Empresa empresa = null;
        if (posicao >= 0 && posicao < empresas.size()) {
            empresa = empresas.get(posicao);
        }
        return empresa;
    }

    private String cargoSelecionado() {
        int posicao = spCargo.getSelectedItemPosition();
        String cargo = "";
        if (posicao >= 0 && posicao < cargos.size()) {
            cargo = cargos.get(posicao).getNome() == null ? "" : cargos.get(posicao).getNome();
        }
        return cargo;
    }

    private String regimeSelecionado() {
        Object item = spRegime.getSelectedItem();
        return item == null ? "" : item.toString();
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
