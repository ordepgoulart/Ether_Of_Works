import styled, {keyframes} from 'styled-components'

//animações de abertura do modal
const aparecer = keyframes`
    from{ opacity: 0; }
    to{ opacity: 1; }
`
const subir = keyframes`
    from{ opacity: 0; transform: translateY(16px) scale(0.98); }
    to{ opacity: 1; transform: translateY(0) scale(1); }
`

//fundo escuro que cobre a página atrás do modal
export const Fundo = styled.div`
    position: fixed;
    inset: 0;
    background-color: rgba(5, 8, 60, 0.7);
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 16px;
    z-index: 10;
    animation: ${aparecer} 0.15s ease-out;
`

export const Container = styled.div`
    font-family: 'Inter', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    width: 100%;
    max-width: 430px;
    box-sizing: border-box;
    background-color: #FFFF00;
    border-radius: 20px;
    border: 2px solid #0A0E6B;
    box-shadow: 8px 8px 0 #FF2A2A;
    padding: 22px 26px;
    animation: ${subir} 0.2s ease-out;

    h2{
        margin: 0 0 10px 0;
        font-size: 1.2rem;
        font-weight: 900;
        color: #000;
    }
    p{
        margin: 0;
        color: #000;
        line-height: 1.5;
    }
    small{
        display: block;
        margin-top: 8px;
        font-size: 0.78rem;
        font-weight: 600;
        color: #B00000;
    }
`

//ícone de alerta no topo do modal
export const Icone = styled.div`
    width: 44px;
    height: 44px;
    border-radius: 50%;
    background-color: #FF2A2A;
    border: 2px solid #0A0E6B;
    color: #FFF;
    font-size: 1.4rem;
    font-weight: 900;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 12px;
`

export const Botoes = styled.div`
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 22px;
    button{
        font-family: inherit;
        font-weight: 800;
        font-size: 15px;
        padding: 8px 24px;
        border: 2px solid #0A0E6B;
        border-radius: 18px;
        cursor: pointer;
        box-shadow: 3px 3px 0 #0A0E6B;
        transition: transform 0.1s, box-shadow 0.1s;
        &:hover{
            transform: translate(-1px, -1px);
            box-shadow: 4px 4px 0 #0A0E6B;
        }
        &:active{
            transform: translate(3px, 3px);
            box-shadow: 0 0 0 #0A0E6B;
        }
    }
    .cancelar{
        background-color: #FFC000;
        color: #000;
    }
    .apagar{
        background-color: #FF2A2A;
        color: #FFF;
    }
`
