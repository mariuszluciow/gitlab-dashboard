import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { Stream } from './../stream.service'
import { ToasterService } from 'angular2-toaster';

import { Observable } from 'rxjs/Rx';

import 'rxjs/add/operator/timeInterval.js';
import 'rxjs/add/operator/switchMap.js';
import 'rxjs/add/operator/delay.js';
import {StreamService} from "../stream.service";

@Component({
  moduleId: module.id,
  selector: 'my-stream',
  templateUrl: './stream.component.html'
})
export class StreamComponent implements OnInit, OnDestroy {

  @Input()
  stream:Stream;
  private sub:any;

  constructor(private streamService:StreamService, private toasterService:ToasterService) {
  }

  ngOnInit():void {
    this.sub = Observable.interval(5000)
      .delay(this.randomIntFromInterval(10, 2000))
      .switchMap(() => this.streamService.getStream(this.stream.project.id))
      .subscribe((stream) => this.stream = stream)
  }

  ngOnDestroy():any {
    this.sub.unsubscribe();
  }

  randomIntFromInterval(min, max):number {
    return Math.floor(Math.random() * (max - min + 1) + min);
  }

  retry(projectId:number, buildId:number):void {
    this.streamService.retry(projectId, buildId)
      .then(() => this.toasterService.pop('success', 'Build Retried', 'The build was scheduled for retry'));
  }

  start(projectId:number, buildId:number):void {
    this.streamService.start(projectId, buildId)
      .then(() => this.toasterService.pop('success', 'Build Started', 'The build was scheduled'));
  }

  cancel(projectId:number, buildId:number):void {
    this.streamService.cancel(projectId, buildId)
      .then(() => this.toasterService.pop('success', 'Build Cancelled', 'The build was cancelled'));
  }

}
