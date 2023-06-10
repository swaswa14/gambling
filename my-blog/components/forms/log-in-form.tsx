import React, { useState } from 'react';

import {authenticate} from "../../utils/api/api-client";
import {AuthenticationForm} from "../../utils/entity/forms";
import {QueryClient} from "@tanstack/query-core";

import {useMutation} from "@tanstack/react-query";

interface LoginFormProps {
    onForgotPassword: () => void;
    onSignUp: () => void;
}



const queryClient = new QueryClient();

const LoginForm: React.FC<LoginFormProps> = ({ onForgotPassword, onSignUp }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [rememberMe, setRememberMe] = useState(false);
    const myForm: AuthenticationForm = {
        username: username,
        password: password,
    };
    const signInMutate = useMutation(authenticate, {
        // Optionally, invalidate related queries after mutation
        onSuccess: () => {
            queryClient.invalidateQueries(['login']);
        },
        onError: (error, variables, context) => {

        }
    });

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        signInMutate.mutate(myForm)
        // Implement your login logic here
    };



    return (
        <form className="space-y-6 w-4/12">
            <div>
                <label className="block text-sm font-medium text-gray-700">
                    Username
                </label>
                <div className="mt-1">
                    <input
                        type="text"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                        className="shadow-sm  focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600   px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                    />
                </div>
            </div>
            <div>
                <label className="block text-sm font-medium text-gray-700">
                    Password
                </label>
                <div className="mt-1">
                    <input
                        type="password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        className="shadow-sm  focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600   px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                    />
                </div>
            </div>
            {signInMutate.isError ? (<div className={"block text-sm font-medium text-red-700"}> <span>  {signInMutate.error?.response.data.errorMessage.replace('{"error"', "").replace("}","") || 'An error occurred'}</span></div>) : null}


                <div className="lg:flex lg:justify-between text-sm sm:flex-row">
                    <div className="flex items-center">
                        <input
                            id="remember_me"
                            name="remember_me"
                            type="checkbox"
                            className="h-4 w-4  focus:ring-indigo-500 border-gray-300 rounded prose max-w-none text-gray-500 dark:text-gray-400"
                            checked={rememberMe}
                            onChange={e => setRememberMe(e.target.checked)}
                        />
                        <label htmlFor="remember_me" className="ml-2 block text-sm text-gray-900 dark:text-gray-100">
                            Remember me
                        </label>
                    </div>
                <div>
                    <a href="#" className="font-medium text-sm text-primary-500 hover:text-primary-600 dark:hover:text-primary-400 " onClick={onForgotPassword}>
                        Forgot your password?
                    </a>
                </div>
                </div>

            <div>
                <button onClick={handleSubmit}  className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                    Sign in
                </button>
            </div>



                <div>
                    <a href="#" className="font-medium text-sm text-primary-500 hover:text-primary-600 dark:hover:text-primary-400" onClick={onSignUp}>
                        Don't have an account? Sign up
                    </a>
                </div>

        </form>
    );
};

export default LoginForm;
