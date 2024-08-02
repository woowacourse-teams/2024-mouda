export interface MoimInfo {
  moimId: number;
  title: string;
  date: string;
  time: string;
  place: string;
  maxPeople: number;
  currentPeople: number;
  authorNickname: string;
  participants: Participation[];
  description?: string;
  status: 'MOIMING' | 'COMPLETE' | 'CANCEL';
  comments: Comment[];
  isZzimed: boolean;
}

export interface Participation {
  nickname: string;
  profile: string;
  role: Role;
}

export type Role = 'moimer' | 'moimee';

export interface Comment {
  commentId: number;
  nickname: string;
  content: string;
  dateTime: string;
  profile: string;
  child: Comment[];
}
export type MoimInputInfo = Omit<
  MoimInfo,
  | 'moimId'
  | 'currentPeople'
  | 'participants'
  | 'status'
  | 'comments'
  | 'isZzimed'
>;

export type TempMoimInputInfo = Omit<
  MoimInfo,
  | 'moimId'
  | 'currentPeople'
  | 'participants'
  | 'status'
  | 'comments'
  | 'authorNickname'
  | 'isZzimed'
>;
