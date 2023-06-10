import React, {useState, useEffect, useRef, ReactChild, ReactNode, RefObject} from 'react';
import {useRouter} from "next/router";
import {Props} from "next/script";
import ElementChildrenAttribute = React.JSX.ElementChildrenAttribute;
import { motion, useSpring } from 'framer-motion';
import Modal from 'react-modal';
import {number} from "property-information/lib/util/types";
import {CircularProgress, Divider} from '@mui/material';
import PageTitle from "@/components/PageTitle";
import {SafetyDividerOutlined} from "@mui/icons-material";
interface ForgotPasswordProps {
}

const ForgotPasswordForm: React.FC<ForgotPasswordProps> = () => {

    const [step, setStep] = useState(0);

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        // Implement your forgot password logic here
        setStep(step + 1);
    };



    return (
        <>
        <head>
            <title>Forgot password</title>
        </head>


                <div className="space-y-6 w-full sm:w-4/12 h-full" >
                    <h4 className={"font-mono font-extrabold  uppercase text-gray-900 dark:text-gray-100   tracking-wide "}>Reset Password</h4>
                    <Divider/>
                    {step === 0 && (
                        <StepOne handleSubmit={handleSubmit}/>
                    )}
                    {step === 1 && (
                        <StepTwo
                            handleSubmit={handleSubmit}
                        />
                    )}
                    {step === 2 && (
                        <StepThree/>
                    )}
                </div>


        </>

    );
};

interface handleStepProps {
    handleSubmit: (event: React.FormEvent) => void;
}






export const StepOne: React.FC<handleStepProps> = ({ handleSubmit }) => {
    const [email, setEmail] = useState('');
    return(
        <form onSubmit={handleSubmit}>
            <div>
                <label className="block text-sm font-medium text-gray-700">
                    Email
                </label>
                <div className="mt-1">
                    <input
                        type="email"
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        className="shadow-sm focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600 px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                    />
                </div>
            </div>

            <div className="mt-4">
                <button type="submit" className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                    Submit
                </button>
            </div>
        </form>
    )
}







export const StepTwo: React.FC<handleStepProps> = ({ handleSubmit }) => {
    const [expired, setExpired] = useState(false);
    const [timer, setTimer] = useState(300); // 300 seconds = 5 minutes
    const inputsRef = useRef([]);
    const router = useRouter()


    useEffect(() => {
        if (timer > 0) {
            const timeout = setTimeout(() => setTimer(timer - 1), 1000); // decrease time every second
            return () => clearTimeout(timeout);
        } else {
            setExpired(true);
        }
    }, [timer]);
    const handleInputChange = (digit, index) => {
        const num = Number(digit);
        if (!isNaN(num)) {
            if (index < 5 && inputsRef.current[index + 1]) {
                inputsRef.current[index + 1].focus(); // move focus to next input box
            }
            setCode([...code.slice(0, index), num, ...code.slice(index + 1)]); // update code
        }
    }

    const [code, setCode] = useState(Array(6).fill(''));
    const handlePaste = (event) => {
        event.preventDefault();
        const pastedData = event.clipboardData.getData('text/plain');
        setCode([...pastedData.slice(0, 6)]); // update the code state with the pasted data
    };

    return (
        <div>
            {!expired ? (
                    <div>An code has been sent to your email address</div>
                ) :
                null
            }
            {expired ? (
                <p>Your code has expired. Please request a new one.</p>
            ) : (
                <form onSubmit={handleSubmit}>
                    <div className="flex space-x-2 mt-5" onPaste={handlePaste}>
                        {code.map((digit, index) => (
                            <input
                                key={index}
                                type="number"
                                value={digit}
                                onChange={(e) => handleInputChange(e.target.value, index)}
                                className="shadow-sm focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600 px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                                ref={el => inputsRef[index] = el}
                            />
                        ))}
                    </div>
                    <button type="submit" disabled={expired} className="mt-10 w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                        Verify
                    </button>
                </form>
            )}
            {
                !expired ?
                    (
                        <div className={"mt-2 block text-sm font-medium text-gray-700"}>
                            You have <TimerComponent timer={timer}>{Math.floor(timer / 60)}</TimerComponent> minutes and <TimerComponent timer={timer}>{timer % 60}</TimerComponent> seconds to enter the code.
                        </div>
                    ) :
                    (
                        <button onClick={()=> {router.reload()}} className="mt-10 w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            Reset again
                        </button>)
            }
        </div>
    )
}



export const StepThree: React.FC = () => {
    const [password, setPassword] = useState('');
    const [verifyPassword, setVerifyPassword] = useState('');
    const [isModalOpen, setIsModalOpen] = useState(false);
    const router = useRouter();
    const opacity = useSpring(1, { duration: 1.5 });
    const marginTop = useSpring(0, { duration: 1.5 });
    const [isRedirecting, setIsRedirecting] = useState(false);

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        setIsModalOpen(true);
        setTimeout(() => {
            setIsModalOpen(false);
            setIsRedirecting(true);
        }, 1500);
    };

    useEffect(() => {
        if (isRedirecting) {
            setTimeout(() => {
                router.push('/login');
            }, 5000);
        }
    }, [isRedirecting, router]);


    return(
        <>
            {isRedirecting && (
                <div className="flex justify-center align-top items-center h-screen mt-0">
                    <motion.div
                        initial={{ opacity: 0 }}
                        animate={{ opacity: 1 }}
                        transition={{ duration: 0.3 }}
                        className="text-2xl"
                    >
                        <div className={"flex-col space-y-3 justify-center"}>
                            <span>Redirecting...</span>
                            <br/>
                            <CircularProgress />
                        </div>


                    </motion.div>
                </div>
            )}
        {!isRedirecting && (
        <form className="space-y-6 w-5/12" onSubmit={handleSubmit}>
            <div>

                <label className="block text-sm font-medium text-gray-700">
                    Verify Password
                </label>
                <input
                    type="password"
                    value={verifyPassword}
                    onChange={e => setVerifyPassword(e.target.value)}
                    className="shadow-sm focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600 px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                />
            </div>
            <div>
                <label className="block text-sm font-medium text-gray-700">
                    Password
                </label>
                <input
                    type="password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    className="shadow-sm focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600 px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                />

            </div>
            <div>
                <button
                    className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                    Change password
                </button>
            </div>
        </form>
        )}
            <Modal
                isOpen={isModalOpen}
                contentLabel="Success Modal"
                style={{
                    overlay: {
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                    },
                    content: {
                        position: 'relative',
                        top: 'auto',
                        left: 'auto',
                        right: 'auto',
                        bottom: 'auto',
                        transform: 'none',
                        background: 'none',
                        border: 'none',
                        padding: 'none',
                    },
                }}
            >
                <motion.div style={{ opacity, marginTop }} >
                    <div className="p-6 space-y-4 text-center border w-72 md:w-96 m-auto bg-white rounded-xl shadow-xl">
                        <div className="text-green-500 mx-auto">
                            {/* You can replace this with a checkmark icon */}
                            <AnimatedCheckMark/>
                        </div>
                        <div className="text-xl leading-6 font-medium text-gray-900">
                            Password has been changed
                        </div>
                    </div>
                </motion.div>
            </Modal>

            </>
    )
}







interface TimerComponentProps {
    timer: number;
    children: React.ReactNode;
}

export function TimerComponent({timer, children}: TimerComponentProps ){
    return (
        <span
            className={`
                                ${
                timer / 300 > 0.9
                    ? 'text-gray-700'
                    : timer / 300 > 0.8
                        ? 'text-red-50'
                        : timer / 300 > 0.7
                            ? 'text-red-100'
                            : timer / 300 > 0.6
                                ? 'text-red-200'
                                : timer / 300 > 0.5
                                    ? 'text-red-300'
                                    : timer / 300 > 0.4
                                        ? 'text-red-400'
                                        : timer / 300 > 0.3
                                            ? 'text-red-500'
                                            : timer / 300 > 0.2
                                                ? 'text-red-600'
                                                : timer / 300 > 0.1
                                                    ? 'text-red-700'
                                                    : 'text-red-700'
            }
                                
                                `}
        >
            {children}
        </span>
    )
}

export  function AnimatedCheckMark(){
    return(
        <div className="wrapper">
            <svg className="checkmark checkmark-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
                <circle className="checkmark__circle" cx="26" cy="26" r="25" fill="none"/>
                <path className="checkmark__check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/>
            </svg>
        </div>
    )
}
export default ForgotPasswordForm;
