import * as S from './MineInfoCard.style';
import ProfileFrame from '@_components/ProfileFrame/ProfileFrame';
import { useTheme } from '@emotion/react';
import Edit from '@_common/assets/edit.svg?url';
import LabeledInput from '@_components/Input/MoimInput';
import { ChangeEvent } from 'react';
import { validateNickName } from '@_pages/Mypage/validate';

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
        <LabeledInput
          css={S.input({ theme })}
          value={nickname}
          onChange={(e: ChangeEvent<HTMLInputElement>) =>
            setNickname(e.target.value)
          }
          placeholder="닉네임을 1자에서 12자이하로 지어주세요"
          validateFun={validateNickName}
        />
      ) : (
        <span css={theme.typography.h5}>{nickname}</span>
      )}
      <span css={theme.typography.b1}>{name}</span>
    </div>
  );
}
