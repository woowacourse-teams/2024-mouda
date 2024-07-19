import { MoimInfo } from '../types';

export interface GetMoim {
  data: { moims: MoimInfo[] };
}

export interface PostMoim {
  id: number;
}
