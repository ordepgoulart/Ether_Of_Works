import styled from 'styled-components'

export const Container = styled.div`
    font-family: 'Inter', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    width: 100%;
    box-sizing: border-box;
    padding: 12px 14px 12px 18px;
    margin-bottom: 12px;
    background-color: #FFFF00;
    border: 2px solid #0A0E6B;
    border-radius: 12px;
    /*faixa lateral e sombra "dura" deslocada*/
    box-shadow: inset 6px 0 0 #FFC700, 3px 3px 0 #0A0E6B;
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 12px;
    transition: transform 0.15s, box-shadow 0.15s;

    /*o cartão "sobe" um pouco ao passar o mouse*/
    &:hover{
        transform: translate(-2px, -2px);
        box-shadow: inset 6px 0 0 #FFC700, 5px 5px 0 #0A0E6B;
    }

    /*em telas pequenas os botões descem para baixo das informações*/
    @media (max-width: 560px){
        flex-wrap: wrap;
    }
`

export const Info = styled.div`
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
    gap: 3px;

    .linha{
        font-size: 0.82rem;
        font-weight: 600;
        color: #000;
        strong{
            font-weight: 800;
            color: #0A0E6B;
        }
    }

    .divisor::after{
        content: "|";
        margin: 0 8px;
        font-weight: 400;
        opacity: 0.5;
    }

    @media (max-width: 560px){
        flex-basis: 100%;
    }
`

export const Topo = styled.div`
    display: flex;
    align-items: baseline;
    gap: 8px;
    flex-wrap: wrap;

    h3{
        margin: 0;
        font-size: 1.05rem;
        font-weight: 800;
        letter-spacing: -0.01em;
        color: #000;
    }
    small{
        font-size: 0.7rem;
        font-weight: 700;
        color: #0A0E6B;
        opacity: 0.55;
    }
`

export const Etiquetas = styled.div`
    display: flex;
    flex-wrap: wrap;
    gap: 6px;
    margin-top: 6px;

    /*pequenas etiquetas cinzas do protótipo*/
    span{
        background-color: #E4E4E4;
        color: #222;
        font-size: 0.7rem;
        font-weight: 700;
        padding: 3px 10px;
        border-radius: 10px;
    }

    /*etiqueta do tipo da empresa*/
    .destaque{
        background-color: #1B2BD9;
        color: #FFF;
    }
`

export const Botoes = styled.div`
    display: flex;
    gap: 8px;

    button{
        font-family: inherit;
        font-weight: 800;
        font-size: 0.8rem;
        padding: 7px 20px;
        border: 2px solid #0A0E6B;
        border-radius: 16px;
        color: #000;
        cursor: pointer;
        box-shadow: 2px 2px 0 #0A0E6B;
        transition: transform 0.1s, box-shadow 0.1s;
        &:hover{
            transform: translate(-1px, -1px);
            box-shadow: 3px 3px 0 #0A0E6B;
        }
        /*efeito de "apertar" o botão*/
        &:active{
            transform: translate(2px, 2px);
            box-shadow: 0 0 0 #0A0E6B;
        }
    }

    .editar{
        background-color: #FFC000;
    }

    .apagar{
        background-color: #FF2A2A;
        color: #FFF;
    }
`
