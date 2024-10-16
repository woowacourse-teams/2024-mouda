import * as S from './MineInfoCard.style';
import ProfileFrame from '@_components/ProfileFrame/ProfileFrame';
import { useTheme } from '@emotion/react';
import Edit from '@_common/assets/edit.svg?url';

interface MineInfoCardProps {
  myInfo: {
    nickname: string;
    name: string;
    profile: string;
  };
  isEditing: boolean;
  onProfileClick: () => void;
  setNickname: (nickname: string) => void;
}

export default function MineInfoCard({
  myInfo,
  isEditing,
  onProfileClick,
  setNickname,
}: MineInfoCardProps) {
  const { name, nickname, profile } = myInfo;
  const theme = useTheme();

  return (
    <div css={S.MineInfoContainer({ theme })}>
      <div
        onClick={isEditing ? onProfileClick : undefined}
        css={S.profileWrapper}
      >
        <ProfileFrame width={9} height={9} borderWidth={0} src={profile} />
        {isEditing && <img src={Edit} alt="Edit" css={S.editSVG} />}
      </div>
      {isEditing ? (
        <input
          css={S.input({ theme })}
          value={nickname}
          onChange={(e) => setNickname(e.target.value)}
        />
      ) : (
        <span css={theme.typography.h5}>{nickname}</span>
      )}
      <span css={theme.typography.b1}>{name}</span>
    </div>
  );
}
