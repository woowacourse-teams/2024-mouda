import ApiClient from './apiClient';

export const deleteCancelChamyo = async (moimId: number) => {
  await ApiClient.deleteWithLastDarakbangId(`/chamyo`, {
    moimId,
  });
};

export const deleteMyInfo = async () => {
  await ApiClient.deleteWithAuth(`/auth`);
};
