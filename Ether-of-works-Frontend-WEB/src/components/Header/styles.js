import styled from 'styled-components'

export const Container = styled.div`
    font-family: 'Inter', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    width: 100%;
    max-width: 760px;
    box-sizing: border-box;
    min-height: 58px;
    margin: 28px auto 0 auto;
    padding: 8px 24px 8px 10px;
    background-color: #FFC700;
    /*cabeçalho em formato de pílula*/
    border-radius: 32px;
    border: 2px solid #0A0E6B;
    /*sombra "dura" deslocada, sem desfoque*/
    box-shadow: 5px 5px 0 #0A0E6B;
    /*componentes em forma de caixa*/
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 8px 16px;
`
export const Esq = styled.div`
    display: flex;
    align-items: center;
    gap: 12px;

    h1{
        margin: 0;
        font-size: 1.2rem;
        font-weight: 900;
        letter-spacing: -0.02em;
        color: #1B2BD9;
        line-height: 1.1;
    }
    small{
        font-size: 0.7rem;
        font-weight: 700;
        text-transform: uppercase;
        letter-spacing: 0.08em;
        color: #0A0E6B;
        opacity: 0.7;
    }
`

export const Logo = styled.div`
    width: 42px;
    height: 42px;
    border-radius: 50%;
    background-color: #FFF;
    border: 2px solid #0A0E6B;
    display: flex;
    align-items: center;
    justify-content: center;
    img{
        width: 28px;
        height: 20px;
    }
`

export const Dir = styled.div`
    flex: 1;
    /*começar do final da caixa - canto direito*/
    display: flex;
    justify-content: flex-end;
    span{
        color: #1B2BD9;
        font-weight: 700;
        font-style: italic;
        font-size: 0.82rem;
        text-align: right;
    }

    @media (max-width: 560px){
        flex-basis: 100%;
        justify-content: flex-start;
        padding: 0 0 4px 6px;
        span{
            text-align: left;
        }
    }
`
