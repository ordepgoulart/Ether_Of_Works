import React from 'react';
import * as Styl from './styles'
import api from '../../services/api'

function ModalExcluir({vaga, fechar, excluido}) {

    async function excluir() {
        await api.delete(`/delete-vaga/${vaga.registro}`)
        .then(resp=>{
            alert(resp.data)
            excluido()
        })
        .catch(()=>{
            alert("Erro ao excluir a vaga!")
        })
    }

    return (
        <Styl.Fundo onClick={fechar}>
            <Styl.Container onClick={e=>e.stopPropagation()}>
                <Styl.Icone>!</Styl.Icone>
                <h2>Apagar vaga</h2>
                <p>
                    Deseja realmente apagar a vaga de <strong>{vaga.cargo}</strong>
                    {vaga.empresa != undefined ? ` na ${vaga.empresa.nome_fantasia}` : ''} (registro {vaga.registro})?
                </p>
                <small>Essa ação não poderá ser desfeita.</small>
                <Styl.Botoes>
                    <button type="button" className="cancelar" onClick={fechar}>Cancelar</button>
                    <button type="button" className="apagar" onClick={()=>excluir()}>Apagar</button>
                </Styl.Botoes>
            </Styl.Container>
        </Styl.Fundo>
    );
}
export default ModalExcluir;
