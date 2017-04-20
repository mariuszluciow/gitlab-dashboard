import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';

import { Observable }        from 'rxjs/Observable';
import { Subject }           from 'rxjs/Subject';

import { User }   from './../../login/user.service';

import { Stream } from './stream.service'
import { Dashboard,Project } from './dashboard.service'
import { DashboardService } from './dashboard.service';
import { ToasterService } from 'angular2-toaster/angular2-toaster';

@Component({
  moduleId: module.id,
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class NewDashboardComponent implements OnInit {

  firstCol:Stream[] = [];
  secondCol:Stream[] = [];
  dashboard:Dashboard = new Dashboard;
  showProjectsPanel:boolean = false;
  private searchTerms = new Subject<string>();
  projects:Observable<Project[]>;

  constructor(private dashboardService:DashboardService, private toasterService:ToasterService,
              private route:ActivatedRoute, private router:Router) {
  }

  ngOnInit():void {
    this.route.params
      .map((params:Params) => params['id'])
      .filter(id => id)
      .subscribe(id => this.dashboardService.getDashboard(id)
        .then(dashboard => {
          this.dashboard = dashboard;
          dashboard.projectIdsCol1.forEach(id => {
            let stream = new Stream;
            stream.project = new Project(id);
            stream.loading = true;
            this.firstCol.push(stream);
          })
          dashboard.projectIdsCol2.forEach(id => {
            let stream = new Stream;
            stream.project = new Project(id);
            stream.loading = true;
            this.secondCol.push(stream);
          })
        })
      );

    this.projects = this.searchTerms
      .debounceTime(300)
      .distinctUntilChanged()
      .switchMap(term => term
        ? this.dashboardService.getProjects(term)
        : Observable.of<Project[]>([]))
      .catch(error => {
        this.toasterService.pop('error', 'Something went wrong');
        return Observable.of<Project[]>([]);
      });
  }

  search(term:string):void {
    this.searchTerms.next(term);
  }

  projectsPanel():void {
    this.showProjectsPanel = !this.showProjectsPanel;
  }

  addProject(project:Project):void {
    let stream = new Stream;
    stream.project = project;
    stream.loading = true;
    this.firstCol.push(stream);
  }

  save():void {
    this.dashboard.projectIdsCol1 = this.firstCol.map(stream => stream.project.id);
    this.dashboard.projectIdsCol2 = this.secondCol.map(stream => stream.project.id);
    this.dashboardService.saveDashboard(this.dashboard)
      .then((dashboard) => {
        this.toasterService.pop('success', 'Dashboard Saved', 'the dashboard was saved successfully');
        this.router.navigate(['/dashboard', dashboard.id]);
      });
  }
}
