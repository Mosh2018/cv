export class User {
  status: string;
  jwt: string
  constructor(response: string, jwt: string) {
    this.status = response;
    this.jwt = jwt;
  }
}

export class SignUpData {
  status: string;
  message: string;
  validationResult: any[];
  constructor(respons: string, message: string) {
    this.status = respons;
    this.message = message;
  }
}

export class CustomResponse {
  status: string;
  timeStamp: number;
  validationResult: Validation[];
  constructor(status: string) {
    this.status = status;
  }
}

export class Validation {
  status: string;
  message: string;
}
