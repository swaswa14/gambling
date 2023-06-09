import axios, { AxiosResponse } from 'axios';
import config from "tailwindcss/defaultConfig";

export interface IAxiosPostPut {
    url: string;
    data?: any;
}

export interface IAxiosPostPut {
    url: string;
    data?: any;
}

export interface IAxiosGet {
    url: string;
}

export interface IAxiosDelete {
    url: string;
}

const defaultHeaders = {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
};




export async function axiosPost<T>(options: IAxiosPostPut): Promise<AxiosResponse<T>> {
    try {
        const response: AxiosResponse<T> = await axios.post<T>(options.url, options.data, {
            headers: { ...defaultHeaders, "Access-Control-Allow-Methods": "POST" }
        });
        return response;
    } catch (error) {
        throw error;
    }
}

export async function axiosPut<T>(options: IAxiosPostPut): Promise<AxiosResponse<T>> {
    try {
        const response: AxiosResponse<T> = await axios.put<T>(options.url, options.data, {
            headers: { ...defaultHeaders, "Access-Control-Allow-Methods": "PUT" }
        });
        return response;
    } catch (error) {
        throw error;
    }
}

export async function axiosDelete<T>(options: IAxiosDelete): Promise<AxiosResponse<T>> {
    try {
        const response: AxiosResponse<T> = await axios.delete<T>(options.url, {
            headers: { ...defaultHeaders, "Access-Control-Allow-Methods": "DELETE" }
        });
        return response;
    } catch (error) {
        throw error;
    }
}

export async function axiosGet<T>(options: IAxiosGet): Promise<AxiosResponse<T>> {
    try {
        const response: AxiosResponse<T> = await axios.get<T>(options.url, {
            headers: { ...defaultHeaders, "Access-Control-Allow-Methods": "GET" },
        });
        return response;
    } catch (error) {
        throw error;
    }
}

