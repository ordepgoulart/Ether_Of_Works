//importar o react que traz todos os recursos dele
import React, {useState} from 'react';
import * as Styl from './styles'
import api from '../../services/api'
import regimes from '../../utils/regimes'

function ModalVaga({vaga, cargos, empresas, fechar, salvo}) {


    const [registro, setRegistro] = useState(vaga != undefined ? vaga.registro : '')
    const [empresa, setEmpresa] = useState(vaga != undefined && vaga.empresa != undefined ? vaga.empresa.nome_fantasia : '')
    const [cargo, setCargo] = useState(vaga != undefined ? vaga.cargo : '')
    const [cidade, setCidade] = useState(vaga != undefined ? vaga.cidade : '')
    const [estado, setEstado] = useState(vaga != undefined ? vaga.estado : '')
    const [requisitos, setRequisitos] = useState(vaga != undefined ? vaga.pre_requisitos : '')
    const [formacao, setFormacao] = useState(vaga != undefined ? vaga.formacao : '')
    const [conhecimentos, setConhecimentos] = useState(vaga != undefined ? vaga.conhecimentos_requeridos : '')
    const [regime, setRegime] = useState(vaga != undefined ? vaga.regime : '')
    const [jornada, setJornada] = useState(vaga != undefined ? vaga.jornada_trabalho : '')
    const [remuneracao, setRemuneracao] = useState(vaga != undefined ? vaga.remuneracao : '')

    const campoVazio = (valor) => valor == undefined || valor.toString().trim() == ''

    const montarVaga = (empresaEscolhida) => ({
        registro: registro.trim(),
        cargo,
        cidade: cidade.trim(),
        estado: estado.trim().toUpperCase(),
        pre_requisitos: requisitos,
        formacao,
        conhecimentos_requeridos: conhecimentos,
        regime: regime.trim().toUpperCase(),
        jornada_trabalho: jornada,
        remuneracao,
        empresa: {
            nome_fantasia: empresaEscolhida.nome_fantasia,
            razao_social: empresaEscolhida.razao_social,
            tipo: empresaEscolhida.tipo
        }
    })

    async function salvar() {
        const vazio = [registro, empresa, cargo, cidade, estado, requisitos, formacao,
            conhecimentos, regime, jornada, remuneracao].some(campoVazio)

        if(vazio){
            alert("Preencha todos os campos da vaga!")
        }
        else if(!regimes.includes(regime.trim().toUpperCase())){
            alert("O regime deve ser CLT ou PJ!")
        }
        else if(vaga == undefined){
            await api.post('/add-vaga', montarVaga(empresas.find(e=> e.nome_fantasia == empresa)))
            .then(()=>{
                alert("Vaga cadastrada!")
                salvo()
            })
            .catch((e)=>{
                console.log(e.response.data)
                alert(e.response.data)
            })
        }
        else{
            await api.put('/update-vaga', montarVaga(empresas.find(e=> e.nome_fantasia == empresa)))
            .then(()=>{
                alert("Vaga atualizada!")
                salvo()
            })
            .catch((e)=>{
                console.log(e.response.data)
                alert(e.response.data)

            })
        }
    }

    return (
        <Styl.Fundo onClick={fechar}>
            <Styl.Container onClick={e=>e.stopPropagation()}>
                <Styl.Topo>
                    <h2>{vaga == undefined ? "Cadastrar vaga" : "Editar vaga"}</h2>
                    <button type="button" onClick={fechar}>✕</button>
                </Styl.Topo>

                <Styl.Formulario>
                    <Styl.Secao>Identificação</Styl.Secao>

                    <Styl.Input>
                        <span>Registro</span>
                        <input type="text" placeholder="Registro da vaga" disabled={vaga != undefined}
                        onChange={e=>setRegistro(e.target.value)} value={registro}/>
                    </Styl.Input>

                    <Styl.Input>
                        <span>Empresa</span>
                        <select onChange={e=>setEmpresa(e.target.value)} value={empresa}>
                            <option value="">Selecione a empresa</option>
                            {
                                empresas.map((e, index)=>(
                                    <option key={index} value={e.nome_fantasia}>{e.nome_fantasia}</option>
                                ))
                            }
                        </select>
                    </Styl.Input>

                    <Styl.Input>
                        <span>Cargo</span>
                        <select onChange={e=>setCargo(e.target.value)} value={cargo}>
                            <option value="">Selecione o cargo</option>
                            {
                                cargos.map((c, index)=>(
                                    <option key={index} value={c.nome}>{c.nome}</option>
                                ))
                            }
                        </select>
                    </Styl.Input>

                    <Styl.Input>
                        <span>Remuneração</span>
                        <input type="text" placeholder="Ex: R$ 4.500,00"
                        onChange={e=>setRemuneracao(e.target.value)} value={remuneracao}/>
                    </Styl.Input>

                    <Styl.Secao>Local e contrato</Styl.Secao>

                    <Styl.Input>
                        <span>Cidade</span>
                        <input type="text" placeholder="Cidade"
                        onChange={e=>setCidade(e.target.value)} value={cidade}/>
                    </Styl.Input>

                    <Styl.Input>
                        <span>Estado</span>
                        <input type="text" placeholder="UF" maxLength={2}
                        onChange={e=>setEstado(e.target.value)} value={estado}/>
                    </Styl.Input>

                    <Styl.Input>
                        <span>Regime</span>
                        <input type="text" placeholder="CLT ou PJ" list="regimes"
                        onChange={e=>setRegime(e.target.value)} value={regime}/>
                        <datalist id="regimes">
                            {
                                regimes.map((r, index)=>(
                                    <option key={index} value={r}/>
                                ))
                            }
                        </datalist>
                    </Styl.Input>

                    <Styl.Input>
                        <span>Jornada de trabalho</span>
                        <input type="text" placeholder="Ex: 40h semanais"
                        onChange={e=>setJornada(e.target.value)} value={jornada}/>
                    </Styl.Input>

                    <Styl.Secao>Perfil do candidato</Styl.Secao>

                    <Styl.TextArea>
                        <span>Formação</span>
                        <textarea rows={2} placeholder="Formação exigida"
                        onChange={e=>setFormacao(e.target.value)} value={formacao}/>
                    </Styl.TextArea>

                    <Styl.TextArea>
                        <span>Pré-requisitos</span>
                        <textarea rows={2} placeholder="Requisitos da vaga"
                        onChange={e=>setRequisitos(e.target.value)} value={requisitos}/>
                    </Styl.TextArea>

                    <Styl.TextArea>
                        <span>Conhecimentos requeridos</span>
                        <textarea rows={2} placeholder="Conhecimentos necessários"
                        onChange={e=>setConhecimentos(e.target.value)} value={conhecimentos}/>
                    </Styl.TextArea>
                </Styl.Formulario>

                <Styl.Salvar>
                    <button type="button" className="cancelar" onClick={fechar}>Cancelar</button>
                    { vaga == undefined ?
                        <button type="button" onClick={()=>salvar()}>Salvar</button>
                        :
                        <button type="button" onClick={()=>salvar()}>Atualizar</button>
                    }
                </Styl.Salvar>
            </Styl.Container>
        </Styl.Fundo>
    );
}
export default ModalVaga;
