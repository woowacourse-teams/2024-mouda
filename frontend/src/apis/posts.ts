import { MoimInputInfo, PleaseInfoInput } from '@_types/index';
import { PostMoim, PostMoimBody } from './responseTypes';

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

export const postChat = async (moimId: number, content: string) => {
  await ApiClient.postWithLastDarakbangId('/chat', {
    moimId,
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
  moimId: number,
  date: string,
  time: string,
) => {
  await ApiClient.postWithLastDarakbangId('/chat/datetime', {
    moimId,
    date,
    time,
  });
  return moimId;
};

export const postConfirmPlace = async (moimId: number, place: string) => {
  await ApiClient.postWithLastDarakbangId('/chat/place', {
    moimId,
    place,
  });
  return moimId;
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
