import styled, {keyframes} from 'styled-components'

const aparecer = keyframes`
    from{ opacity: 0; }
    to{ opacity: 1; }
`
const subir = keyframes`
    from{ opacity: 0; transform: translateY(16px) scale(0.98); }
    to{ opacity: 1; transform: translateY(0) scale(1); }
`

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
    max-width: 660px;
    max-height: 92vh;
    box-sizing: border-box;
    overflow-y: auto;
    background-color: #00A2FF;
    border-radius: 20px;
    border: 2px solid #0A0E6B;
    box-shadow: 8px 8px 0 #FFC700;
    padding: 22px 26px;
    animation: ${subir} 0.2s ease-out;
`

export const Topo = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 6px;

    h2{
        margin: 0;
        font-size: 1.15rem;
        font-weight: 900;
        background-color: #FFC700;
        padding: 7px 18px;
        border-radius: 12px;
        border: 2px solid #0A0E6B;
        color: #000;
    }

    button{
        width: 34px;
        height: 34px;
        border-radius: 50%;
        border: 2px solid #0A0E6B;
        background: #FFF;
        font-size: 0.9rem;
        font-weight: 800;
        cursor: pointer;
        &:hover{
            background: #FFC700;
        }
    }
`

export const Formulario = styled.div`
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 12px 18px;

    @media (max-width: 560px){
        grid-template-columns: 1fr;
    }
`

export const Secao = styled.h4`
    grid-column: 1 / -1;
    margin: 12px 0 0 0;
    font-size: 0.72rem;
    font-weight: 800;
    text-transform: uppercase;
    letter-spacing: 0.1em;
    color: #0A0E6B;
    display: flex;
    align-items: center;
    gap: 10px;
    /*linha que completa o título*/
    &::after{
        content: "";
        flex: 1;
        height: 2px;
        background-color: rgba(10, 14, 107, 0.25);
    }
`

export const Input = styled.div`
    display: flex;
    flex-direction: column;
    span{
        color: #000;
        margin-bottom: 4px;
        font-weight: 700;
        font-size: 0.82rem;
    }
    input, select{
        font-family: inherit;
        font-size: 15px;
        padding: 10px 12px;
        border: 2px solid #0A0E6B;
        border-radius: 10px;
        background-color: #FFF;
        outline: none;
        transition: box-shadow 0.15s;
        &:focus{
            box-shadow: 3px 3px 0 #FFC700;
        }
    }
    input:disabled{
        background-color: #D6E6F5;
        color: #555;
    }
`

export const TextArea = styled.div`
    grid-column: 1 / -1;
    display: flex;
    flex-direction: column;
    span{
        color: #000;
        margin-bottom: 4px;
        font-weight: 700;
        font-size: 0.82rem;
    }
    textarea{
        font-family: inherit;
        font-size: 15px;
        padding: 10px 12px;
        border: 2px solid #0A0E6B;
        border-radius: 10px;
        resize: vertical;
        outline: none;
        transition: box-shadow 0.15s;
        &:focus{
            box-shadow: 3px 3px 0 #FFC700;
        }
    }
`

export const Salvar = styled.div`
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 22px;
    button{
        font-family: inherit;
        background: #FFC700;
        font-weight: 800;
        font-size: 15px;
        color: #000;
        padding: 10px 30px;
        border: 2px solid #0A0E6B;
        border-radius: 20px;
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
        background: #FFF;
    }
`
