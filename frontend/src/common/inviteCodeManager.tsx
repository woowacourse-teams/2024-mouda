const INVITE_CODE_KEY = 'invite-code';

export const setInviteCode = (inviteCode: string): void => {
  localStorage.setItem(INVITE_CODE_KEY, inviteCode);
};

export const getInviteCode = () => {
  const inviteCode = localStorage.getItem(INVITE_CODE_KEY);
  if (!inviteCode) return null;
  return inviteCode;
};

export const removeInviteCode = () => {
  localStorage.removeItem(INVITE_CODE_KEY);
};
