import React from 'react';
import * as Styl from './styles'
import logo from '../../assets/logo.svg'

function Header() {
  return (
    <Styl.Container>
        <Styl.Esq>
            <Styl.Logo>
                <img src={logo} alt="Logo"/>
            </Styl.Logo>
            <div>
                <h1>Ether of Works</h1>
                <small>Painel de vagas</small>
            </div>
        </Styl.Esq>
        <Styl.Dir>
            <span>Tome sua Ambrosia, e venha explorar as vagas do panteão</span>
        </Styl.Dir>
    </Styl.Container>
  );
}

export default Header;
