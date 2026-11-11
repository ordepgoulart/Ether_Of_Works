import styled from 'styled-components'

const lupa = `url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%230A0E6B' stroke-width='3' stroke-linecap='round'%3E%3Ccircle cx='10.5' cy='10.5' r='6.5'/%3E%3Cpath d='m20 20-4.5-4.5'/%3E%3C/svg%3E")`

export const Container = styled.div `
    font-family: 'Inter', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    width: 100%;
    min-height: 100vh;
    box-sizing: border-box;
    padding: 0 16px 70px 16px;
    /*fundo azul do protótipo com um brilho suave e uma trama de pontos ("éter")*/
    background-color: #0B17F0;
    background-image:
        radial-gradient(ellipse 60% 40% at 50% 0%, rgba(255, 199, 0, 0.18), transparent 70%),
        radial-gradient(circle at 1px 1px, rgba(255, 255, 255, 0.10) 1px, transparent 0);
    background-size: 100% 100%, 22px 22px;
`

export const Tabelas = styled.div `
    width: 100%;
    max-width: 720px;
    margin: 72px auto 0 auto;
    display: grid;
    grid-template-columns: minmax(0, 1fr) minmax(0, 1fr);
    gap: 64px;

    @media (max-width: 680px){
        grid-template-columns: 1fr;
        gap: 48px;
    }
`

export const Painel = styled.div `
    width: 100%;
    position: relative;
`

export const Abas = styled.div `
    display: flex;
    align-items: flex-end;
    gap: 8px;
    /*as abas sobem por cima da borda do painel*/
    margin-bottom: -20px;
    margin-left: -6px;
    position: relative;
    z-index: 1;

    .aba{
        font-family: inherit;
        font-size: 0.85rem;
        font-weight: 700;
        color: #000;
        background-color: #FFC700;
        border: 2px solid #0A0E6B;
        border-radius: 12px;
        box-shadow: 3px 3px 0 #0A0E6B;
        height: 38px;
        padding: 0 12px;
        outline: none;
        box-sizing: border-box;
        min-width: 0;
        transition: background-color 0.15s, transform 0.1s, box-shadow 0.1s;
    }

    input.aba{
        flex: 1;
        max-width: 240px;
        padding-left: 34px;
        background-image: ${lupa};
        background-repeat: no-repeat;
        background-position: 11px center;
        background-size: 16px 16px;
        &::placeholder{
            color: #000;
            opacity: 0.75;
        }
        &:focus{
            background-color: #FFE066;
        }
    }

    select.aba{
        width: 200px;
        cursor: pointer;
        &:hover{
            background-color: #FFD633;
        }
    }

    /*aba com filtro aplicado*/
    .filtrando{
        background-color: #FFFF00;
    }

    .tipo{
        flex-shrink: 0;
        cursor: pointer;
        margin-bottom: 4px;
        padding: 0 16px;
        &:hover{
            transform: translate(-1px, -1px);
            box-shadow: 4px 4px 0 #0A0E6B;
        }
        &:active{
            transform: translate(3px, 3px);
            box-shadow: 0 0 0 #0A0E6B;
        }
    }

    .novo{
        margin-left: auto;
        flex-shrink: 0;
        width: 46px;
        height: 46px;
        border-radius: 50%;
        border: 2px solid #0A0E6B;
        background-color: #FFC700;
        box-shadow: 3px 3px 0 #0A0E6B;
        color: #0A0E6B;
        font-size: 1.9rem;
        font-weight: 900;
        line-height: 1;
        cursor: pointer;
        margin-bottom: 4px;
        transition: transform 0.15s, box-shadow 0.1s;
        &:hover{
            transform: rotate(90deg);
            background-color: #FFFF00;
        }
        &:active{
            box-shadow: 0 0 0 #0A0E6B;
        }
    }

    @media (max-width: 560px){
        select.aba{
            width: auto;
            flex: 1;
        }
    }
`

export const AreaVagas = styled.div `
    width: 100%;
    max-width: 720px;
    margin: 76px auto 0 auto;
`

export const ListaVagas = styled.div `
    width: 100%;
    min-height: 220px;
    box-sizing: border-box;
    padding: 34px 18px 8px 18px;
    background-color: #00A2FF;
    border-radius: 18px;
    border: 2px solid #0A0E6B;
    box-shadow: 5px 5px 0 #0A0E6B;
`

export const CabecalhoVagas = styled.div `
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 0 4px 12px 4px;

    h3{
        margin: 0;
        font-size: 0.95rem;
        font-weight: 800;
        color: #000;
        display: flex;
        align-items: center;
        gap: 8px;
        /*contador de vagas*/
        span{
            font-size: 0.72rem;
            font-weight: 800;
            color: #FFF;
            background-color: #0A0E6B;
            border-radius: 10px;
            padding: 2px 8px;
        }
    }

    button{
        font-family: inherit;
        font-size: 0.75rem;
        font-weight: 800;
        color: #0A0E6B;
        background: #FFF;
        border: 2px solid #0A0E6B;
        border-radius: 12px;
        padding: 4px 12px;
        cursor: pointer;
        &:hover{
            background: #FFFF00;
        }
    }
`

export const Vazio = styled.div `
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 4px;
    text-align: center;
    padding: 30px 16px 38px 16px;
    margin-bottom: 10px;
    border: 2px dashed rgba(10, 14, 107, 0.4);
    border-radius: 12px;
    color: #0A0E6B;

    strong{
        font-size: 1rem;
        font-weight: 800;
    }
    span{
        font-size: 0.85rem;
        font-weight: 500;
    }
`
