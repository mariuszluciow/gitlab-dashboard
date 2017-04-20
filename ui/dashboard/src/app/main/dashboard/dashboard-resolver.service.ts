import { Injectable }             from '@angular/core';
import { Resolve } from '@angular/router';

import {Dashboard, DashboardService} from './dashboard.service';

@Injectable()
export class DashboardResolver implements Resolve<Dashboard[]> {
  constructor(private dashboardService:DashboardService) {
  }

  resolve():Promise<Dashboard[]> {
    return this.dashboardService.getAllDashboards(0).then(dashboards => {
      return dashboards;
    });
  }
}
