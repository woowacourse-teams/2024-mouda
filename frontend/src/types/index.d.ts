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
  status: MoimStatus;
  comments: Comment[];
  isZzimed: boolean;
  chatRoomId: number | null;
}

export interface Participation {
  darakbangMemberId: number;
  nickname: string;
  profile: string;
  role: Role;
}

export type MoimStatus = 'MOIMING' | 'COMPLETED' | 'CANCELED';
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
  | 'chatRoomId'
>;

export interface ChattingPreview {
  chatRoomId: number;
  title: string;
  participations: Participation[];
  isStarted: boolean;
  lastContent: string;
  unreadChatCount: number;
}
export interface Chat {
  chatId: number;
  content: string;
  isMyMessage: boolean;
  participation: Participation;
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

export type NotificationType =
  | 'MOIM_CREATED'
  | 'MOIMING_COMPLETED'
  | 'MOIMING_REOPENED'
  | 'MOIM_CANCELED'
  | 'MOIM_MODIFIED'
  | 'NEW_COMMENT'
  | 'NEW_REPLY'
  | 'NEW_CHAT'
  | 'NEW_MOIMEE_JOINED'
  | 'MOIMEE_LEFT'
  | 'MOIM_PLACE_COMFIRMED'
  | 'MOIM_TIME_CONFIRMED';

export interface Notification {
  type: NotificationType;
  message: string;
  createdAt: string;
  redirectUrl: string;
}

export interface Darakbang {
  darakbangId: number;
  name: string;
}

export type DarakbangRole = 'MANAGER' | 'MEMBER' | 'OUTSIDER';

export interface Participant {
  nickname: string;
  id: number;
  profileUrl: string;
}

export interface BetSummary {
  id: number;
  title: string;
  currentParticipants: number;
  deadline: `${number}-${number}-${number}T${number}:${number}:${number}`;
  isAnnounced: boolean;
}

export interface BetDetail {
  title: string;
  currentParticipants: number;
  deadline: `${number}-${number}-${number}T${number}:${number}:${number}`;
  isAnnounced: boolean;
  participants: Participant[];
  myRole: Role;
  chatroomId: number | null;
}

export interface BetInputInfo {
  title: string;
  waitingMinutes: number;
}

export type ChatRoomType = 'BET' | 'MOIM';

export interface ChatRoomDetail {
  chatRoomId: number;
  attributes: object;
  type: ChatRoomType;
  title: string;
  participations: Participation[];
}

export interface MoimChatRoomDetail extends ChatRoomDetail {
  type: 'MOIM';
  attributes: {
    place: string;
    isMoimer: boolean;
    isStarted: boolean;
    description?: string;
    date: string;
    time: string;
    moimId: number;
  };
}

export interface BetChatRoomDetail extends ChatRoomDetail {
  type: 'BET';
  attributes: {
    isLoser: boolean;
    betId: number;
    loser: Participation;
  };
}

export const isBetChatRoomDetail = (
  detail: ChatRoomDetail,
): detail is BetChatRoomDetail => {
  return (detail as BetChatRoomDetail).attributes.betId !== undefined;
};

export const isMoimChatRoomDetail = (
  detail: ChatRoomDetail,
): detail is MoimChatRoomDetail => {
  return (detail as MoimChatRoomDetail).attributes.moimId !== undefined;
};
