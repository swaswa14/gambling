import {useMutation, useQuery} from "@tanstack/react-query";
import {authenticate} from "../api/api-client";
import {QueryClient} from "@tanstack/query-core";




const { data  ,isLoading, isError} = useQuery({
    queryKey: ['login'],
    queryFn : ()=> authenticate
})