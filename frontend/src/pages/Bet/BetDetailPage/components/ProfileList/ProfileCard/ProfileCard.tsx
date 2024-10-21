import * as S from './ProfileCard.style';

import DefaultProfile from '@_common/assets/default_profile.svg?url';
import { Participant } from '@_types/index';
import useNicknameWidthEffect from '@_hooks/useNicknameWidthEffect';
import { useTheme } from '@emotion/react';

interface ProfileCardProps {
  info: Participant;
}
export default function ProfileCard(props: ProfileCardProps) {
  const { info } = props;

  const { nicknameRef, formattedNickname } = useNicknameWidthEffect({
    nickname: info.nickname,
    maxNicknameWidth: 100,
  });

  const theme = useTheme();

  return (
    <div css={S.profileCard({ theme })}>
      <img
        src={info.profileUrl}
        css={S.profileImage({ theme })}
        alt={info.nickname + ' 프로필사진'}
        onError={(event) => (event.currentTarget.src = DefaultProfile)}
      />
      <div ref={nicknameRef} css={S.profileNickname({ theme })}>
        {formattedNickname}
      </div>
    </div>
  );
}
