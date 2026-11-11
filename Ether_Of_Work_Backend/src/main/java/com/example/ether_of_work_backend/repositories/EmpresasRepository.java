package com.example.ether_of_work_backend.repositories;

import com.example.ether_of_work_backend.entities.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresasRepository extends MongoRepository<Empresa, String> {

    public List<Empresa> findByTipoIgnoreCase(String tipo);

}
