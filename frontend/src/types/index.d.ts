export type Entries<T> = {
  [K in keyof T]: [K, T[K]];
}[keyof T][];

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
  status: 'MOIMING' | 'COMPLETED' | 'CANCELED';
  comments: Comment[];
  isZzimed: boolean;
}

export interface Participation {
  nickname: string;
  profile: string;
  role: Role;
}

export type Role = 'MOIMER' | 'MOIMEE' | 'NON_MOIMEE';

export interface Comment {
  commentId: number;
  nickname: string;
  content: string;
  dateTime: string;
  profile: string;
  children: Comment[];
}

export type MoimInputInfo = Omit<
  MoimInfo,
  | 'moimId'
  | 'currentPeople'
  | 'participants'
  | 'status'
  | 'comments'
  | 'authorNickname'
  | 'isZzimed'
>;

export interface ChattingPreview {
  moimId: number;
  title: string;
  currentPeople: number;
  isStarted: boolean;
  lastContent: string;
  unreadContentCount: number;
}
export interface Chat {
  chatId: number;
  content: string;
  isMyMessage: boolean;
  nickname: string;
  date: string;
  time: string;
  chatType: 'BASIC' | 'PLACE' | 'DATETIME';
}

export interface PleaseInfoInput {
  title: string;
  description: string;
}
export interface Please {
  pleaseId: number;
  title: string;
  description: string;
  isInterested: boolean;
  interestCount: number;
}
