import ApiClient from './apiClient';
import { MoimInputInfo } from '@_types/index';
import { PostMoim } from './responseTypes';

export const postMoim = async (moim: MoimInputInfo): Promise<number> => {
  const response = await ApiClient.postWithAuth('moim', moim);

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
