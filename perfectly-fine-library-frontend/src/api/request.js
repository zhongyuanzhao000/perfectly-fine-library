import axios from 'axios';

const request = (method , url, data = {}) => {
    let result;
    switch (method.toUpperCase()) {
        case 'GET':
            result = axios.get(url, data);
            /*
            result=axios.request({
                url:url,
                method:"get",
                data:data
            })*/
            break;
        case 'POST':
            result = axios.post(url, data);
            break;
        case 'PUT':
            result = axios.put(url, data);
            break;
    }

    return result;
};



export default request;