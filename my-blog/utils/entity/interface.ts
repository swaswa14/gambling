export enum Role {
    Admin = 'Admin',
    Agent = 'Agent',
    Client = 'Client'
}

enum ExceptionType {
    EntityDoesNotExistsException = 'EntityDoesNotExistsException',
    InvalidValueException = 'InvalidValueException',
    EmptyListException = 'EmptyListException',
    NullEntityException = 'NullEntityException',
    DuplicateEmailException = 'DuplicateEmailException',
    UserRegistrationErrorException = 'UserRegistrationErrorException',
    InvalidRequestException = 'InvalidRequestException',
    ValidationFieldException = 'ValidationFieldException',
    DisabledException = 'DisabledException',
    BadCredentialsException = 'BadCredentialsException',
    TokenExpiredException = 'TokenExpiredException',
    FailedToDeleteException = 'FailedToDeleteException',
    InsufficientFundsException = 'InsufficientFundsException'
}

export interface Client {
    id: number;
    role: Role;
    email: string;
    mobilePhone: string;
    balance: number;
}

export enum TransactionType {
    DEBIT = 'DEBIT',
    CREDIT = 'CREDIT',
    TRANSFER = 'TRANSFER'
}

export interface Transaction {
    id: number;
    createDate: string;
    transactionType: TransactionType;
    value: number;
    clientEmail: string;
}


export interface ApiError {
    status: string;
    errorMessage: string;
    exception: string;
    timeStamp: string;
    statusCode: string;
}

export  interface SpecificFieldError {
    fieldName: string;
    rejectedValue: any;
    errorMessage: string;
    exceptionName: ExceptionType;
    statusCode: string;
}

export  interface FieldErrors {
    numberOfErrors: number;
    errorMaps: Map<string, SpecificFieldError>;
    status: string;
    exception: ExceptionType;
}



