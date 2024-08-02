import { MoimInfo, Participation } from '../types';

export interface GetMoims {
  data: { moims: MoimInfo[] };
}

export interface GetMoim {
  data: MoimInfo;
}

export interface GetChamyoMine {
  data: {
    role: 'MOIMER' | 'MOIMEE' | 'NON_MOIMEE';
  };
}

export interface GetChamyoAll {
  data: {
    chamyos: Participation[];
  };
}

export interface GetZzimMine {
  data: {
    isZzimed: boolean;
  };
}

export interface PostMoim {
  data: number;
}
