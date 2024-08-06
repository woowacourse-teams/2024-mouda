import { MoimInputInfo } from '@_types/index';
import ApiClient from './apiClient';

export const fetchCompleteMoin = async (moimId: number) => {
  await ApiClient.patchWithAuth(`moim/${moimId}/complete`, {
    moimId,
  });
};

export const fetchCancelMoin = async (moimId: number) => {
  await ApiClient.patchWithAuth(`moim/${moimId}/cancel`, {
    moimId,
  });
};

export const fetchModifyMoin = async (
  moimId: number,
  state: TempMoimInputInfo,
) => {
  await ApiClient.patchWithAuth(`moim`, {
    moimId,
    ...state,
  });
};

export const fetchReopenMoin = async (moimId: number) => {
  await ApiClient.patchWithAuth(`moim/${moimId}/reopen`, {
    moimId,
  });
};
