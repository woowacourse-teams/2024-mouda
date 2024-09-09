const LAST_DARAKBANG_ID_KEY = 'last-darakbang-id';

export const setLastDarakbangId = (lastDarakbangId: number): void => {
  localStorage.setItem(LAST_DARAKBANG_ID_KEY, lastDarakbangId + '');
};

export const getLastDarakbangId = () => {
  const lastDarakbangId = localStorage.getItem(LAST_DARAKBANG_ID_KEY);
  if (!lastDarakbangId || process.env.MSW === 'true') return null;
  return +lastDarakbangId;
};
