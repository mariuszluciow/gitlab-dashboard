import { Injectable }             from '@angular/core';
import { Router, Resolve, RouterStateSnapshot,
  ActivatedRouteSnapshot } from '@angular/router';

import { User, UserService } from './user.service';

@Injectable()
export class UserResolver implements Resolve<User> {
  constructor(private userService:UserService, private router:Router) {
  }

  resolve(route:ActivatedRouteSnapshot, state:RouterStateSnapshot):Promise<User> {

    return this.userService.getUser().then(user => {
      if (user.name) {
        return user;
      } else {
        this.router.navigate(['/sign-in']);
        return null;
      }
    });
  }
}
