import { removeAccessToken } from '@_utils/tokenManager';
import * as S from './SettingList.style';
import SettingCard from '../SettingCard/SettingCard';
import useDeleteMyInfo from '@_hooks/mutaions/useDeleteMyInfo';
import { useNavigate } from 'react-router-dom';
import ROUTES from '@_constants/routes';

export default function SettingList() {
  const navigate = useNavigate();
  const { mutate } = useDeleteMyInfo();
  const Settings = [
    {
      title: '로그아웃',
      onClick: () => {
        removeAccessToken();
        navigate(ROUTES.home);
      },
    },
    {
      title: '회원탈퇴',
      onClick: () => {
        mutate();
        navigate(ROUTES.home);
      },
    },
  ];
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
