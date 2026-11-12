package com.example.ether_of_work.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ether_of_work.R;
import com.example.ether_of_work.entities.Vaga;

import java.util.ArrayList;
import java.util.List;

public class VagaAdapter extends RecyclerView.Adapter<VagaAdapter.VagaViewHolder> {

    public interface Acoes {
        void aoSelecionar(Vaga vaga);

        void aoInteressar(Vaga vaga);

        void aoEditar(Vaga vaga);

        void aoExcluir(Vaga vaga);
    }

    private final List<Vaga> vagas = new ArrayList<>();
    private final Acoes acoes;

    public VagaAdapter(Acoes acoes) {
        this.acoes = acoes;
    }

    public void setVagas(List<Vaga> novas) {
        vagas.clear();
        if (novas != null) {
            vagas.addAll(novas);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VagaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vaga, parent, false);
        return new VagaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VagaViewHolder holder, int position) {
        holder.vincular(vagas.get(position), acoes);
    }

    @Override
    public int getItemCount() {
        return vagas.size();
    }

    static class VagaViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCargo;
        private final TextView tvEmpresa;
        private final TextView tvRegime;
        private final TextView tvLocal;
        private final TextView tvJornada;
        private final TextView tvRemuneracao;
        private final View btnInteresse;
        private final View btnEditar;
        private final View btnExcluir;

        VagaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCargo = itemView.findViewById(R.id.tvCargo);
            tvEmpresa = itemView.findViewById(R.id.tvEmpresa);
            tvRegime = itemView.findViewById(R.id.tvRegime);
            tvLocal = itemView.findViewById(R.id.tvLocal);
            tvJornada = itemView.findViewById(R.id.tvJornada);
            tvRemuneracao = itemView.findViewById(R.id.tvRemuneracao);
            btnInteresse = itemView.findViewById(R.id.btnInteresse);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnExcluir = itemView.findViewById(R.id.btnExcluir);
        }

        void vincular(Vaga vaga, Acoes acoes) {
            String cargo = vaga.getCargo() == null ? "" : vaga.getCargo().trim();
            String empresa = "";
            if (vaga.getEmpresa() != null && vaga.getEmpresa().getNomeFantasia() != null) {
                empresa = vaga.getEmpresa().getNomeFantasia().trim();
            }
            String regime = vaga.getRegime() == null ? "" : vaga.getRegime().trim().toUpperCase();
            String jornada = vaga.getJornadaTrabalho() == null ? "" : vaga.getJornadaTrabalho().trim();
            String remuneracao = vaga.getRemuneracao() == null ? "" : vaga.getRemuneracao().trim();

            tvCargo.setText(cargo.isEmpty() ? "Vaga sem cargo" : cargo);
            tvEmpresa.setText(empresa.isEmpty() ? "Empresa não informada" : empresa);
            tvRegime.setText(regime);
            tvLocal.setText(montarLocal(vaga));
            tvJornada.setText(jornada);
            tvRemuneracao.setText(remuneracao);

            itemView.setOnClickListener(v -> acoes.aoSelecionar(vaga));
            btnInteresse.setOnClickListener(v -> acoes.aoInteressar(vaga));
            btnEditar.setOnClickListener(v -> acoes.aoEditar(vaga));
            btnExcluir.setOnClickListener(v -> acoes.aoExcluir(vaga));
        }

        private String montarLocal(Vaga vaga) {
            String cidade = vaga.getCidade() == null ? "" : vaga.getCidade().trim();
            String estado = vaga.getEstado() == null ? "" : vaga.getEstado().trim().toUpperCase();
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
    }
}
