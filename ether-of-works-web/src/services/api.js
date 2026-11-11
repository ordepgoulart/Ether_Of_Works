//vamos guardar as informações de conexão com o backend
import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080/ether'
})

export default api
