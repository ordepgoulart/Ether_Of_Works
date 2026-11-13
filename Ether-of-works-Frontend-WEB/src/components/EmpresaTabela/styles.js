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
    /*a tabela rola quando houver muitas empresas*/
    display: block;
    overflow-y: auto;
    flex: 1;

    tbody{
        display: table;
        width: 100%;
    }

    td{
        padding: 6px 10px;
        font-size: 0.88rem;
        color: #000;
        background-color: rgba(255,255,255,0.22);
        transition: background-color 0.15s;
        strong{
            display: block;
            font-weight: 700;
        }
        span{
            font-size: 0.72rem;
            opacity: 0.75;
        }
    }

    /*cantos arredondados da linha*/
    td:first-child{
        border-radius: 8px 0 0 8px;
    }
    td:last-child{
        border-radius: 0 8px 8px 0;
    }

    .tipo{
        text-align: right;
        white-space: nowrap;
    }

    tr{
        cursor: pointer;
        &:hover td{
            background-color: rgba(255,255,255,0.5);
        }
    }

    /*empresa selecionada no filtro*/
    .ativo td{
        background-color: #FFFF00;
    }
    .ativo td:first-child{
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

//etiqueta do tipo da empresa
export const Tipo = styled.span`
    && {
        font-size: 0.68rem;
        font-weight: 800;
        opacity: 1;
        padding: 2px 8px;
        border-radius: 8px;
        color: ${prop=> prop.internacional == "true" ? '#FFF' : '#0A0E6B'};
        background-color: ${prop=> prop.internacional == "true" ? '#1B2BD9' : '#FFF'};
    }
`
