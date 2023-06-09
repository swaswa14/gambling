export interface ClientRegistrationForm{
    email: string;
    password: string;
    invitationCode: string;
    mobilePhone: string;
}

export interface AuthenticationForm{
    username : string;
    password : string;
}
