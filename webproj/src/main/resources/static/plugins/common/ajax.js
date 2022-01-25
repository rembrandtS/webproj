let axiosError = function (error, failFn) {
  if (error.response.status === 900) {
    alert(error.response.message);
    window.location.href = "/logout";
  }

  if ($.isFunction(failFn)) failFn();
}

let axiosDone = function (res, doneFn) {
  if ($.isFunction(doneFn)) doneFn(res);
}

let axiosResponse = function (axiosRequest, doneFn, failFn) {
  axiosRequest.then((res) => {
    axiosDone(res, doneFn)
  })
  .catch((error) => {
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
    axios.post(url, JSON.stringify(jsonData),{ headers: { 'Content-Type': 'application/json' } })
    , doneFn
    , failFn
  );
}

let axiosPut = function (url, params, doneFn, failFn) {
  axiosResponse(
    axios.put(url, {
      params: params
    })
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