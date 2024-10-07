import * as S from './ProfileCard.style';
import { useTheme } from '@emotion/react';
import { Participant } from '@_types/index';
import ProfileFrame from '../../../../../../components/ProfileFrame/ProfileFrame';

interface ProfileCardProps {
  info: Participant;
}
export default function ProfileCard(props: ProfileCardProps) {
  const { info } = props;
  const theme = useTheme();
  return (
    <div css={S.ProfileCard}>
      <ProfileFrame width={7} height={7} src={info.profileUrl} />
      <div css={S.ProfileName({ theme })}>{info.nickname}</div>
    </div>
  );
}
