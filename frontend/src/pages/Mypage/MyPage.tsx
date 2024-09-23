import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import MineInfoCard from './components/MineInfoCard/MineInfoCard';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { common } from '@_common/common.style';
import useMyInfo from '@_hooks/queries/useMyInfo';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import { css, useTheme } from '@emotion/react';

export default function MyPage() {
  const { myInfo, isLoading } = useMyInfo();
  const { darakbangName } = useNowDarakbangName();

  const theme = useTheme();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!myInfo) {
    return <div>Failed to load my information.</div>;
  }
  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <span css={[[theme.typography.h5, common.nonDrag]]}>
            <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
          </span>
        </InformationLayout.Header.Left>
      </InformationLayout.Header>
      <InformationLayout.ContentContainer>
        <MineInfoCard nickname={myInfo.nickname} profile={myInfo.profile} />
        <div
          css={css`
            display: flex;
            gap: 10px;
            justify-content: end;
          `}
        >
          <button
            onClick={() => alert('TODO: 로그아웃')}
            css={css`
              ${theme.typography.b2}
              background: none;
              border: 1px solid ${theme.colorPalette.grey[200]};
              border-radius: 4px;
            `}
          >
            로그아웃
          </button>
          <button
            onClick={() => alert('TODO: 회원탈퇴')}
            css={css`
              ${theme.typography.b2}
              background: none;
              border: 1px solid ${theme.colorPalette.grey[200]};
              border-radius: 4px;
            `}
          >
            회원탈퇴
          </button>
        </div>
      </InformationLayout.ContentContainer>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </InformationLayout>
  );
}
