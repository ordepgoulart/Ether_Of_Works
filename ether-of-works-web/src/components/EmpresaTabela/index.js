import React from 'react';
import * as Styl from './styles'

function EmpresaTabela({empresas, empresaAtiva, empresaClick}) {
  return (
    <Styl.Container>
        <Styl.Titulo>
            <h2>Tabela de empresas</h2>
            <span>{empresas.length}</span>
        </Styl.Titulo>
        <Styl.Tabela>
            <tbody>
            {
                empresas.length == 0 ?
                <tr><td className="vazio">Nenhuma empresa encontrada.</td></tr>
                :
                empresas.map((e, index)=>(
                    <tr key={index}
                    className={empresaAtiva == e.nome_fantasia ? 'ativo' : ''}
                    title="Clique para filtrar as vagas por esta empresa"
                    onClick={()=>empresaClick(e.nome_fantasia)}>
                        <td>
                            <strong>{e.nome_fantasia}</strong>
                            <span>{e.razao_social}</span>
                        </td>
                        <td className="tipo">
                            <Styl.Tipo internacional={(e.tipo == "Internacional").toString()}>{e.tipo}</Styl.Tipo>
                        </td>
                    </tr>
                ))
            }
            </tbody>
        </Styl.Tabela>
    </Styl.Container>
  );
}
export default EmpresaTabela;
