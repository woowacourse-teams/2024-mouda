import POLICIES from '@_constants/poclies';

export const validateNickName = (nickName: string) => {
  return (
    POLICIES.minNicknameLength <= nickName.length &&
    nickName.length <= POLICIES.maxNicknameLength
  );
};
