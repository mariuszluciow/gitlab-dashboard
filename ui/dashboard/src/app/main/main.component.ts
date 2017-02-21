import { Component,OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { User }   from './../login/user.service';

@Component({
  moduleId: module.id,
  selector: 'main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  user:User;

  constructor(private route:ActivatedRoute) {
  }

  ngOnInit():void {
    this.route.data
      .subscribe((data:{ user: User }) => {
        this.user = data.user;
      });
  }
}
