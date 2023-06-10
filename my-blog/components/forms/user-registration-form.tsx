import React, { useState } from 'react';



const RegistrationForm: React.FC = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [verifyPassword, setVerifyPassword] = useState('');
    const [invitationCode, setInvitationCode] = useState('');
    const [mobilePhone, setMobilePhone] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        // Implement your registration logic here
    };

    return (
        <form className="space-y-6 w-5/12" onSubmit={handleSubmit}>
            <div className="flex flex-col sm:flex-row space-y-4 sm:space-y-0 space-x-0 sm:space-x-6">
                <div className="flex-1">
                    <label className="block text-sm font-medium text-gray-700">
                        First Name
                    </label>
                    <input
                        type="text"
                        value={firstName}
                        onChange={e => setFirstName(e.target.value)}
                        className="shadow-sm focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600 px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                    />
                </div>
                <div className="flex-1">
                    <label className="block text-sm font-medium text-gray-700">
                        Last Name
                    </label>
                    <input
                        type="text"
                        value={lastName}
                        onChange={e => setLastName(e.target.value)}
                        className="shadow-sm focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600 px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                    />
                </div>
            </div>
            <div>
                <label className="block text-sm font-medium text-gray-700">
                    Email
                </label>
                <input
                    type="email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    className="shadow-sm focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600 px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                />
            </div>
            <div className="flex flex-col sm:flex-row space-y-4 sm:space-y-0 space-x-0 sm:space-x-6 ">
                <div className="flex-1">
                    <label className="block text-sm font-medium text-gray-700">
                        Mobile Phone
                    </label>
                    <input
                        type="tel"
                        value={mobilePhone}
                        onChange={e => setMobilePhone(e.target.value)}
                        className="shadow-sm focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600 px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                    />
                </div>
                <div className="flex-1">
                    <label className="block text-sm font-medium text-gray-700">
                        Invitation Code
                    </label>
                    <input
                        type="text"
                        value={invitationCode}
                        onChange={e => setInvitationCode(e.target.value)}
                        className="shadow-sm focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md focus:ring-primary-600 px-4 focus:border-transparent focus:outline-none focus:ring-2 dark:bg-black"
                    />

                </div>
            </div>
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
                    Register
                </button>
            </div>
        </form>
    );
};

export default RegistrationForm;
