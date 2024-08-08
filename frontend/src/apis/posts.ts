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

  const response = await ApiClient.postWithAuth('moim', parsedMoim);

  const json: PostMoim = await response.json();
  return json.data;
};

export const postJoinMoim = async (moimId: number) => {
  await ApiClient.postWithAuth('chamyo', {
    moimId,
  });
};

export const postChangeZzim = async (moimId: number) => {
  await ApiClient.postWithAuth('zzim', {
    moimId,
  });
};
export const postWriteComment = async (
  moimId: number,
  selectedComment: number,
  message: string,
) => {
  if (selectedComment === 0) {
    await ApiClient.postWithAuth(`moim/${moimId}`, {
      content: message,
    });
  } else {
    await ApiClient.postWithAuth(`moim/${moimId}`, {
      parentId: selectedComment,
      content: message,
    });
  }
};

export const postChat = async (moimId: number, content: string) => {
  await ApiClient.postWithAuth('chat', {
    moimId,
    content,
  });
};

export const postInterest = async (pleaseId: number, isInterested: boolean) => {
  await ApiClient.postWithAuth('interest', {
    pleaseId,
    isInterested,
  });
};
export const postLastReadChatId = async (
  moimId: number,
  lastReadChatId: number,
) => {
  await ApiClient.postWithAuth('chat/last', {
    moimId,
    lastReadChatId,
  });
};

export const postConfirmDatetime = async (
  moimId: number,
  date: string,
  time: string,
) => {
  await ApiClient.postWithAuth('chat/datetime', {
    moimId,
    date,
    time,
  });
};

export const postConfirmPlace = async (moimId: number, place: string) => {
  await ApiClient.postWithAuth('chat/place', {
    moimId,
    place,
  });
};

export const postPlease = async (please: PleaseInfoInput) => {
  await ApiClient.postWithAuth('please', please);
};
