import * as S from '@_components/MineInfoCard/MineInfoCard.style';
import ProfileFrame from '@_components/Profile/ProfileFrame';
import { useTheme } from '@emotion/react';

interface MineInfoCardProps {
  nickname: string;
  profile: string;
}
export default function MineInfoCard(props: MineInfoCardProps) {
  const { nickname, profile } = props;
  const theme = useTheme();
  return (
    <div css={S.MineInfoContainer()}>
      <ProfileFrame
        width={9}
        height={9}
        borderWidth={0}
        src={profile}
      ></ProfileFrame>
      <div css={S.MinetextWrapper}>
        <span css={theme.typography.s1}>안녕하세요</span>
        <span css={theme.typography.h4}>{nickname}</span>
        <span css={theme.typography.c1}>우아한테크코스 6기</span>
      </div>
    </div>
  );
}
