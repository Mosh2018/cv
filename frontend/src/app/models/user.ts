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
  constructor(respons: string, message: string) {
    this.status = respons;
    this.message = message;
  }
}

export class CustomError {
  status: string;
  message: string;
  timeStamp: number;
  constructor(status: string, message: string, time: number) {
    this.status = status;
    this.message = message;
    this.timeStamp = time;
  }
}
