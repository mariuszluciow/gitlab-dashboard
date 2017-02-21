import { Injectable }             from '@angular/core';
import { Resolve, RouterStateSnapshot,
  ActivatedRouteSnapshot } from '@angular/router';

import { GroupService, Group }   from './../group/group.service';

@Injectable()
export class GroupSearchResolver implements Resolve<Group[]> {
  constructor(private groupService:GroupService) {
  }

  resolve(route:ActivatedRouteSnapshot, state:RouterStateSnapshot):Promise<Group[]> {
    return this.groupService.getGroups(route.params['search'], 1).then(groups => {
      return groups;
    });
  }
}
