import { removeAccessToken } from '@_utils/tokenManager';
import * as S from './SettingList.style';
import SettingCard from '../SettingCard/SettingCard';

const Settings = [
  { title: '로그아웃', onClick: () => removeAccessToken() },
  { title: '회원탈퇴', onClick: () => {} },
];
export default function SettingList() {
  return (
    <ul css={S.cardListSection}>
      {Settings.map((SettingInfo, index) => {
        return (
          <SettingCard
            key={SettingInfo.title + index}
            title={SettingInfo.title}
            onClick={SettingInfo.onClick}
          ></SettingCard>
        );
      })}
    </ul>
  );
}
