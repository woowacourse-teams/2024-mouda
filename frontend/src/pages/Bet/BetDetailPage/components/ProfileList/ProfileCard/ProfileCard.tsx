import * as S from './ProfileCard.style';
import { useTheme } from '@emotion/react';
import { Participant } from '@_types/index';
import ProfileFrame from '../../../../../../components/ProfileFrame/ProfileFrame';
import useNicknameWidthEffect from '@_hooks/useNicknameWidthEffect';

interface ProfileCardProps {
  info: Participant;
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
      <ProfileFrame width={7} height={7} src={info.profileUrl} />
      <div ref={nicknameRef} css={S.profileNickname({ theme })}>
        {formattedNickname}
      </div>
    </div>
  );
}
