import * as S from './ProfileCard.style';

import { Participation } from '@_types/index';
import ProfileFrame from '../../../../../../components/ProfileFrame/ProfileFrame';
import useNicknameWidthEffect from '@_hooks/useNicknameWidthEffect';
import { useTheme } from '@emotion/react';
import { Fragment } from 'react';
import useDarakbangMember from '@_hooks/queries/useDarakbangMember';
import useProfileBottomSheet from '@_hooks/useProfileBottomSheet';

interface ProfileCardProps {
  info: Participation;
}

export default function ProfileCard(props: ProfileCardProps) {
  const { info } = props;

  const theme = useTheme();

  const { nicknameRef, formattedNickname } = useNicknameWidthEffect({
    nickname: info.nickname,
    maxNicknameWidth: 70,
  });

  const { member } = useDarakbangMember(info.darakbangMemberId);

  console.log(member);
  const { profileBottomSheet, open } = useProfileBottomSheet({
    name: member?.name || '',
    nickname: member?.nickname || '',
    description: member?.description || '',
    url: member?.profile || '',
  });

  const handleCardClick = () => {
    open();
  };

  return (
    <Fragment>
      <li
        css={S.profileCard}
        tabIndex={0}
        aria-label={info.nickname}
        onClick={handleCardClick}
      >
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
      {profileBottomSheet}
    </Fragment>
  );
}
