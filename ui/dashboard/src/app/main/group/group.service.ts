import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

export class Group {
  id:number;
  name:string;
  path:string;
  avatar_url:string;
  web_url:string;
}


@Injectable()
export class GroupService {

  private groupUrl = '/api/gitlab/groups';

  constructor(private http:Http) {
  }

  getGroups(search:string, page:number):Promise<Group[]> {
    return this.http.get(`${this.groupUrl}?search=${search}&page=${page}`)
      .toPromise()
      .then(response => response.json() as Group[])
      .catch(this.handleError);
  }

  getAllGroups(page:number):Promise<Group[]> {
    return this.http.get(`${this.groupUrl}?page=${page}`)
      .toPromise()
      .then(response => response.json() as Group[])
      .catch(this.handleError);
  }

  private handleError(error:any):Promise<any> {
    return Promise.reject(error.message || error);
  }
}
