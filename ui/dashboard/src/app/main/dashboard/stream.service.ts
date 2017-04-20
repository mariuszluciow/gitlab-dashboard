import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { ToasterService } from 'angular2-toaster';

import 'rxjs/add/operator/toPromise';
import {Project} from './dashboard.service';

export class Stream {
  project:Project;
  loading:boolean;
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

  constructor(private http:Http, private toasterService:ToasterService) {
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
      .catch((error) => {
        if (error.status == 403) {
          this.toasterService.pop('warning', 'Missing Roles', 'You need write access to this project')
        }
        return Promise.reject(error.message || error);
      });
  }

  start(projectId:number, buildId:number):Promise<void> {
    return this.http.post(`/api/gitlab/projects/${projectId}/builds/${buildId}/start`, null)
      .toPromise()
      .catch((error) => {
        if (error.status == 403) {
          this.toasterService.pop('warning', 'Missing Roles', 'You need write access to this project')
        }
        return Promise.reject(error.message || error);
      });
  }

  cancel(projectId:number, buildId:number):Promise<void> {
    return this.http.post(`/api/gitlab/projects/${projectId}/builds/${buildId}/cancel`, null)
      .toPromise()
      .catch((error) => {
        if (error.status == 403) {
          this.toasterService.pop('warning', 'Missing Roles', 'You need write access to this project')
        }
        return Promise.reject(error.message || error);
      });
  }

  private handleError(error:any):Promise<any> {
    if (error.status == 403) {
      this.toasterService.pop('warning', 'Missing Roles', 'The build was cancelled')
    }
    return Promise.reject(error.message || error);
  }
}
