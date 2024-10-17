import * as S from './ProfileCard.style';
import { useTheme } from '@emotion/react';
import { Participation } from '@_types/index';
import ProfileFrame from '../../../../../../components/ProfileFrame/ProfileFrame';
import useNicknameWidthEffect from '@_hooks/useNicknameWidthEffect';

interface ProfileCardProps {
  info: Participation;
}
export default function ProfileCard(props: ProfileCardProps) {
  const { info } = props;

  const { nicknameRef, formattedNickname } = useNicknameWidthEffect({
    nickname: info.nickname,
    maxNicknameWidth: 70,
  });

  const theme = useTheme();

  return (
    <div css={S.profileCard}>
      <ProfileFrame width={7} height={7} src={info.profile} role={info.role} />
      <div ref={nicknameRef} css={S.profileName({ theme })}>
        {formattedNickname}
      </div>
    </div>
  );
}
