import * as S from './ProfileCardList.style';

import { Participant } from '@_types/index';
import ProfileCard from '../ProfileCard/ProfileCard';

interface ProfileCardListProps {
  profiles: Participant[];
}

export default function ProfileCardList(props: ProfileCardListProps) {
  const { profiles } = props;

  return (
    <div css={S.list}>
      {profiles.map((profile) => (
        <ProfileCard info={profile} key={profile.id} />
      ))}
    </div>
  );
}
