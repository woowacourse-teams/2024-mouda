import { BetInputInfo, MoimInputInfo, PleaseInfoInput } from '@_types/index';
import { PostBet, PostMoim, PostMoimBody } from './responseTypes';

import ApiClient from './apiClient';

export const postMoim = async (moim: MoimInputInfo): Promise<number> => {
  const parsedMoim: PostMoimBody = {
    ...moim,
    date: moim.date || undefined,
    time: moim.time || undefined,
    place: moim.place || undefined,
  };

  const response = await ApiClient.postWithLastDarakbangId('/moim', parsedMoim);

  const json: PostMoim = await response.json();
  return json.data;
};

export const postJoinMoim = async (moimId: number) => {
  await ApiClient.postWithLastDarakbangId('/chamyo', {
    moimId,
  });
};

export const postChangeZzim = async (moimId: number) => {
  await ApiClient.postWithLastDarakbangId('/zzim', {
    moimId,
  });
};
export const postWriteComment = async (
  moimId: number,
  selectedComment: number,
  message: string,
) => {
  if (selectedComment === 0) {
    await ApiClient.postWithLastDarakbangId(`/moim/${moimId}`, {
      content: message,
    });
  } else {
    await ApiClient.postWithLastDarakbangId(`/moim/${moimId}`, {
      parentId: selectedComment,
      content: message,
    });
  }
};

export const postChat = async (chatRoomId: number, content: string) => {
  await ApiClient.postWithLastDarakbangId(`/chatroom/${chatRoomId}`, {
    content,
  });
};

export const postInterest = async (pleaseId: number, isInterested: boolean) => {
  await ApiClient.postWithLastDarakbangId('/interest', {
    pleaseId,
    isInterested,
  });
};
export const postLastReadChatId = async (
  moimId: number,
  lastReadChatId: number,
) => {
  await ApiClient.postWithLastDarakbangId('/chat/last', {
    moimId,
    lastReadChatId,
  });
};

export const postConfirmDatetime = async (
  chatRoomId: number,
  date: string,
  time: string,
) => {
  await ApiClient.postWithLastDarakbangId(`/chatroom/${chatRoomId}/datetime`, {
    date,
    time,
  });
  return chatRoomId;
};

export const postConfirmPlace = async (chatRoomId: number, place: string) => {
  await ApiClient.postWithLastDarakbangId(`/chatroom/${chatRoomId}/place`, {
    place,
  });

  return chatRoomId;
};

export const postPlease = async (please: PleaseInfoInput) => {
  await ApiClient.postWithLastDarakbangId('/please', please);
};

export const postNotificationToken = async (currentToken: string) => {
  await ApiClient.postWithAuth('/notification/register', {
    token: currentToken,
  });
};

export const postDarakbang = async ({
  name,
  nickname,
}: {
  name: string;
  nickname: string;
}) => {
  const response = await ApiClient.postWithAuth('/darakbang', {
    name,
    nickname,
  });

  const json = await response.json();

  return json?.data;
};

export const postDarakbangEntrance = async ({
  code,
  nickname,
}: {
  code: string;
  nickname: string;
}) => {
  const data = await ApiClient.postWithAuth(
    '/darakbang/entrance?code=' + code,
    {
      nickname,
    },
  );

  const json = await data.json();

  return json.data as number;
};

export const postBet = async (bet: BetInputInfo) => {
  const data = await ApiClient.postWithLastDarakbangId('/bet', bet);

  const json: PostBet = await data.json();
  return json.data.betId;
};

export const postBetResult = async (betId: number) => {
  await ApiClient.postWithLastDarakbangId(`/bet/${betId}/result`);
};

export const postJoinBet = async (betId: number) => {
  await ApiClient.postWithLastDarakbangId(`/bet/${betId}`);
};

export const patchMyInfo = async (myInfo: FormData) => {
  await ApiClient.postWithLastDarakbangId(`/member/mine`, myInfo);
};
