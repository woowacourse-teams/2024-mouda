import ApiClient from './apiClient';

export const deleteCancelChamyo = async (moimId: number) => {
  await ApiClient.deleteWithLastDarakbangId(`chamyo`, {
    moimId,
  });
};
