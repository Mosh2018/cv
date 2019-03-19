export class User {
  status: string;
  jwt: string;

  constructor(response: string, jwt: string) {
    this.status = response;
    this.jwt = jwt;
  }
}
