import { TempMoimInputInfo } from '@_types/index';
import ApiClient from './apiClient';
import { checkStatus } from './apiconfig';

export const fetchCompleteMoin = async (moimId: number) => {
  const response = await ApiClient.patchWithAuth(`moim/${moimId}/complete`, {
    moimId,
  });
  await checkStatus(response);
};

export const fetchCancelMoin = async (moimId: number) => {
  const response = await ApiClient.patchWithAuth(`moim/${moimId}/cancel`, {
    moimId,
  });
  await checkStatus(response);
};

export const fetchModifyMoin = async (
  moimId: number,
  state: TempMoimInputInfo,
) => {
  const response = await ApiClient.patchWithAuth(`moim`, {
    moimId,
    ...state,
  });
  await checkStatus(response);
};

export const fetchReopenMoin = async (moimId: number) => {
  const response = await ApiClient.patchWithAuth(`moim/${moimId}/reopen`, {
    moimId,
  });
  await checkStatus(response);
};
