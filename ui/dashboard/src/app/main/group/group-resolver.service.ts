import { Injectable }             from '@angular/core';
import { Resolve } from '@angular/router';

import { GroupService, Group }   from './../group/group.service';

@Injectable()
export class GroupResolver implements Resolve<Group[]> {
  constructor(private groupService:GroupService) {
  }

  resolve():Promise<Group[]> {
    return this.groupService.getAllGroups(1).then(groups => {
      return groups;
    });
  }
}
