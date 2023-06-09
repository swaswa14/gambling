
/** @type {import('next').NextConfig} */
const testEnv =  String("http://localhost:8080")
const nextConfig = {
  reactStrictMode: true,

  env: {
   
    // getUserById: `${testEnv}/api/v1/{user}/{id}`,
    // getAllUserTransactions : `${testEnv}/api/v1/{user}/transactions/{id}`,
    // getAllUsers: `${testEnv}/api/v1/{user}/{users}`,
    // getAllUnlockedUsers : `${testEnv}/api/v1/{user}/{users}/unlocked`,
    // getAllLockedUsers : `${testEnv}/api/v1/{user}/{users}/locked`,
    // getAllEnabledUsers : `${testEnv}/api/v1/{user}/{users}/enabled`,
    // getAllDisabledUsers : `${testEnv}/api/v1/{user}/{users}/disabled`,
    // updateUser: `${testEnv}/api/v1/{user}/update/{id}`,
    // deleteUser : `${testEnv}/api/v1/{user}/delete/{id}`,
    // postRegisterUser: `${testEnv}/api/v1/auth/register/{user}`,
    // postAuthenticateUser: `${testEnv}/api/v1/auth/authenticate`,
  }
}


module.exports = nextConfig
