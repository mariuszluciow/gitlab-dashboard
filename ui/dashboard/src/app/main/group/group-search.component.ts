import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { GroupService, Group }   from './../group/group.service';
import { PAGE_SIZE } from './../../app.component'

import 'rxjs/add/operator/map';

@Component({
  moduleId: module.id,
  selector: 'group-search',
  templateUrl: './group-search.component.html'
})
export class GroupSearchComponent implements OnInit {

  groups:Group[] = [];
  hasNext:boolean = false;
  search:string;
  private currentPage = 1;

  constructor(private route:ActivatedRoute,
              private groupService:GroupService) {
  }

  ngOnInit():void {
    this.route.data
      .subscribe((data:{ groups: Group[] }) => {
        this.groups = data.groups;
        this.hasNext = data.groups.length == PAGE_SIZE;
      });

    this.route.params
      .map((params:Params) => params['search'])
      .subscribe(search => {
        this.search = search;
      });
  }

  loadNextPage():void {
    this.loadData(this.search, ++this.currentPage);
  }

  private loadData(search:string, page:number):void {
    this.groupService.getGroups(search, page)
      .then(groups => {
        this.groups = this.groups.concat(groups);
        this.hasNext = groups.length == PAGE_SIZE;
      });
  }
}
