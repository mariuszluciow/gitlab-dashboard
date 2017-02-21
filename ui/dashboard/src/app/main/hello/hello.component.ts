import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Group, GroupService }   from './../group/group.service';
import { PAGE_SIZE } from './../../app.component'

@Component({
  moduleId: module.id,
  selector: 'hello',
  templateUrl: './../group/group-search.component.html',
})
export class HelloComponent implements OnInit {

  groups:Group[] = [];
  hasNext:boolean = false;
  private currentPage = 1;

  constructor(private route:ActivatedRoute, private groupService:GroupService) {
  }

  ngOnInit():void {
    this.route.data
      .subscribe((data:{ groups: Group[] }) => {
        this.groups = data.groups;
        this.hasNext = data.groups.length == PAGE_SIZE;
      });
  }

  loadNextPage():void {
    this.loadData(++this.currentPage);
  }

  private loadData(page:number):void {
    this.groupService.getAllGroups(page)
      .then(groups => {
        this.groups = this.groups.concat(groups);
        this.hasNext = groups.length == PAGE_SIZE;
      });
  }
}
