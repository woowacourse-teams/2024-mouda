import * as S from './ProfileCard.style';

import { Participation } from '@_types/index';
import ProfileFrame from '../../../../../../components/ProfileFrame/ProfileFrame';
import useNicknameWidthEffect from '@_hooks/useNicknameWidthEffect';
import { useTheme } from '@emotion/react';

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
    <li css={S.profileCard} tabIndex={0} aria-label={info.nickname}>
      <ProfileFrame
        width={7}
        height={7}
        src={info.profile}
        role={info.role}
        aria-hidden
      />
      <div ref={nicknameRef} css={S.profileName({ theme })} aria-hidden>
        {formattedNickname}
      </div>
    </li>
  );
}
