package com.example.ether_of_work_backend.DTO;

import com.example.ether_of_work_backend.entities.Cargo;
import com.example.ether_of_work_backend.entities.Empresa;

public record DTO_Vaga(String registro, String cargo, String cidade, String estado, String pre_requisitos, String formacao, String conhecimentos_requeridos, String regime, String jornada_trabalho, String remuneracao, Empresa empresa) {
}
