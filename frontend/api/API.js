import axios from 'axios';

const API = axios.create({
  baseURL: 'http://j6c208.p.ssafy.io/api',
  headers: {
    "Content-type": "application/json",
    "Access-Control-Allow-Origin": "*",
  },
});
  
export default API;