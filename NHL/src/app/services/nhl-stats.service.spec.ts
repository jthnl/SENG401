import { TestBed } from '@angular/core/testing';

import { NhlStatsService } from './nhl-stats.service';

describe('NhlStatsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NhlStatsService = TestBed.get(NhlStatsService);
    expect(service).toBeTruthy();
  });
});
