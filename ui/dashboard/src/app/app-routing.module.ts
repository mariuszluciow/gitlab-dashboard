import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent }   from './main/dashboard/dashboard.component';
import { StreamResolver }   from './main/dashboard/stream-resolver.service';
import { MainComponent }   from './main/main.component';
import { HelloComponent }   from './main/hello/hello.component';
import { LoginComponent }   from './login/login.component';
import { GroupSearchComponent }   from './main/group/group-search.component';

import { UserResolver }   from './login/user-resolver.service';
import { GroupSearchResolver }   from './main/group/group-search-resolver.service';
import { GroupResolver }   from './main/group/group-resolver.service';

const routes:Routes = [
  {path: '', redirectTo: '/hello', pathMatch: 'full'},
  {path: 'sign-in', component: LoginComponent},
  {
    path: '', component: MainComponent,
    resolve: {
      user: UserResolver
    },
    children: [
      {
        path: 'hello', component: HelloComponent,
        resolve: {
          groups: GroupResolver
        }
      },
      {
        path: 'groups/:search', component: GroupSearchComponent,
        resolve: {
          groups: GroupSearchResolver
        },
      },
      {
        path: 'group/:id/:name', component: DashboardComponent,
        resolve: {
          streams: StreamResolver
        },
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
