import * as S from './MineInfoCard.style';

import { common } from '@_common/common.style';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import { useTheme } from '@emotion/react';
import ProfileFrame from '@_components/ProfileFrame/ProfileFrame';

interface MineInfoCardProps {
  nickname: string;
  profile: string;
}
export default function MineInfoCard(props: MineInfoCardProps) {
  const { nickname, profile } = props;
  const theme = useTheme();
  const { darakbangName } = useNowDarakbangName();
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
        <span css={[theme.typography.c1, common.nonScroll]}>
          {darakbangName}
        </span>
      </div>
    </div>
  );
}
