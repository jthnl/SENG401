import { TestBed } from '@angular/core/testing';

import { ForumsService } from './forums.service';

describe('ForumsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ForumsService = TestBed.get(ForumsService);
    expect(service).toBeTruthy();
  });
});
