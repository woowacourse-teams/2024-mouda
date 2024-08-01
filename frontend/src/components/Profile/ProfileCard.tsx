import ProfileFrame from './ProfileFrame';
import * as S from './ProfileCard.style';
import { useTheme } from '@emotion/react';
import { Participation } from '@_types/index';

interface ProfileCardProps {
  profile: Participation;
}
export default function ProfileCard(props: ProfileCardProps) {
  const { profile } = props;
  const theme = useTheme();
  return (
    <div css={S.ProfileCard}>
      <ProfileFrame
        width={7}
        height={7}
        src={profile.src}
        role={profile.role}
      />
      <div css={S.ProfileName({ theme })}>{profile.nickname}</div>
    </div>
  );
}
