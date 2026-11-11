package com.example.ether_of_work_backend.services;

import com.example.ether_of_work_backend.DTO.Erro;
import com.example.ether_of_work_backend.entities.Cargo;
import com.example.ether_of_work_backend.entities.Empresa;
import com.example.ether_of_work_backend.entities.Vaga;
import com.example.ether_of_work_backend.repositories.CargosRepository;
import com.example.ether_of_work_backend.repositories.EmpresasRepository;
import com.example.ether_of_work_backend.repositories.InteressadosRepository;
import com.example.ether_of_work_backend.repositories.VagasRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class EtherServices {

    @Autowired
    private CargosRepository cargosRepository;
    @Autowired
    private EmpresasRepository empresasRepository;
    @Autowired
    private InteressadosRepository interessadosRepository;
    @Autowired
    private VagasRepository vagasRepository;

    public List<Cargo> get_all_cargos(){
        List<Cargo> cargoList = cargosRepository.findAll();
        return cargoList;
    }

    public List<Empresa> get_all_empresas()
    {
        List<Empresa> empresasList = empresasRepository.findAll();
        return empresasList;
    }

    public List<Empresa> get_empresas_by_tipo(String filtro)
    {
        List<Empresa> empresasList = empresasRepository.findByTipoIgnoreCase(filtro);
        return empresasList;
    }

    public boolean delete_vaga(String registro)
    {
        Long ok = vagasRepository.deleteByRegistro(registro);
        return ok > 0;
    }

    public Vaga get_vaga(String registro)
    {
        Vaga vaga = vagasRepository.findByRegistro(registro);
        return vaga;
    }

    public List<Vaga> get_all_vagas() {
        List<Vaga> vagaList = vagasRepository.findAll();
        return vagaList;
    }

    public Erro add_vaga(Vaga vaga){
        Erro erro = vaga.validar();
        if(erro.isSucess()){
            try
            {
                Vaga aux_vaga = new Vaga();
                aux_vaga = vagasRepository.findByRegistro(vaga.getRegistro());
                if (aux_vaga == null || aux_vaga.getRegistro().isEmpty()) {
                    aux_vaga = vagasRepository.save(vaga);
                    erro.setSucess(true);
                }
                else
                {
                    erro.setMensage("Uma vaga com esse registro já foi cadastrada.");
                    erro.setSucess(false);
                }
            }
            catch (Exception e)
            {
                return new Erro("Algo inesperado ocorreu : " + e.getMessage(), false);
            }
        }

        return erro;
    }

    public Erro update_vaga(Vaga vaga){
        Erro erro = vaga.validar();
        if(erro.isSucess()){
            try
            {
                Vaga aux_vaga = new Vaga();
                aux_vaga = vagasRepository.findByRegistro(vaga.getRegistro());
                if (aux_vaga != null) {
                    aux_vaga = vagasRepository.save(vaga);
                    erro.setSucess(true);
                }
                else
                {
                    erro.setMensage("A vaga com esse registro não foi encontrada.");
                    erro.setSucess(false);
                }
            }
            catch (Exception e)
            {
                return new Erro("Algo inesperado ocorreu : " + e.getMessage(), false);
            }
        }

        return erro;
    }
}
