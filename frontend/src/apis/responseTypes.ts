import {
  Chat,
  ChattingPreview,
  MoimInfo,
  Participation,
  Please,
} from '../types';

export interface GetMoims {
  data: { moims: MoimInfo[] };
}

export interface GetMoim {
  data: MoimInfo;
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
