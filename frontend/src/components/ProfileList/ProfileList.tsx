import ProfileBox from '@_components/Profile/ProfileBox';
import * as S from './ProfileList.style';
import { Participation } from '@_types/index';

interface ProfileListProps {
  participants: Participation[];
}

export default function ProfileList(props: ProfileListProps) {
  const { participants } = props;

  return (
    <div css={S.ProfileContanier}>
      {participants.map((participant) => {
        return (
          <ProfileBox
            key={participant.nickname}
            name={participant.nickname}
            src={participant.profile}
          />
        );
      })}
    </div>
  );
}
