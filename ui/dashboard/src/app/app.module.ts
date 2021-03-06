import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { SlimLoadingBarModule } from 'ng2-slim-loading-bar';
import { CookieService } from 'angular2-cookie/services/cookies.service';

import { AppComponent } from './app.component';
import { GroupDashboardComponent }   from './main/dashboard/group-dashboard.component';
import { NewDashboardComponent }   from './main/dashboard/new-dashboard.component';
import { DashboardsComponent }   from './main/dashboard/dashboards.component';
import { HeaderComponent }   from './main/header/header.component';
import { GroupSearchComponent }   from './main/group/group-search.component';
import { LoginComponent }   from './login/login.component';
import { MainComponent }   from './main/main.component';
import { HelloComponent }   from './main/hello/hello.component';
import { StreamComponent }   from './main/dashboard/stream/stream.component';

import { UserService }   from './login/user.service';
import { UserResolver }   from './login/user-resolver.service';
import { GroupService }   from './main/group/group.service';
import { GroupSearchResolver }   from './main/group/group-search-resolver.service';
import { GroupResolver }   from './main/group/group-resolver.service';
import { StreamService }   from './main/dashboard/stream.service';
import { StreamResolver }   from './main/dashboard/stream-resolver.service';

import { AppRoutingModule } from './app-routing.module';
import { ToasterModule } from 'angular2-toaster';
import { DashboardService } from './main/dashboard/dashboard.service';
import { DashboardResolver } from './main/dashboard/dashboard-resolver.service';
import {DragulaModule} from "ng2-dragula/index";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    GroupDashboardComponent,
    NewDashboardComponent,
    DashboardsComponent,
    MainComponent,
    HeaderComponent,
    GroupSearchComponent,
    HelloComponent,
    StreamComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    SlimLoadingBarModule.forRoot(),
    ToasterModule,
    DragulaModule
  ],
  providers: [
    UserService,
    UserResolver,
    {provide: CookieService, useFactory: cookieServiceFactory}, //because of https://github.com/salemdar/angular2-cookie/issues/37
    GroupService,
    StreamService,
    StreamResolver,
    GroupSearchResolver,
    GroupResolver,
    DashboardService,
    DashboardResolver
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export function cookieServiceFactory() {
  return new CookieService();
}
