package com.example.ether_of_work_backend.repositories;

import com.example.ether_of_work_backend.entities.Cargo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargosRepository extends MongoRepository<Cargo, String> {
}
