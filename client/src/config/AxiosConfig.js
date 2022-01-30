import axios from "axios";

const instance = axios.create({
  baseURL: `http://localhost:8095/api/v1`,
  headers: {
    "Content-Type": "application/json",
  },
});

instance.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    return Promise.reject(error);
  }
);

const api = {
  get(url, params) {
    return instance.get(url, { ...params }).then((response) => {
      return response.data;
    });
  },

  post(url, data) {
    return instance.post(url, data).then((response) => {
      return response.data;
    });
  },

  put(url, data) {
    return instance.put(url, data).then((response) => {
      return response.data;
    });
  },

  delete(url, data) {
    return instance.delete(url, data).then((response) => {
      return response.data;
    });
  },
};

export default api;
