import ApiClient from './apiClient';

export const deleteCancelChamyo = async (moimId: number) => {
await ApiClient.deleteWithAuth(`chamyo`, {
    moimId,
  });
};
