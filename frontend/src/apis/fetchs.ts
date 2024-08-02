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

