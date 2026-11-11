package com.example.ether_of_work_backend.repositories;

import com.example.ether_of_work_backend.entities.Interessado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteressadosRepository extends MongoRepository<Interessado, String> {
}
