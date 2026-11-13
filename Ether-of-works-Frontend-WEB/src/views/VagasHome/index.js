//importar o react que traz todos os recursos dele
import React, {useState, useEffect} from 'react';
import Header from '../../components/Header';
import * as Styl from './styles'
import CargoTabela from '../../components/CargoTabela';
import EmpresaTabela from '../../components/EmpresaTabela';
import VagaCartao from '../../components/VagaCartao';
import ModalVaga from '../../components/ModalVaga';
import ModalExcluir from '../../components/ModalExcluir';
import api from '../../services/api'
import paraLista from '../../utils/paraLista';
import tiposEmpresa from '../../utils/tiposEmpresa';

function Home() {

const [cargos, atualizaCargos] = useState([])
const [empresas, atualizaEmpresas] = useState([])
const [todasEmpresas, atualizaTodasEmpresas] = useState([])
const [vagas, atualizaVagas] = useState([])

const [pesquisaCargo, setPesquisaCargo] = useState('')
const [pesquisaEmpresa, setPesquisaEmpresa] = useState('')

const [tipoEmpresa, atualizarTipoEmpresa] = useState(0)

const [filtroCargo, atualizarFiltroCargo] = useState('')
const [filtroEmpresa, atualizarFiltroEmpresa] = useState('')

const [modalVaga, atualizarModalVaga] = useState('fechado')
const [modalExcluir, atualizarModalExcluir] = useState(false)
const [vagaSelecionada, atualizarVagaSelecionada] = useState()

async function carregarCargos() {
  await api.get('/get-all-cargos')
  .then(resp=>{
    atualizaCargos(paraLista(resp.data))
  })
  .catch(()=>{
    alert("Erro ao carregar os cargos!")
  })
}

async function carregarTodasEmpresas() {
  await api.get('/get-all-empresas')
  .then(resp=>{
    atualizaTodasEmpresas(paraLista(resp.data))
  })
  .catch(()=>{
    alert("Erro ao carregar as empresas!")
  })
}

async function carregarEmpresas() {
  if(tipoEmpresa == 0){
    await api.get('/get-all-empresas')
    .then(resp=>{
      atualizaEmpresas(paraLista(resp.data))
    })
  }
  else{
    await api.get(`/get-empresas-by-tipo/${tiposEmpresa[tipoEmpresa]}`)
    .then(resp=>{
      atualizaEmpresas(paraLista(resp.data))
    })
  }
}

async function carregarVagas() {
  await api.get('/get-all-vagas')
  .then(resp=>{
    atualizaVagas(paraLista(resp.data))
  })
  .catch(()=>{
    alert("Erro ao carregar as vagas!")
  })
}

function trocarTipo() {
  atualizarTipoEmpresa((tipoEmpresa + 1) % tiposEmpresa.length)
}

function cargoClick(nome) {
  atualizarFiltroCargo(filtroCargo == nome ? '' : nome)
}

function empresaClick(nome) {
  atualizarFiltroEmpresa(filtroEmpresa == nome ? '' : nome)
}

function abrirCadastro() {
  atualizarVagaSelecionada(undefined)
  atualizarModalVaga('cadastro')
}

function abrirEdicao(vaga) {
  atualizarVagaSelecionada(vaga)
  atualizarModalVaga('edicao')
}

function abrirExclusao(vaga) {
  atualizarVagaSelecionada(vaga)
  atualizarModalExcluir(true)
}

function fecharModais() {
  atualizarModalVaga('fechado')
  atualizarModalExcluir(false)
  atualizarVagaSelecionada(undefined)
}

//depois de salvar ou excluir, fecha o modal e recarrega as vagas
function operacaoConcluida() {
  fecharModais()
  carregarVagas()
}

//listas filtradas que serão exibidas na tela
const cargosFiltrados = cargos.filter(c=>
  c.nome.toLowerCase().includes(pesquisaCargo.toLowerCase()))

const empresasFiltradas = empresas.filter(e=>
  e.nome_fantasia.toLowerCase().includes(pesquisaEmpresa.toLowerCase()))

const vagasFiltradas = vagas.filter(v=>
  (filtroCargo == '' || v.cargo == filtroCargo) &&
  (filtroEmpresa == '' || (v.empresa != undefined && v.empresa.nome_fantasia == filtroEmpresa)))

useEffect(()=>{
  carregarCargos()
  carregarTodasEmpresas()
  carregarVagas()
}, [])

useEffect(()=>{
  carregarEmpresas()
}, [tipoEmpresa])


return  (
    <Styl.Container>
      <Header/>

      <Styl.Tabelas>
        <Styl.Painel>
          <Styl.Abas>
            <input type="text" className="aba" placeholder="Pesquisa por nome"
            value={pesquisaCargo} onChange={e=>setPesquisaCargo(e.target.value)}/>
          </Styl.Abas>
          <CargoTabela
          cargos={cargosFiltrados}
          cargoAtivo={filtroCargo}
          cargoClick={cargoClick}
          />
        </Styl.Painel>

        <Styl.Painel>
          <Styl.Abas>
            <input type="text" className="aba" placeholder="Pesquisa por nome"
            value={pesquisaEmpresa} onChange={e=>setPesquisaEmpresa(e.target.value)}/>
            <button type="button" className={tipoEmpresa != 0 ? "aba tipo filtrando" : "aba tipo"}
            title="Clique para alternar o tipo de empresa" onClick={()=>trocarTipo()}>
              {tipoEmpresa == 0 ? "Tipo" : tiposEmpresa[tipoEmpresa]}
            </button>
          </Styl.Abas>
          <EmpresaTabela
          empresas={empresasFiltradas}
          empresaAtiva={filtroEmpresa}
          empresaClick={empresaClick}
          />
        </Styl.Painel>
      </Styl.Tabelas>

      <Styl.AreaVagas>
        <Styl.Abas>
          <select className={filtroCargo != '' ? "aba filtrando" : "aba"} value={filtroCargo} onChange={e=>atualizarFiltroCargo(e.target.value)}>
            <option value="">Filtrar por cargo</option>
            {
              cargos.map((c, index)=>(
                <option key={index} value={c.nome}>{c.nome}</option>
              ))
            }
          </select>
          <select className={filtroEmpresa != '' ? "aba filtrando" : "aba"} value={filtroEmpresa} onChange={e=>atualizarFiltroEmpresa(e.target.value)}>
            <option value="">Filtrar por empresa</option>
            {
              todasEmpresas.map((e, index)=>(
                <option key={index} value={e.nome_fantasia}>{e.nome_fantasia}</option>
              ))
            }
          </select>
          <button type="button" className="novo" title="Cadastrar vaga" onClick={()=>abrirCadastro()}>+</button>
        </Styl.Abas>

        <Styl.ListaVagas>
          <Styl.CabecalhoVagas>
            <h3>Vagas <span>{vagasFiltradas.length}</span></h3>
            {
              filtroCargo != '' || filtroEmpresa != '' ?
              <button type="button" onClick={()=>{atualizarFiltroCargo(''); atualizarFiltroEmpresa('');}}>
                Limpar filtros ✕
              </button>
              : null
            }
          </Styl.CabecalhoVagas>
          {
            vagasFiltradas.length == 0 ?
            <Styl.Vazio>
              <strong>Nenhuma vaga por aqui.</strong>
              <span>Mude os filtros ou clique em + para cadastrar uma nova vaga.</span>
            </Styl.Vazio>
            :
            vagasFiltradas.map((v, index)=>(
              <VagaCartao key={index}
              registro={v.registro}
              cargo={v.cargo}
              empresa={v.empresa}
              remuneracao={v.remuneracao}
              jornada={v.jornada_trabalho}
              cidade={v.cidade}
              estado={v.estado}
              regime={v.regime}
              editarClick={()=>abrirEdicao(v)}
              apagarClick={()=>abrirExclusao(v)}
              />
            ))
          }
        </Styl.ListaVagas>
      </Styl.AreaVagas>

      {
        modalVaga != 'fechado' ?
        <ModalVaga
        vaga={vagaSelecionada}
        cargos={cargos}
        empresas={todasEmpresas}
        fechar={fecharModais}
        salvo={operacaoConcluida}
        />
        : null
      }

      {
        modalExcluir ?
        <ModalExcluir
        vaga={vagaSelecionada}
        fechar={fecharModais}
        excluido={operacaoConcluida}
        />
        : null
      }
    </Styl.Container>
)
}

export default Home;
