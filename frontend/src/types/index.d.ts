export interface MoimInfo {
  moimId: number;
  title: string;
  date: string;
  time: string;
  place: string;
  currentPeople: number;
  maxPeople: number;
  currentPeople: number;
  authorNickname: string;
  participants: string[];
  description?: string;
}

export interface Participation {
  nickname: string;
  src: string;
  role: Role;
}

export type Role = 'moimer' | 'moimee';

export interface Comment {
  id: number;
  nickname: string;
  content: string;
  dateTime: string;
  src: string;
  child: Comment[];
}
export type MoimInputInfo = Omit<
  MoimInfo,
  'moimId' | 'currentPeople' | 'participants'
>;
