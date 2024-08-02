export interface MoimInfo {
  moimId: number;
  title: string;
  date: string;
  time: string;
  place: string;
  maxPeople: number;
  currentPeople: number;
  authorNickname: string;
  participants: string[];
  description?: string;
  zzim: boolean;
}

export interface Participation {
  nickname: string;
  src: string;
  role: Role;
}

export type Role = 'moimer' | 'moimee';

export type MoimInputInfo = Omit<
  MoimInfo,
  'moimId' | 'currentPeople' | 'participants' | 'zzim'
>;
