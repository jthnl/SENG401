import { TestBed } from '@angular/core/testing';

import { NhlWebappService } from './nhl-webapp.service';

describe('NhlWebappService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NhlWebappService = TestBed.get(NhlWebappService);
    expect(service).toBeTruthy();
  });
});
