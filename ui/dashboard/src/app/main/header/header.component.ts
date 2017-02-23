import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'angular2-cookie/services/cookies.service';

import { User }   from './../../login/user.service';

@Component({
  moduleId: module.id,
  selector: 'my-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {

  @Input()
  user:User;

  csrf:string;
  search:string;

  constructor(private cookieService:CookieService,
              private router:Router) {
  }

  ngOnInit():void {
    this.csrf = this.cookieService.get('XSRF-TOKEN');
  }

  gotoSearch():void {
    let link = ['/groups', this.search];
    this.router.navigate(link);
  }

}
