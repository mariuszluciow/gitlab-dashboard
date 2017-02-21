import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { User }   from './../../login/user.service';

import { Stream } from './stream.service'
import { BuildService } from "./../build/build.service";
import { ToasterService } from 'angular2-toaster';

@Component({
  moduleId: module.id,
  selector: 'dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {

  streams:Stream[];
  name:String;

  constructor(private route:ActivatedRoute, private buildService:BuildService, private toasterService:ToasterService) {
  }

  ngOnInit():void {
    this.route.data
      .subscribe((data:{ streams: Stream[] }) => {
        this.streams = data.streams;
      });

    this.route.params
      .map((params:Params) => params['name'])
      .subscribe(name => this.name = name);
  }

  retry(projectId:number, buildId:number):void {
    this.buildService.retry(projectId, buildId)
      .then(() => this.toasterService.pop('success', 'Build Retried', 'The build was scheduled for retry'));
  }

  start(projectId:number, buildId:number):void {
    this.buildService.start(projectId, buildId)
      .then(() => this.toasterService.pop('success', 'Build Started', 'The build was scheduled'));
  }

  cancel(projectId:number, buildId:number):void {
    this.buildService.cancel(projectId, buildId)
      .then(() => this.toasterService.pop('success', 'Build Cancelled', 'The build was cancelled'));
  }

}
