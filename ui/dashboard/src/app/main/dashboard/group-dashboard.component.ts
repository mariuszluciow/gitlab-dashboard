import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { User }   from './../../login/user.service';

import { Stream } from './stream.service'

@Component({
  moduleId: module.id,
  selector: 'group-dashboard',
  templateUrl: './group-dashboard.component.html'
})
export class GroupDashboardComponent implements OnInit {

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
