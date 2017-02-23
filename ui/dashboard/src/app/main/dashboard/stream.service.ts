import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

export class Stream {
  project:{
    id:number;
    name:string;
    description:string
    web_url:string
    builds_enabled:boolean
  };
  stages:[{
    name:string;
    builds: [{
      id:number;
      status:string;
      stage:string;
      name:string;
      started_at:Date;
      created_at:Date;
      finished_at:Date;
      user: {
        name:string;
        username:string;
        avatar_url:string;
        web_url:string;
      };
      pipeline:{
        status:string;
        ref:string;
        sha:string;
      }
    }]
  }]
}


@Injectable()
export class StreamService {

  private gitLabUrl = '/api/gitlab';

  constructor(private http:Http) {
  }

  getStreams(groupId:number):Promise<Stream[]> {
    return this.http.get(`${this.gitLabUrl}/groups/${groupId}`)
      .toPromise()
      .then(response => response.json() as Stream[])
      .catch(this.handleError);
  }

  getStream(projectId:number):Promise<Stream> {
    return this.http.get(`${this.gitLabUrl}/projects/${projectId}`)
      .toPromise()
      .then(response => response.json() as Stream)
      .catch(this.handleError);
  }

  retry(projectId:number, buildId:number):Promise<void> {
    return this.http.post(`/api/gitlab/projects/${projectId}/builds/${buildId}/retry`, null)
      .toPromise()
      .catch(this.handleError);
  }

  start(projectId:number, buildId:number):Promise<void> {
    return this.http.post(`/api/gitlab/projects/${projectId}/builds/${buildId}/start`, null)
      .toPromise()
      .catch(this.handleError);
  }

  cancel(projectId:number, buildId:number):Promise<void> {
    return this.http.post(`/api/gitlab/projects/${projectId}/builds/${buildId}/cancel`, null)
      .toPromise()
      .catch(this.handleError);
  }

  private handleError(error:any):Promise<any> {
    return Promise.reject(error.message || error);
  }
}
