import {
  Chat,
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
  data: { chatPreviewResponses: ChattingPreview[] };
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
    nickname: string;
    profile: string;
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
    darakbangMemberResponses: {
      memberId: number;
      nickname: string;
      profile: string;
    }[];
  };
}
