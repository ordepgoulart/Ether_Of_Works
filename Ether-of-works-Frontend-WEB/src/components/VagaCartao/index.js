//importar o react que traz todos os recursos dele
import React from 'react';
import * as Styl from './styles'

function VagaCartao({registro, cargo, empresa, remuneracao, jornada, cidade, estado, regime, editarClick, apagarClick}) {
  return (
    <Styl.Container>
      <Styl.Info>
        <Styl.Topo>
          <h3>{cargo}</h3>
          <small>#{registro}</small>
        </Styl.Topo>
        <span className="linha">
          {empresa != undefined ? empresa.nome_fantasia : "Empresa não informada"}
          <span className="divisor"/>
          <strong>{remuneracao}</strong>
        </span>
        <Styl.Etiquetas>
          {
            empresa != undefined && empresa.tipo != undefined ?
            <span className="destaque">{empresa.tipo}</span> : null
          }
          <span>{jornada}</span>
          <span>{regime}</span>
          <span>{cidade} - {estado}</span>
        </Styl.Etiquetas>
      </Styl.Info>
      <Styl.Botoes>
        <button type="button" className="editar" onClick={editarClick}>Editar</button>
        <button type="button" className="apagar" onClick={apagarClick}>Apagar</button>
      </Styl.Botoes>
    </Styl.Container>
  );
}
export default VagaCartao;
