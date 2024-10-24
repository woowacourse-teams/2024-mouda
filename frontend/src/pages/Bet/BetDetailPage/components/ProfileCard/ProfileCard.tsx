import * as S from './ProfileCard.style';

import DefaultProfile from '@_common/assets/default_profile.svg?url';
import { Participant } from '@_types/index';
import useNicknameWidthEffect from '@_hooks/useNicknameWidthEffect';
import { useTheme } from '@emotion/react';
import useProfileBottomSheet from '@_hooks/useProfileBottomSheet';
import useDarakbangMember from '@_hooks/queries/useDarakbangMember';
import { Fragment } from 'react';

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

  console.log(info);
  const { member } = useDarakbangMember(info.id);

  const { profileBottomSheet, open } = useProfileBottomSheet({
    name: member?.name || '',
    nickname: member?.nickname || '',
    description: member?.description || '',
    url: member?.profile || '',
  });

  return (
    <Fragment>
      <div css={S.profileCard({ theme })} onClick={() => open()}>
        <img
          src={info.profileUrl || ''}
          css={S.profileImage({ theme })}
          alt={info.nickname + ' 프로필사진'}
          onError={(event) => (event.currentTarget.src = DefaultProfile)}
        />
        <div ref={nicknameRef} css={S.profileNickname({ theme })}>
          {formattedNickname}
        </div>
      </div>
      {profileBottomSheet}
    </Fragment>
  );
}
