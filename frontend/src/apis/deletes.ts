import ApiClient from './apiClient';
import { checkStatus } from './apiconfig';

export const deleteCancelChamyo = async (moimId: number) => {
  const response = await ApiClient.deleteWithAuth(`moim/${moimId}/comment`, {
    moimId,
  });
  await checkStatus(response);
};
