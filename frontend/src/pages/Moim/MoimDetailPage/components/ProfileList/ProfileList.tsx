import * as S from './ProfileList.style';

import { Fragment } from 'react';
import { Participation } from '@_types/index';
import ProfileCard from './ProfileCard/ProfileCard';
import { useTheme } from '@emotion/react';

interface ProfileListProps {
  participants: Participation[];
}

export default function ProfileList(props: ProfileListProps) {
  const theme = useTheme();
  const { participants } = props;
  return (
    <Fragment>
      <h2 css={theme.typography.s1} aria-label="참여자">
        참여자
      </h2>
      <ul css={S.ProfileContanier}>
        {participants.map((participant) => {
          return <ProfileCard key={participant.nickname} info={participant} />;
        })}
      </ul>
    </Fragment>
  );
}
