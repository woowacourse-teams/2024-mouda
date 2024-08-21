import ProfileFrame from './ProfileFrame';
import * as S from './ProfileCard.style';
import { useTheme } from '@emotion/react';
import { Participation } from '@_types/index';

interface ProfileCardProps {
  info: Participation;
}
export default function ProfileCard(props: ProfileCardProps) {
  const { info } = props;
  const theme = useTheme();
  return (
    <div css={S.ProfileCard}>
      <ProfileFrame width={7} height={7} src={info.profile} role={info.role} />
      <div css={S.ProfileName({ theme })}>{info.nickname}</div>
    </div>
  );
}
