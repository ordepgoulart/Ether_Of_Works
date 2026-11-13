package com.example.ether_of_work.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ether_of_work.R;
import com.example.ether_of_work.entities.Vaga;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VagaAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<Vaga> vagas;

    public VagaAdapter(Context context, List<Vaga> vagas) {
        this.vagas = (vagas != null) ? vagas : new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    public void setVagas(List<Vaga> novasVagas) {
        this.vagas = (novasVagas != null) ? novasVagas : new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return vagas.size();
    }

    @Override
    public Vaga getItem(int position) {
        return (position >= 0 && position < vagas.size()) ? vagas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_vaga, parent, false);
            holder = new ViewHolder();
            holder.tvCargo = convertView.findViewById(R.id.tvCargo);
            holder.tvRegistro = convertView.findViewById(R.id.tvRegistro);
            holder.tvEmpresa = convertView.findViewById(R.id.tvEmpresa);
            holder.tvRemuneracao = convertView.findViewById(R.id.tvRemuneracao);
            holder.tvTipo = convertView.findViewById(R.id.tvTipo);
            holder.tvJornada = convertView.findViewById(R.id.tvJornada);
            holder.tvRegime = convertView.findViewById(R.id.tvRegime);
            holder.tvLocal = convertView.findViewById(R.id.tvLocal);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        Vaga vaga = getItem(position);
        if (vaga != null) {
            String cargo = texto(vaga.getCargo());
            String empresa = vaga.nomeEmpresa();

            holder.tvCargo.setText(cargo.isEmpty() ? "Vaga sem cargo" : cargo);
            holder.tvRegistro.setText("#" + texto(vaga.getRegistro()));
            holder.tvEmpresa.setText(empresa.isEmpty() ? "Empresa não informada" : empresa);
            holder.tvRemuneracao.setText(texto(vaga.getRemuneracao()));

            // etiquetas do cartão (somem quando o campo está vazio)
            etiqueta(holder.tvTipo, vaga.tipoEmpresa());
            etiqueta(holder.tvJornada, texto(vaga.getJornadaTrabalho()));
            etiqueta(holder.tvRegime, texto(vaga.getRegime()).toUpperCase(Locale.ROOT));
            etiqueta(holder.tvLocal, vaga.montarLocal());
        }

        return convertView;
    }

    private void etiqueta(TextView tv, String valor) {
        tv.setText(valor);
        tv.setVisibility(valor.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private String texto(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private static class ViewHolder {
        TextView tvCargo;
        TextView tvRegistro;
        TextView tvEmpresa;
        TextView tvRemuneracao;
        TextView tvTipo;
        TextView tvJornada;
        TextView tvRegime;
        TextView tvLocal;
    }
}
