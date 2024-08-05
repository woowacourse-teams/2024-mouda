import ApiClient from './apiClient';
import { checkStatus } from './apiconfig';

export const deleteCancelChamyo = async (moimId: number) => {
  const response = await ApiClient.deleteWithAuth(`chamyo`, {
    moimId,
  });
  await checkStatus(response);
};
