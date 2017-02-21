import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

export class User {
  userAuthentication:{
    details: {
      name: string;
      username: string;
      id: string;
      avatar_url: string;
      web_url: string;
      email: string;
    }
  };
  name:string;
}


@Injectable()
export class UserService {

  private userUrl = '/api/user';  // URL to web api

  constructor(private http:Http) {
  }

  getUser():Promise<User> {
    return this.http.get(this.userUrl)
      .toPromise()
      .then(response => response.json() as User)
      .catch(this.handleError);
  }

  private handleError(error:any):Promise<any> {
    return Promise.reject(error.message || error);
  }
}
