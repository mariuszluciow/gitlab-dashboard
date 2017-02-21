import { Injectable }             from '@angular/core';
import { Resolve, RouterStateSnapshot,
  ActivatedRouteSnapshot } from '@angular/router';

import { Stream, StreamService } from './stream.service';

@Injectable()
export class StreamResolver implements Resolve<Stream[]> {
  constructor(private streamService:StreamService) {
  }

  resolve(route:ActivatedRouteSnapshot, state:RouterStateSnapshot):Promise<Stream[]> {
    return this.streamService.getStreams(route.params['id']).then(streams => {
      return streams;
    });
  }
}
