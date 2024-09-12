import * as S from './ProfileList.style';
import { Participation } from '@_types/index';
import { useTheme } from '@emotion/react';
import { Fragment } from 'react';
import ProfileCard from './ProfileCard/ProfileCard';

interface ProfileListProps {
  participants: Participation[];
}

export default function ProfileList(props: ProfileListProps) {
  const theme = useTheme();
  const { participants } = props;
  return (
    <Fragment>
      <div css={theme.typography.s1}>참여자</div>
      <div css={S.ProfileContanier}>
        {participants.map((participant) => {
          return <ProfileCard key={participant.nickname} info={participant} />;
        })}
      </div>
    </Fragment>
  );
}
