import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { User }   from './../../login/user.service';

import { Stream } from './stream.service'

@Component({
  moduleId: module.id,
  selector: 'dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {

  streams:Stream[];
  name:String;

  constructor(private route:ActivatedRoute) {
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

}
