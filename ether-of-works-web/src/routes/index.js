import React from "react";
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import Home from "../views/VagasHome";

function Rotas(){
    return(
        <Router>
            <Routes>
                <Route path="/" element={<Home/>}></Route>
            </Routes>
        </Router>
    )
}

export default Rotas
