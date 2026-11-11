package com.example.ether_of_work_backend.repositories;

import com.example.ether_of_work_backend.entities.Vaga;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagasRepository extends MongoRepository<Vaga, String> {

    Long deleteByRegistro(String registro);

    Vaga findByRegistro(String registro);
}
