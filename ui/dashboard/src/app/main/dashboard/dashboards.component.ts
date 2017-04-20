import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';

import { PAGE_SIZE } from './../../app.component'

import { Stream } from './stream.service'
import { Dashboard,Project } from './dashboard.service'
import { DashboardService } from './dashboard.service';
import { ToasterService } from 'angular2-toaster/angular2-toaster';

@Component({
  moduleId: module.id,
  selector: 'dashboard',
  templateUrl: './dashboards.component.html'
})
export class DashboardsComponent implements OnInit {

  dashboards:Dashboard[] = [];
  hasNext:boolean = false;
  private currentPage = 1;

  constructor(private route:ActivatedRoute, private dashboardService:DashboardService) {
  }

  ngOnInit():void {
    this.route.data
      .subscribe((data:{ dashboards: Dashboard[] }) => {
        console.log(data);
        this.dashboards = data.dashboards;
        this.hasNext = data.dashboards.length == PAGE_SIZE;
      });
  }

  loadNextPage():void {
    this.loadData(++this.currentPage);
  }

  private loadData(page:number):void {
    this.dashboardService.getAllDashboards(page)
      .then(dashboards => {
        this.dashboards.push(...dashboards);
        this.hasNext = dashboards.length == PAGE_SIZE;
      });
  }
}
