let axiosError = function (error, failFn) {

    if ($.isFunction(failFn)) failFn();

    if (error.response) {
        if (error.response.status == 405) {
            alert(`[${error.response.status}]올바른 요청이 아닙니다.`);
        } else if (error.response.status == 900) {
            alert(`[${error.response.status}]${error.response.message}`);
            window.location.href = '/logout';
        }
    } else if (error.request) console.log(error.request);
    else console.log('Error', error.message);
}

let axiosDone = function (res, doneFn) {
    if ($.isFunction(doneFn)) doneFn(res);
}

let axiosResponse = function (axiosRequest, doneFn, failFn) {
    axiosRequest.then((res) => {
        axiosDone(res, doneFn)
    })
        .catch(error => {
            axiosError(error, failFn)
        });
}

let axiosGet = function (url, params, doneFn, failFn) {

    axiosResponse(
        axios.get(url, {
            params: params
        })
        , doneFn
        , failFn
    );
}

let axiosPost = function (url, jsonData, doneFn, failFn) {
    axiosResponse(
        axios.post(url, JSON.stringify(jsonData), {headers: {'Content-Type': 'application/json'}})
        , doneFn
        , failFn
    );
}

let axiosPut = function (url, jsonData, doneFn, failFn) {
    axiosResponse(
        axios.put(url, JSON.stringify(jsonData), {headers: {'Content-Type': 'application/json'}})
        , doneFn
        , failFn
    );
}

let axiosDelete = function (url, params, doneFn, failFn) {
    axiosResponse(
        axios.delete(url, {
            params: params
        })
        , doneFn
        , failFn
    );
}