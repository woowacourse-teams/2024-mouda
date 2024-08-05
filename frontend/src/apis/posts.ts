import { MoimInputInfo } from '@_types/index';
import ApiClient from './apiClient';
import { PostMoim } from './responseTypes';
import { checkStatus } from './apiconfig';

export const postMoim = async (moim: MoimInputInfo): Promise<number> => {
  const response = await ApiClient.postWithAuth('moim', moim);

  checkStatus(response);

  const json: PostMoim = await response.json();
  return json.data;
};

export const postJoinMoim = async (moimId: number) => {
  const response = await ApiClient.postWithAuth('chamyo', {
    moimId,
  });
  await checkStatus(response);
};

export const postChangeZzim = async (moimId: number) => {
  const response = await ApiClient.postWithAuth('zzim', {
    moimId,
  });
  await checkStatus(response);
};
export const postWriteComment = async (
  moimId: number,
  selectedComment: number,
  message: string,
) => {
  if (selectedComment === 0) {
    const response = await ApiClient.postWithAuth(`moim/${moimId}`, {
      content: message,
    });
    await checkStatus(response);
  } else {
    const response = await ApiClient.postWithAuth(`moim/${moimId}`, {
      parentId: selectedComment,
      content: message,
    });
    await checkStatus(response);
  }
};
