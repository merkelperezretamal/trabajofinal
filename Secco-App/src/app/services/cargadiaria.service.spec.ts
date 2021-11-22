import { TestBed } from '@angular/core/testing';

import { CargadiariaService } from './cargadiaria.service';

describe('CargadiariaService', () => {
  let service: CargadiariaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CargadiariaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
