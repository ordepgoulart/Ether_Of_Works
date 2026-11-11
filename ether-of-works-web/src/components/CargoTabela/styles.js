import styled from 'styled-components'

export const Container = styled.div`
    font-family: 'Inter', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    width: 100%;
    height: 240px;
    box-sizing: border-box;
    padding: 36px 16px 14px 16px;
    background-color: #00A2FF;
    border-radius: 18px;
    border: 2px solid #0A0E6B;
    /*sombra "dura" deslocada, sem desfoque*/
    box-shadow: 5px 5px 0 #0A0E6B;
    display: flex;
    flex-direction: column;
`

export const Titulo = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 0 6px 8px 6px;

    h2{
        margin: 0;
        font-size: 0.95rem;
        font-weight: 800;
        color: #000;
    }

    /*contador de registros*/
    span{
        min-width: 22px;
        text-align: center;
        font-size: 0.72rem;
        font-weight: 800;
        color: #FFF;
        background-color: #0A0E6B;
        border-radius: 10px;
        padding: 2px 7px;
    }
`

export const Tabela = styled.table`
    width: 100%;
    border-collapse: separate;
    border-spacing: 0 4px;
    /*a tabela rola quando houver muitos cargos*/
    display: block;
    overflow-y: auto;
    flex: 1;

    tbody{
        display: table;
        width: 100%;
    }

    td{
        padding: 7px 10px;
        font-size: 0.88rem;
        font-weight: 500;
        color: #000;
        background-color: rgba(255,255,255,0.22);
        border-radius: 8px;
        transition: background-color 0.15s;
    }

    tr{
        cursor: pointer;
        &:hover td{
            background-color: rgba(255,255,255,0.5);
        }
    }

    /*cargo selecionado no filtro*/
    .ativo td{
        background-color: #FFFF00;
        font-weight: 800;
        box-shadow: inset 4px 0 0 #0A0E6B;
    }
    .ativo:hover td{
        background-color: #FFFF00;
    }

    .vazio{
        background: none;
        cursor: default;
        opacity: 0.7;
        font-style: italic;
    }
`
