import {axiosDelete, axiosGet, axiosPost, axiosPut, IAxiosDelete, IAxiosGet, IAxiosPostPut} from "./api-interface";
import {Client} from "../entity/interface";
import {AuthenticationForm, ClientRegistrationForm} from "../entity/forms";



export async function getAllClients(){
    const link = String(process.env.getAllUsers);
    const formattedLink = link.replace("{user}", "client").replace("{users}", "clients");
    const options : IAxiosGet = {
        url: formattedLink
    };
    const response = await axiosGet<Client[]>(options);
    console.log(response.data)
    return response.data;
}

export async function getAllUnlocked(){
    const link = String(process.env.getAllUnlockedUsers);
    const formattedLink = link.replace("{user}", "client").replace("{users}", "clients");
    const options : IAxiosGet = {
        url: formattedLink
    };
    const response = await axiosGet<Client[]>(options);
    console.log(response.data)
    return response.data;
}

export async function getAllLocked(){
    const link = String(process.env.getAllLockedUsers);
    const formattedLink = link.replace("{user}", "client").replace("{users}", "clients");
    const options : IAxiosGet = {
        url: formattedLink
    };
    const response = await axiosGet<Client[]>(options);
    console.log(response.data)
    return response.data;
}

export async function getAllEnabled(){
    const link = String(process.env.getAllEnabledUsers);
    const formattedLink = link.replace("{user}", "client").replace("{users}", "clients");
    const options : IAxiosGet = {
        url: formattedLink
    };
    const response = await axiosGet<Client[]>(options);
    console.log(response.data)
    return response.data;
}
export async function getAllDisabled(){
    const link = String(process.env.getAllDisabledUsers);
    const formattedLink = link.replace("{user}", "client").replace("{users}", "clients");
    const options : IAxiosGet = {
        url: formattedLink
    };
    const response = await axiosGet<Client[]>(options);
    console.log(response.data)
    return response.data;
}




export async function updateClient(data: Client){

    const link = String(process.env.updateUser);
    const formattedLink = link.replace("{user}", "client");
    const options : IAxiosPostPut = {
        url: formattedLink,
        data: data
    };
    const response = await axiosPut<Client>(options);
    console.log(response.data)
    return response.data;
}


export async function deleteClient(id: number){

    const link = String(process.env.deleteUser);
    const formattedLink = link.replace("{user}", "client").replace("{id}", String(id));
    const options : IAxiosDelete = {
        url: formattedLink,
    };
    const response = await axiosDelete<Client>(options);
    console.log(response.data)
    return response.data;
}


export async function registerClient(form : ClientRegistrationForm){

    const link = String(process.env.postRegisterUser);
    const formattedLink = link.replace("{user}", "client");
    const options : IAxiosPostPut = {
        url: formattedLink,
        data : form
    };
    const response = await axiosPost<Client>(options);
    console.log(response.data)
    return response.data;
}

export async function authenticate(form : AuthenticationForm){

    const link = String(process.env.NEXT_PUBLIC_postAuthenticateUser);
    console.log("link here " + link)
    const formattedLink = link.replace("{user}", "client");
    const options : IAxiosPostPut = {
        url: formattedLink,
        data : form
    };
    const response = await axiosPost<Client>(options);
    console.log(response.data)
    return response.data;
}


export async function getUserByID(id : number){

    const link = String(process.env.getUserById);
    console.log(link)
    const formattedLink = link.replace("{user}", "client").replace("{id}", String(id));
    const options : IAxiosGet = {
        url: formattedLink,
    };
    const response = await axiosGet<Client>(options);
    console.log(response.data)
    return response.data;
}

export async function getAllUserTransactions(id : number){

    const link = String(process.env.getAllUserTransactions);
    const formattedLink = link.replace("{user}", "client").replace("{id}", String(id));
    const options : IAxiosGet = {
        url: formattedLink,
    };
    const response = await axiosGet<any>(options);
    console.log(response.data)
    return response.data;
}








