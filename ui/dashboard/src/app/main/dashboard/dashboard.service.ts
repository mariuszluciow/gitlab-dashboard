import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { ToasterService } from 'angular2-toaster';

import 'rxjs/add/operator/toPromise';

export class Dashboard {
  name:string = "";
  id:number;
  projectIdsCol1:number[] = [];
  projectIdsCol2:number[] = [];
}

export class Project {
  id:number;
  name:string;
  web_url:string;
  description:string;
  builds_enabled:boolean;

  constructor(id:number) {
    this.id = id;
  }
}

@Injectable()
export class DashboardService {

  constructor(private http:Http, private toasterService:ToasterService) {
  }

  saveDashboard(dashboard:Dashboard):Promise<Dashboard> {
    return this.http.post(`/api/rogue/dashboards`, dashboard)
      .toPromise()
      .then(response => response.json() as Dashboard)
      .catch(this.handleError);
  }

  getDashboard(dashboardId:number):Promise<Dashboard> {
    return this.http.get(`/api/rogue/dashboards/${dashboardId}`)
      .toPromise()
      .then(response => response.json() as Dashboard)
      .catch(this.handleError);
  }

  getProjects(query:string):Promise<Project[]> {
    return this.http.get(`/api/rogue/dashboards/projects?search=${query}`)
      .toPromise()
      .then(response => response.json() as Project[])
      .catch(this.handleError);
  }

  getAllDashboards(page:number):Promise<Dashboard[]> {
    return this.http.get(`/api/rogue/dashboards?page=${page}`)
      .toPromise()
      .then(response => response.json() as Dashboard[])
      .catch(this.handleError);
  }

  private handleError(error:any):Promise<any> {
    if (error.status == 403) {
      this.toasterService.pop('warning', 'Missing Roles', 'The build was cancelled')
    }
    return Promise.reject(error.message || error);
  }
}
