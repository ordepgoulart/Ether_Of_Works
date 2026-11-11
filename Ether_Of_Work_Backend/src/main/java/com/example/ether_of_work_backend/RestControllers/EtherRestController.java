package com.example.ether_of_work_backend.RestControllers;

import com.example.ether_of_work_backend.DTO.Erro;
import com.example.ether_of_work_backend.entities.Cargo;
import com.example.ether_of_work_backend.entities.Empresa;
import com.example.ether_of_work_backend.entities.Vaga;
import com.example.ether_of_work_backend.repositories.CargosRepository;
import com.example.ether_of_work_backend.services.EtherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("ether")
public class EtherRestController {

    @Autowired
    private EtherServices etherServices;

    @GetMapping("/")
    public ResponseEntity<Object> rota_teste(){
        return ResponseEntity.ok("Servidor Online.");
    }

    @GetMapping("get-all-cargos")
    public ResponseEntity<Object> get_all_cargos(){
        List<Cargo> cargoList = etherServices.get_all_cargos();
        if(cargoList.isEmpty())
            return ResponseEntity.ok("Não há cargos cadastrados.");
        return ResponseEntity.ok(cargoList);
    }

    @GetMapping("get-all-empresas")
    public ResponseEntity<Object> get_all_empresas(){
        List<Empresa> empresasList = etherServices.get_all_empresas();
        if(empresasList.isEmpty())
            return ResponseEntity.ok("Não há empresas cadastradas.");
        return ResponseEntity.ok(empresasList);
    }

    @GetMapping("get-empresas-by-tipo/{filtro}")
    public ResponseEntity<Object> get_empresas_by_tipo(@PathVariable String filtro){
        List<Empresa> empresasList = etherServices.get_empresas_by_tipo(filtro);
        if(empresasList.isEmpty())
            return ResponseEntity.ok("Não há empresas cadastradas para o filtro de tipo '" + filtro + "'.");
        return ResponseEntity.ok(empresasList);
    }

    @DeleteMapping("delete-vaga/{registro}")
    public ResponseEntity<Object> delete_vaga(@PathVariable String registro){
        Boolean ok = etherServices.delete_vaga(registro);
        if(!ok)
            return ResponseEntity.ok("Não foi possível deletar a vaga de registro '" + registro + "'.");
        return ResponseEntity.ok("Vaga deletada com sucesso.");
    }

    @GetMapping("get-all-vagas")
    public ResponseEntity<Object> get_all_vagas(){
        List<Vaga> vagaList = etherServices.get_all_vagas();
        if(vagaList.isEmpty())
            return ResponseEntity.ok("Não há vagas cadastradas.");
        return ResponseEntity.ok(vagaList);
    }

    @PostMapping("add-vaga")
    public ResponseEntity<Object> add_vaga(@RequestBody Vaga vaga){
        Erro response = etherServices.add_vaga(vaga);
        if(response.isSucess())
            return ResponseEntity.ok("A vaga foi cadastrada com sucesso");
        return ResponseEntity.badRequest().body(response.getMensage());
    }

    @PutMapping("update-vaga")
    public ResponseEntity<Object> update_vaga(@RequestBody Vaga vaga){
        Erro response = etherServices.update_vaga(vaga);
        if(response.isSucess())
            return ResponseEntity.ok("A vaga foi atualizada com sucesso");
        return ResponseEntity.badRequest().body(response.getMensage());
    }
}
