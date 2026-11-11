import React from 'react';
import * as Styl from './styles'

function CargoTabela({cargos, cargoAtivo, cargoClick}) {
  return (
    <Styl.Container>
        <Styl.Titulo>
            <h2>Tabela de cargos</h2>
            <span>{cargos.length}</span>
        </Styl.Titulo>
        <Styl.Tabela>
            <tbody>
            {
                cargos.length == 0 ?
                <tr><td className="vazio">Nenhum cargo encontrado.</td></tr>
                :
                cargos.map((c, index)=>(
                    <tr key={index}
                    className={cargoAtivo == c.nome ? 'ativo' : ''}
                    title="Clique para filtrar as vagas por este cargo"
                    onClick={()=>cargoClick(c.nome)}>
                        <td>{c.nome}</td>
                    </tr>
                ))
            }
            </tbody>
        </Styl.Tabela>
    </Styl.Container>
  );
}
export default CargoTabela;
