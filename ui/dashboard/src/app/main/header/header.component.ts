import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
//import { CookieService } from 'angular2-cookie/services/cookies.service';

import { User }   from './../../login/user.service';

@Component({
  moduleId: module.id,
  selector: 'my-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent {

  @Input()
  user:User;

  csrf:string;
  search:string;

  constructor(//private cookieService:CookieService,
    private router:Router) {
    //this.csrf = cookieService.get('XSRF-TOKEN');
  }

  gotoSearch():void {
    let link = ['/groups', this.search];
    this.router.navigate(link);
  }

}
