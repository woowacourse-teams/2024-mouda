import {
  BetDetail,
  BetSummary,
  Chat,
  ChatRoomDetail,
  ChattingPreview,
  Darakbang,
  DarakbangRole,
  MoimInfo,
  Notification,
  Participation,
  Please,
  Role,
} from '../types';

export interface GetMoims {
  data: { moims: MoimInfo[] };
}

export interface GetMoim {
  data: MoimInfo;
}

export interface PostMoimBody {
  place?: string;
  date?: string;
  time?: string;
  description?: string;
  title: string;
  maxPeople: number;
}
export interface PostMoim {
  data: number;
}

export interface GetChattingPreview {
  data: { previews: ChattingPreview[] };
}

export interface GetChatRoomDetail {
  data: ChatRoomDetail;
}
export interface GetChat {
  data: { chats: Chat[] };
}

export interface GetChamyoMine {
  data: {
    role: Role;
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

export interface GetPleases {
  data: {
    pleases: Please[];
  };
}

export interface GetMyInfo {
  data: {
    name: string;
    nickname: string;
    profile: string;
    description: string;
  };
}

export interface GetNotifications {
  data: {
    notifications: Notification[];
  };
}

export interface GetDarakbangMine {
  data: {
    darakbangResponses: Darakbang[];
  };
}

export interface GetMyRoleInDarakbang {
  data: {
    role: DarakbangRole;
  };
}

export interface GetDarakbangMembers {
  data: {
    responses: {
      darakbangMemberId: number;
      nickname: string;
      profile: string;
    }[];
  };
}

export interface GetDarakbangInviteCode {
  data: {
    code: string;
  };
}

export interface GetDarakbangNameByCode {
  data: {
    name: string;
  };
}

export interface GetBets {
  data: { bets: BetSummary[] };
}

export interface GetBet {
  data: BetDetail;
}

export interface GetBetDetail {
  data: {
    nickname: string;
  };
}

export interface PostBet {
  data: {
    betId: number;
  };
}

export interface GetDarakbangMemberProfile {
  data: {
    darakbangMemberId: number;
    name: string;
    nickname: string;
    url: string;
    description: string;
  };
}
